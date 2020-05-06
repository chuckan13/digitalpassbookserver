package com.example;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {
  private OrganizationRepository repository;
  private StudentRepository studentRepo;
  private EventRepository eventRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  public OrganizationController(OrganizationRepository repository, StudentRepository studentRepo, 
  EventRepository eventRepo, PasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.studentRepo = studentRepo;
    this.eventRepo = eventRepo;
    this.passwordEncoder = passwordEncoder;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Organization> get(@PathVariable("id") Long id) {
    Organization organization = repository.findOne(id);
    if (null == organization) return new ResponseEntity<Organization>(
      HttpStatus.NOT_FOUND
    );
    return new ResponseEntity<Organization>(organization, HttpStatus.OK);
  }

  @Transactional
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Organization> delete(@PathVariable("id") Long id) {
    Organization organization = repository.findOne(id);
    if (organization == null) return new ResponseEntity<Organization>(
      HttpStatus.NOT_FOUND
    );
    eventRepo.deleteByOrgId(id);
    repository.delete(organization);
    return new ResponseEntity<Organization>(organization, HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
  public ResponseEntity<Organization> editOrganization(
    @PathVariable("id") Long id,
    @RequestBody Organization organization
  ) {
    Organization foundOrganization = repository.findOne(id);
    if (null == foundOrganization || null == organization) return new ResponseEntity<Organization>(
      HttpStatus.NOT_FOUND
    ); else {
      // ERROR ERROR ERROR!!! organization.getPassword() is returning null. for now, commented out
      if (!foundOrganization.getPassword().equals(organization.getPassword())) {
        // return new ResponseEntity<Student>(HttpStatus.METHOD_NOT_ALLOWED);
        System.out.println(foundOrganization.getPassword());
        System.out.println(organization.getPassword());
        organization.setPassword(passwordEncoder.encode(organization.getPassword()));
      }
      foundOrganization.updateParameters(organization);
      repository.save(foundOrganization);
      return get(foundOrganization.getId());
    }
  }

  @RequestMapping(value = "/{id}/password", method = RequestMethod.PATCH)
  public ResponseEntity<Organization> editPassword(
    @PathVariable("id") Long id,
    @RequestBody String password
  ) {
    Organization foundOrganization = repository.findOne(id);
    if (null == foundOrganization) return new ResponseEntity<Organization>(
      HttpStatus.NOT_FOUND
    ); else {
      foundOrganization.setPassword(passwordEncoder.encode(password));
      repository.save(foundOrganization);
      return get(foundOrganization.getId());
    }
  }

  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public ResponseEntity<Organization> update(
    @RequestBody Organization organization
  ) {
    System.out.println("THIS IS A LOGGING LINE"); //logging
    System.out.println(organization);
    System.out.println(
      "THIS IS THE ORGANIZATION BEING ADDED:" +
      organization.getId() +
      organization.getName()
    );
    organization.setPassword(passwordEncoder.encode(organization.getPassword()));
    repository.save(organization);
    return get(organization.getId());
  }

  @RequestMapping(value = "/existance/{name}", method = RequestMethod.GET)
  public boolean checkOrganizationExist(@PathVariable("name") String name) {
    try {
      String resultName = URLDecoder.decode(name, "UTF-8");
      System.out.println("RESULT NAME:" + resultName);
      Organization returned = repository.findByName(resultName);
      if (returned == null) {
        return false;
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @RequestMapping(value = "/signin/{signin}", method = RequestMethod.GET)
  public ResponseEntity<Organization> getOrgBySignIn(@PathVariable("signin") String signin) {
    Organization item = repository.findBySignin(signin);
    return new ResponseEntity<Organization>(item, HttpStatus.OK);
  }

  @RequestMapping(value = "/passwordcorrectness/{name}/{password}", method = RequestMethod.GET)
  public boolean checkPassword(
    @PathVariable("name") String name, 
    @PathVariable("password") String password
  ) {
    try {
      String resultName = URLDecoder.decode(name, "UTF-8");
      System.out.println("RESULT NAME:" + resultName);
      Organization item = repository.findBySignin(resultName);
      if (item == null) {
        return false;
      }
      if (passwordEncoder.matches(password, item.getPassword())) {
        return true;
      }
      return false; 
    } catch (Exception e) {
      return false;
    }
  }

  @RequestMapping(value = "/members/{id}", method = RequestMethod.GET)
  public List<Student> getMembers(@PathVariable("id") Long id){
    List<Student> members = studentRepo.findByOrgId(id);
    for (Student x : members) {
      System.out.println(x.getName());
    }
    return members;
  }

  @RequestMapping(value = "/members/{id}/{gradclass}", method = RequestMethod.GET)
  public List<Student> getMembersByClass(@PathVariable("id") Long id, @PathVariable("gradclass") Long gradclass){
    List<Student> membersByClass = studentRepo.findByOrgIdAndGraduatingClass(id, gradclass);
    return membersByClass;
  }

  @RequestMapping(value = "/events/{id}", method = RequestMethod.GET)
  public List<Event> getEvents(@PathVariable("id") Long id) {
    List<Event> events = eventRepo.findByOrgIdOrderByDateAsc(id);
    return events;
  }

  @RequestMapping(value = "/memberaddition/{id}", method = RequestMethod.PATCH)
  public ResponseEntity<Organization> addMember(
    @PathVariable("id") Long id,
    @RequestBody String netId
  ) {
    Organization organization = repository.findOne(id);
    Student student = studentRepo.findByNetId(netId);
    if (organization == null || student == null) 
      return new ResponseEntity<Organization>(HttpStatus.NOT_FOUND);
    if (student.getOrgId().intValue() != 0) // if belongs to a different organization
      return new ResponseEntity<Organization>(HttpStatus.METHOD_NOT_ALLOWED);
    student.setOrgId(id);
    studentRepo.save(student);
    return get(organization.getId());
  }

  @RequestMapping(value = "/memberremoval/{id}", method = RequestMethod.PATCH)
  public ResponseEntity<Organization> removeMember(
    @PathVariable("id") Long id,
    @RequestBody String netId
  ) {
    Organization organization = repository.findOne(id);
    Student student = studentRepo.findByNetId(netId);
    if (organization == null || student == null) 
      return new ResponseEntity<Organization>(HttpStatus.NOT_FOUND);
    if (!student.getOrgId().equals(id)) // if doesn't belong to organization
      return new ResponseEntity<Organization>(HttpStatus.METHOD_NOT_ALLOWED);
    student.setOrgId(new Long(0));
    studentRepo.save(student);
    return get(organization.getId());
  }

  @RequestMapping
  public List<Organization> all() {
    return repository.findAllByOrderByNameAsc();
  }
}
 