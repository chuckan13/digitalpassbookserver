package com.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
  Student findByNetId(String netId);
  // List<Student> findByNetId(String netId);
  // TODO: change findByNetId to returning a list?
  List<Student> findByOrgId(Long orgId);
  List<Student> findByBarCodeNumber(String barCodeNumber);
  List<Student> findAllByOrderByNameAsc();
  List<Student> findByOrgIdAndGraduatingClass(Long orgId, Long graduatingClass);
}
