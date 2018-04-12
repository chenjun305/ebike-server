alter table `ebike` drop column `month_num`;
alter table `ebike` add column month_total tinyint unsigned default '0' comment '月换电套餐总次数' after is_membership;
alter table `ebike` add column month_left tinyint unsigned default '0' comment '月换电套餐剩余次数' after is_membership;
alter table `payment_order` add column product_id INT(11) UNSIGNED  comment '产品ID' after ebike_sn;
update payment_order set product_id=1 where ebike_sn IS NOT NULL;