package com.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrganizationRepository
  extends JpaRepository<Organization, Long> {
  Organization findByName(String name);
  Organization findBySignin(String signin);
  List<Organization> findAllByOrderByNameAsc();
}
