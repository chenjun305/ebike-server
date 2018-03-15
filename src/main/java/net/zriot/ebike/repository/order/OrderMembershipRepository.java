package net.zriot.ebike.repository.order;

import net.zriot.ebike.entity.order.OrderMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ChenJun on 2018/3/15.
 */
@Repository
@Transactional
public interface OrderMembershipRepository extends JpaRepository<OrderMembership, Long> {
}
