package net.zriot.ebike.repository.order;

import net.zriot.ebike.entity.order.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ChenJun on 2018/3/16.
 */
@Repository
@Transactional
public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
}
