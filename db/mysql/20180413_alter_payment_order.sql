alter table `payment_order` add column pay_date date comment '付款日期' after shop_id;
ALTER TABLE `payment_order` ADD INDEX pay_date_idx (`pay_date`);
update payment_order set pay_date=date(create_time);