package com.example;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.text.*;

@RestController
@RequestMapping("/passes")
public class PassController {
  private PassRepository repository;
  private EventRepository eventRepo;
  private StudentRepository studentRepo;
  private OrganizationRepository orgRepo;

  @Autowired
  public PassController(PassRepository repository, EventRepository eventRepo, StudentRepository studentRepo, OrganizationRepository orgRepo) {
    this.repository = repository;
    this.eventRepo = eventRepo;
    this.studentRepo = studentRepo;
    this.orgRepo = orgRepo;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Pass> get(@PathVariable("id") Long id) {
    Pass pass = repository.findOne(id);
    if (null == pass) return new ResponseEntity<Pass>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<Pass>(pass, HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Pass> delete(@PathVariable("id") Long id) {
    Pass pass = repository.findOne(id);
    if (pass == null) return new ResponseEntity<Pass>(HttpStatus.NOT_FOUND);
    repository.delete(pass);
    return new ResponseEntity<Pass>(pass, HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
  public ResponseEntity<Pass> editPass(
    @PathVariable("id") Long id,
    @RequestBody Pass pass
  ) {
    Pass foundPass = repository.findOne(id);
    if (null == foundPass) return new ResponseEntity<Pass>(
      HttpStatus.NOT_FOUND
    ); else {
      String[] prevOwners = pass.getOwners();
      String[] newOwners = new String[prevOwners.length+1];
      for (int i=0;i<prevOwners.length;i++){
        newOwners[i] = prevOwners[i];
      }
      Long newUserId = pass.getUserId();
      Student foundStudent = studentRepo.findOne(newUserId);
      String newNetId = foundStudent.getNetID();
      newOwners[newOwners.length-1] = newNetId;
      pass.setOwners(newOwners);
      foundPass.updateParameters(pass);
      repository.save(foundPass);
      return get(foundPass.getId());
    }
  }

  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public ResponseEntity<Pass> update(@RequestBody Pass pass) {
    System.out.println("THIS IS A LOGGING LINE"); //logging
    System.out.println(pass);
    System.out.println(
      "THIS IS THE PASS BEING ADDED: " + pass.getId()
    );
    String[] newOwners = new String[1];
    Long newUserId = pass.getUserId();
    Student foundStudent = studentRepo.findOne(newUserId);
    String newNetId = foundStudent.getNetID();
    newOwners[0] = newNetId;
    pass.setOwners(newOwners);
    repository.save(pass);
    return get(pass.getId());
  }

  
  @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
  public List<Pass> getByUserId(@PathVariable("id") Long userId) {
    List<Pass> passList = repository.findByUserIdOrderByDateAsc(userId);
    List<Pass> passListFinal = new ArrayList<Pass>();
    Date date = new Date();
    Calendar c = Calendar.getInstance(); 
    c.setTime(date); 
    c.add(Calendar.DATE, -1);
    date = c.getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));
    String currDate = formatter.format(date);
    for (Pass pass : passList) {
      // String eventDate = new SimpleDateFormat("yyyy-MM-dd").format(pass.getDate()); 
      String eventDate = "2/3/3";
      currDate = "2/3/2";
      System.out.println(currDate);
      System.out.println(eventDate);
      // if (eventDate.compareTo(currDate) >= 0) {
      passListFinal.add(pass);
      // }
    }
    return passListFinal;
  }

  @RequestMapping(value = "/previousowner/{id}", method = RequestMethod.GET)
  public String getPreviousOwner(@PathVariable("id") Long id) {
    Pass pass = repository.findOne(id);
    String[] owners = pass.getOwners();
    if (owners == null || owners.length < 2) {
      Organization org = orgRepo.findOne(pass.getOrgId());
      return org.getName();
    }
    return owners[owners.length-2];
  }

  @RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
  public List<Pass> getByEventsId(@PathVariable("id") Long eventsId) {
    return repository.findByEventsId(eventsId);
  }

  @RequestMapping(value = "/for_user_at_event", method = RequestMethod.GET)
  public List<Pass> getByEventsAndUserId(@RequestParam("eventsid") Long eventsId, @RequestParam("userid") Long userId) {
    List<Pass> passes = getByUserId(userId);
    List<Pass> finalPasses = new ArrayList<Pass>();
    for (Pass pass : passes) {
        if (pass.getEventsId().equals(eventsId)) finalPasses.add(pass);
    }
    return finalPasses;
  }

  @RequestMapping
  public List<Pass> all() {
    Date date = new Date();
    Calendar c = Calendar.getInstance(); 
    c.setTime(date); 
    c.add(Calendar.DATE, -1);
    date = c.getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));
    String currDate = formatter.format(date);
    List<Event> eventList = eventRepo.findAllByOrderByDateAsc();
    List<Pass> passList = new ArrayList<Pass>();
    for (Event event : eventList) {
      List<Pass> eventPasses = repository.findByEventsId(event.getId());
      // String eventDate = new SimpleDateFormat("yyyy-MM-dd").format(event.getDate()); 
      String eventDate = "2/3/3";
      currDate = "2/3/2";
      System.out.println(currDate);
      System.out.println(eventDate);
      // if (eventDate.compareTo(currDate) >= 0) {
      for (Pass pass : eventPasses) {
        passList.add(pass);
      }
      // } 
    }
    return passList;
  }
}
