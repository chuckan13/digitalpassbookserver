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
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/students")
public class StudentController {
  private StudentRepository repository;
  private PassRepository passRepo;
  private OrganizationRepository orgRepo;

  @Autowired
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public StudentController(StudentRepository repository, PassRepository passRepo, 
  OrganizationRepository orgRepo, PasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.passRepo = passRepo;
    this.orgRepo = orgRepo;
    this.passwordEncoder = passwordEncoder;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Student> get(@PathVariable("id") Long id) {
    Student student = repository.findOne(id);
    if (null == student) return new ResponseEntity<Student>(
      HttpStatus.NOT_FOUND
    );
    return new ResponseEntity<Student>(student, HttpStatus.OK);
  }

  @RequestMapping(value = "/netid/{netid}", method = RequestMethod.GET)
  public ResponseEntity<Student> getByNetId(@PathVariable("netid") String netId) {
    Student student = repository.findByNetId(netId);
    if (null == student) return new ResponseEntity<Student>(
      HttpStatus.NOT_FOUND
    );
    return new ResponseEntity<Student>(student, HttpStatus.OK);
  }

  @Transactional
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Student> delete(@PathVariable("id") Long id) {
    Student student = repository.findOne(id);
    if (student == null) return new ResponseEntity<Student>(
      HttpStatus.NOT_FOUND
    );
    List<Organization> orgsList = orgRepo.findAll();
    for (Organization x: orgsList) {
      String[] permList = x.getPermGuestList();
      if (permList == null) {
        continue;
      }
      for (int i=0;i<permList.length;i++) {
        if(permList[i].equals(student.getNetID())){
          permList = ArrayUtils.remove(permList, i);
        }
      }
      x.setPermGuestList(permList);
    }  
    passRepo.deleteByUserId(id);
    repository.delete(student);
    return new ResponseEntity<Student>(student, HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
  public ResponseEntity<Student> editStudent(
    @PathVariable("id") Long id,
    @RequestBody Student student
  ) {
    Student foundStudent = repository.findOne(id);
    if (null == foundStudent) return new ResponseEntity<Student>(
      HttpStatus.NOT_FOUND
    ); else {
      if (!foundStudent.getPassword().equals(student.getPassword())) { // might be an issue if are equivalent but not equal
        return new ResponseEntity<Student>(HttpStatus.METHOD_NOT_ALLOWED);
      }
      foundStudent.updateParameters(student);
      repository.save(foundStudent);
      return get(foundStudent.getId());
    }
  }

  @RequestMapping(value = "/{id}/password", method = RequestMethod.PATCH)
  public ResponseEntity<Student> editPassword(
    @PathVariable("id") Long id,
    @RequestBody String password
  ) {
    Student foundStudent = repository.findOne(id);
    if (null == foundStudent) return new ResponseEntity<Student>(
      HttpStatus.NOT_FOUND
    ); else {
      foundStudent.setPassword(passwordEncoder.encode(password));
      repository.save(foundStudent);
      return get(foundStudent.getId());
    }
  }

  @RequestMapping(value = "/{id}/barcode", method = RequestMethod.PATCH)
  public ResponseEntity<Student> editBarCode(
    @PathVariable("id") Long id,
    @RequestBody String barcode
  ) {
    Student foundStudent = repository.findOne(id);
    if (null == foundStudent) return new ResponseEntity<Student>(
      HttpStatus.NOT_FOUND
    ); else {
      barcode = barcode.replace("\"", "");
      foundStudent.setBarCodeNumber(barcode);
      repository.save(foundStudent);
      return get(foundStudent.getId());
    }
  }

  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public ResponseEntity<Student> update(@RequestBody Student student) {
    System.out.println("THIS IS A LOGGING LINE"); //logging
    System.out.println(student);
    System.out.println(
      "THIS IS THE STUDENT BEING ADDED:" + student.getId() + student.getName()
    );
    student.setPassword(passwordEncoder.encode(student.getPassword()));
    repository.save(student);
    return get(student.getId());
  }

  @RequestMapping(value = "/existance/{netid}", method = RequestMethod.GET)
  public boolean checkStudentExist(@PathVariable("netid") String netId) {
    try {
      String resultName = URLDecoder.decode(netId, "UTF-8");
      System.out.println("RESULT NAME:" + resultName);
      Student returned = repository.findByNetId(resultName);
      if (returned == null) {
        return false;
      }
      return true;
    } catch (Exception e) {
      System.out.println("ERROR in checking existance: "+e.toString());
      return true;
    }
  }

  @RequestMapping(value = "/passwordcorrectness/{netid}/{password}", method = RequestMethod.GET)
  public boolean checkPassword(
    @PathVariable("netid") String netId, 
    @PathVariable("password") String password
  ) {
    try {
      String resultNetId = URLDecoder.decode(netId, "UTF-8");
      Student item = repository.findByNetId(resultNetId);
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

  @RequestMapping
  public List<Student> all() {
    return repository.findAllByOrderByNameAsc();
  }
}
