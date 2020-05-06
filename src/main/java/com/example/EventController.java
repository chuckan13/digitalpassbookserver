package com.example;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream; 
import org.springframework.transaction.annotation.Transactional;


@RestController
@RequestMapping("/events")
public class EventController {
  private EventRepository repository;
  private OrganizationRepository orgRepo;
  private PassRepository passRepo;
  private StudentRepository studentRepo;

  @Autowired
  public EventController(EventRepository repository, OrganizationRepository orgRepo, PassRepository passRepo, StudentRepository studentRepo) {
    this.repository = repository;
    this.orgRepo = orgRepo;
    this.passRepo = passRepo;
    this.studentRepo = studentRepo;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Event> get(@PathVariable("id") Long id) {
    Event event = repository.findOne(id);
    if (null == event) return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<Event>(event, HttpStatus.OK);
  }

  @Transactional
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Event> delete(@PathVariable("id") Long id) {
    Event event = repository.findOne(id);
    if (event == null) return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
    passRepo.deleteByEventsId(id);
    repository.delete(event);
    return new ResponseEntity<Event>(event, HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
  public ResponseEntity<Event> editEvent(
    @PathVariable("id") Long id,
    @RequestBody Event event
  ) {
    Event foundEvent = repository.findOne(id);
    if (null == foundEvent) return new ResponseEntity<Event>(
      HttpStatus.NOT_FOUND
    ); else {
      foundEvent.updateParameters(event);
      repository.save(foundEvent);
      return get(foundEvent.getId());
    }
  }

  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public ResponseEntity<Event> update(@RequestBody Event event) {
    System.out.println("THIS IS A LOGGING LINE"); //logging
    System.out.println(event);
    System.out.println(
      "THIS IS THE EVENT BEING ADDED: " + event.getId() + event.getEventName()
    );
    repository.save(event);
    return get(event.getId());
  }

  @RequestMapping(value = "/org/{id}", method = RequestMethod.GET)
  public List<Event> getByOrgId(@PathVariable("id") Long orgId) {
    return repository.findByOrgIdOrderByDateAsc(orgId);
  }

  @RequestMapping(value = "/passes/{id}", method = RequestMethod.GET)
  public List<Pass> getPasses(@PathVariable("id") Long id) {
    List<Pass> passes = passRepo.findByEventsId(id);
    return passes;
  }

  @RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
  public List<Student> getStudents(@PathVariable("id") Long id) {
    Event currEvent = get(id).getBody();
    Organization currOrg = orgRepo.findOne(currEvent.getOrgId());
    List<Pass> currPasses = getPasses(id);
    List<Student> studentsList = new ArrayList<Student>();
    for (Pass pass : currPasses) {
      Student currStudent = studentRepo.findOne(pass.getUserId());
      if (currStudent != null && currStudent.getOrgId() != currOrg.getId()) {
        studentsList.add(currStudent);
      }
    }
    return studentsList;
  }

  @RequestMapping(value = "/numinvites/{id}", method = RequestMethod.GET)
  public ResponseEntity<Integer> getNumInvites(@PathVariable("id") Long id) {
    List<Student> allStudentsList = getStudents(id);
    int count = 0;
    Event currEvent = get(id).getBody();
    Long eventOrgId = currEvent.getId();
    for (Student student : allStudentsList) {
      if (student.getOrgId() != eventOrgId) {
        count++;
      }
    }
    return new ResponseEntity<Integer>(count, HttpStatus.OK);
  }

  @RequestMapping(value = "/numpasses/{id}", method = RequestMethod.GET)
  public ResponseEntity<Integer> getNumPasses(@PathVariable("id") Long id) {
    List<Pass> passesList = getPasses(id);
    return new ResponseEntity<Integer>(passesList.size(), HttpStatus.OK);
  }

  @RequestMapping(value = "/isinvited", method = RequestMethod.POST)
  public Boolean checkIsInvited(
    @RequestParam("eventsid") Long eventsId, 
    @RequestParam("userbarcode") String userBarcode
  ) {
    Event event = repository.findOne(eventsId);
    List<Student> invitedStudents = getStudents(eventsId);
    List<Student> studentList = studentRepo.findByBarCodeNumber(userBarcode);
    for (Student student : studentList) {
        if (student.getOrgId() == event.getOrgId()) return true;
        if (invitedStudents.contains(student)) {
          // if haven't checked in yet and are invited, lock one pass and return true
          List<Pass> passes = passRepo.findByEventsIdAndUserId(eventsId, student.getId());
          if (passes.isEmpty()) continue;
          if (!passes.stream().filter(o -> o.getIsLocked()).findFirst().isPresent()) { // if no passes are locked yet
            Pass pass = passes.get(0);
            pass.setIsLocked(true); // lock the pass
            passRepo.save(pass);
            event.setNumArrived(event.getNumArrived()+1); // increase the number of students arrived by 1
            repository.save(event);
          }
          return true;
        }
    }
    return false;
  }

  @RequestMapping(value = "/{id}/bouncer", method = RequestMethod.PATCH)
  public ResponseEntity<Event> addBouncer(
    @PathVariable("id") Long id,
    @RequestBody String netId
  ) {
    Event event = repository.findOne(id);
    Student student = studentRepo.findByNetId(netId);
    if (event == null || student == null) 
      return new ResponseEntity<Event>(HttpStatus.NOT_FOUND); 
    // update arrays of the event, checking if student is already in list
    String[] eventBouncers = event.getBouncers();
    if (eventBouncers == null) eventBouncers = new String[0];
    if (!Arrays.asList(eventBouncers).contains(netId)) {
      String[] newEventBouncers = new String[eventBouncers.length+1];
      for (int i = 0; i < eventBouncers.length; i++) newEventBouncers[i] = eventBouncers[i]; 
      newEventBouncers[eventBouncers.length] = netId;
      event.setBouncers(newEventBouncers);
      repository.save(event);
    }
    // update array of the user
    int[] bouncingEvents = student.getBouncingEvents();
    if (bouncingEvents == null) bouncingEvents = new int[0];
    if (!IntStream.of(bouncingEvents).anyMatch(x -> x == id)) {
      int[] newBouncingEvents = new int[bouncingEvents.length+1];
      for (int i = 0; i < bouncingEvents.length; i++) newBouncingEvents[i] = bouncingEvents[i]; 
      newBouncingEvents[bouncingEvents.length] = id.intValue();
      student.setBouncingEvents(newBouncingEvents);
      studentRepo.save(student);
    }
    return get(event.getId());
  }

  @RequestMapping
  public List<Event> all() {
    return repository.findAllByOrderByDateAsc();
  }
}
