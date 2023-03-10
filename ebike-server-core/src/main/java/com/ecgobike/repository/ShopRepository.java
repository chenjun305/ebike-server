package com.ecgobike.repository;

import com.ecgobike.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ChenJun on 2018/3/11.
 */
@Repository
@Transactional
public interface ShopRepository extends JpaRepository<Shop, Long> {

}
