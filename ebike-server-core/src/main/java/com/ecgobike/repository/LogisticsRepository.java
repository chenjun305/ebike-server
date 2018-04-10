package com.ecgobike.repository;

import com.ecgobike.entity.Logistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ChenJun on 2018/4/10.
 */
@Repository
@Transactional
public interface LogisticsRepository extends JpaRepository<Logistics, Long> {
}
