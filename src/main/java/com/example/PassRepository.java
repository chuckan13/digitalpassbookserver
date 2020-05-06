package com.example;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassRepository extends JpaRepository<Pass, Long> {
  List<Pass> findByUserIdOrderByDateAsc(Long userId);
  // List<Pass> findAllByOrderByDateAsc();
  // List<Pass> findByUserIdOrderByDateAsc(Long orgId);
  List<Pass> findByEventsId(Long eventsId);
  List<Pass> findByUserId(Long userId);
  List<Pass> findByEventsIdAndUserId(Long eventsId, Long userId);
  void deleteByEventsId(Long eventsId);
  void deleteByUserId(Long userId);
}
