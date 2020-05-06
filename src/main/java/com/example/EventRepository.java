package com.example;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
  List<Event> findByOrgId(Long orgId);
  List<Event> findByOrgIdOrderByDateAsc(Long orgId);
  List<Event> findAllByOrderByDateAsc();
  void deleteByOrgId(Long orgId);
}
