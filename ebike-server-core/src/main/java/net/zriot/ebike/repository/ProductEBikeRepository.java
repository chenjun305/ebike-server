package net.zriot.ebike.repository;

import net.zriot.ebike.entity.ProductEBike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ChenJun on 2018/3/28.
 */
@Repository
@Transactional
public interface ProductEBikeRepository extends JpaRepository<ProductEBike, Long> {
}
