package com.ecgobike.repository;

import com.ecgobike.common.enums.AppType;
import com.ecgobike.common.enums.OsType;
import com.ecgobike.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by ChenJun on 2018/5/18.
 */
@Repository
@Transactional
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    UserToken findFirstByUidAndAppTypeAndOsType(String uid, AppType appType, OsType osType);
}
