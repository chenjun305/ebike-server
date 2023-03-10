package com.ecgobike.repository;

import com.ecgobike.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ChenJun on 2018/3/10.
 */

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByTel(String tel);
    User findOneByUid(String uid);
}
