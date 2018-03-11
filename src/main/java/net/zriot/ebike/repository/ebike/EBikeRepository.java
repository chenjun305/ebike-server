package net.zriot.ebike.repository.ebike;

import net.zriot.ebike.model.ebike.EBike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/11.
 */
@Repository
@Transactional
public interface EBikeRepository extends JpaRepository<EBike, Long> {
    List<EBike> findAllByUid(String uid);
    EBike findOneBySn(String sn);
}
