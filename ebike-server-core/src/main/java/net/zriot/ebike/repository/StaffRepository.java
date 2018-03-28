package net.zriot.ebike.repository;

import net.zriot.ebike.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ChenJun on 2018/3/19.
 */
@Repository
@Transactional
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff findOneByTel(String tel);
    Staff findOneByUid(String uid);
}
