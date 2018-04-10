alter table `ebike` drop column `shop_id`;
alter table `ebike` add column month_num tinyint unsigned default '0' comment '月换电次数' after is_membership;
alter table `payment_order` add column month_num tinyint comment '月换电次数' after shop_id;
