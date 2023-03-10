CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增唯一id',
  `uid` varchar(32) NOT NULL COMMENT 'ID',
  `tel` varchar(12) NOT NULL COMMENT '手机号',
  `avatar` varchar(128) DEFAULT NULL COMMENT '头像',
  `nickname` varchar(32) DEFAULT NULL COMMENT '用户昵称',
  `is_real` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否进行了 0未实名认证 1 实名认证 2是外籍身份 3审核中 4审核不通过',
  `real_name` varchar(100) DEFAULT NULL COMMENT '真实姓名',
  `gender` tinyint(2) NOT NULL DEFAULT '0' COMMENT '性别  1男  2女 0未知',
  `id_card_num` VARCHAR(64) DEFAULT NULL COMMENT 'ID Card Number',
  `id_card_pics` VARCHAR(512) DEFAULT NULL COMMENT 'ID Card 照片',
  `money` DECIMAL(11,2) NOT NULL DEFAULT '0.00' COMMENT '用户余额',
  `currency` varchar(32) NOT NULL DEFAULT 'USD' COMMENT '货币类型，默认美元USD',
  `address` varchar(128) DEFAULT NULL COMMENT '用户地址',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '用户状态:0禁用 1正常',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tel` (`tel`) USING BTREE,
  UNIQUE KEY `uid_idx` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `user_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增唯一id',
  `uid` varchar(32) NOT NULL COMMENT 'UID',
  `role` TINYINT UNSIGNED NOT NULL COMMENT '用户角色: 0未知 1店长 2店员 3运营管理',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态: 0禁用 1正常',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid_role_idx` (`uid`, `role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

CREATE TABLE `staff` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增唯一id',
  `uid` varchar(32) NOT NULL COMMENT 'UID',
  `staff_num` VARCHAR(32) COMMENT '员工编号',
  `shop_id` int(11) unsigned COMMENT '所属商店ID',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态:0禁用 1正常',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid_idx` (`uid`),
  UNIQUE KEY `staff_num_idx` (`staff_num`),
  KEY `shop_idx` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工表';

CREATE TABLE `shop` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增唯一id',
  `name` varchar(32) NOT NULL COMMENT '门店名称',
  `tel` varchar(12) NOT NULL COMMENT '联系电话',
  `address` varchar(128) DEFAULT NULL COMMENT '门店地址',
  `description` varchar(512) DEFAULT NULL COMMENT '门店详情',
  `latitude` varchar(20) DEFAULT NULL COMMENT '经度',
  `longitude` varchar(20) DEFAULT NULL COMMENT '纬度',
  `geohash` varchar(20) DEFAULT NULL COMMENT 'geohash算法计算附近的车',
  `open_time` VARCHAR(64) DEFAULT NULL COMMENT '营业时间',
  `battery_available` SMALLINT NOT NULL DEFAULT '0' COMMENT '可供换电电池数',
  `battery_booked` SMALLINT NOT NULL DEFAULT '0' COMMENT '被预约电池数',
  `status` tinyint(1) DEFAULT '1' COMMENT '门店状态:0禁用 1正常',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `geohash_idx` (`geohash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='门店表';

CREATE TABLE `product` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增唯一id',
  `type` TINYINT NOT NULL DEFAULT '1' COMMENT '类型 1电单车 2电池',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `price` DECIMAL(8, 2) NOT NULL DEFAULT '0' COMMENT '金额',
  `currency` varchar(8) NOT NULL DEFAULT 'USD' COMMENT '货币类型',
  `icon_url` varchar(512) DEFAULT NULL COMMENT '图片地址',
  `model` varchar(32) DEFAULT NULL COMMENT '型号',
  `color` varchar(32) DEFAULT NULL COMMENT '颜色',
  `desc` varchar(1024) DEFAULT NULL COMMENT '详细描述',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态: 0无效 1正常',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品表';

CREATE TABLE `logistics` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增唯一id',
  `sn` varchar(64) NOT NULL COMMENT '编码',
  `product_id` INT(11) UNSIGNED NOT NULL COMMENT '所属产品ID',
  `storage_in_time` datetime DEFAULT NULL COMMENT '入库时间',
  `storage_out_time` datetime DEFAULT NULL COMMENT '出库时间',
  `purchase_sn` VARCHAR(64) COMMENT '进货编号',
  `shop_id` int(11) unsigned COMMENT '商店ID',
  `shop_in_time` datetime COMMENT '入店时间',
  `shop_out_time` datetime COMMENT '卖出时间',
  `status` tinyint(1) DEFAULT '1' COMMENT '0 unknown 1 in_storage 2 in_transit 3 in_shop 4 sell_out',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn_idx` (`sn`),
  KEY `shop_idx` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品物流表';

CREATE TABLE `ebike` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增唯一id',
  `sn` varchar(64) NOT NULL COMMENT '二维码包含的信息，一般情况为车辆编码',
  `product_id` INT(11) UNSIGNED NOT NULL COMMENT '所属产品ID',
  `uid` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `is_membership` TINYINT unsigned DEFAULT '0' COMMENT '是否参加会员',
  `month_left` TINYINT UNSIGNED DEFAULT '0' COMMENT '月换电套餐剩余次数',
  `month_total` TINYINT UNSIGNED DEFAULT '0' COMMENT '月换电套餐总次数',
  `expire_date` DATE DEFAULT NULL COMMENT '包月结束日期',
  `status` tinyint(1) DEFAULT '1' COMMENT '车辆状态: 0无效 1正常，用户使用中',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn_idx` (`sn`),
  KEY `uid_idx` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电单车表';

CREATE TABLE `battery` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增唯一id',
  `sn` varchar(64) NOT NULL COMMENT '二维码包含的信息，一般情况为电池编码',
  `product_id` INT(11) UNSIGNED NOT NULL COMMENT '所属产品ID',
  `shop_id` int(11) unsigned DEFAULT NULL COMMENT '商店ID',
  `ebike_sn` VARCHAR(64) DEFAULT NULL COMMENT '电单车sn',
  `battery` int(5) unsigned DEFAULT '0' COMMENT '电量',
  `status` tinyint(1) DEFAULT '1' COMMENT '车辆状态: 0无效 1 IN_SHOP 2 Lend_Out',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn_idx` (`sn`),
  UNIQUE KEY `ebike_sn_idx` (`ebike_sn`),
  KEY `shop_idx` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电池表';

CREATE TABLE `payment_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sn` varchar(64) NOT NULL COMMENT '订单编号 唯一',
  `type` TINYINT NOT NULL COMMENT '订单类型 1卖车 2店员为用户充值 3店员为用户加入会员 4店员为用户更新包月 5用户加入会员 6用户更新包月 ',
  `price` DECIMAL(8, 2) NOT NULL DEFAULT '0' COMMENT '金额',
  `currency` varchar(8) NOT NULL DEFAULT 'USD' COMMENT '货币类型',
  `ebike_sn` VARCHAR(64) DEFAULT NULL COMMENT '电单车sn',
  `product_id` INT(11) UNSIGNED COMMENT '产品ID',
  `uid` varchar(32) DEFAULT NULL COMMENT '用户uid',
  `tel` varchar(12) COMMENT '手机号',
  `staff_uid` varchar(32) DEFAULT NULL COMMENT '店员uid，店员帮用户操作时有值',
  `shop_id` INT(11) DEFAULT NULL COMMENT '门店ID',
  `pay_date` date comment '付款日期',
  `month_num` TINYINT DEFAULT NULL COMMENT '月换电次数',
  `start_date` DATE DEFAULT NULL COMMENT '包月开始时间',
  `end_date` DATE DEFAULT NULL COMMENT '包月结束时间',
  `status` TINYINT NOT NULL DEFAULT '0' COMMENT '状态 0未完成 1已完成',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单状态更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn` (`sn`),
  KEY `ebike_sn_idx` (`ebike_sn`),
  KEY `uid_idx` (`uid`),
  KEY `staff_uid_idx` (`staff_uid`),
  KEY `shop_id_idx` (`shop_id`),
  KEY `pay_date_idx` (`pay_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='订单';

CREATE TABLE `lend_battery` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sn` varchar(64) NOT NULL COMMENT '编号 唯一',
  `battery_sn` VARCHAR(64) NOT NULL COMMENT '电池sn',
  `ebike_sn` VARCHAR(64) NOT NULL COMMENT '电单车sn',
  `lend_time` DATETIME COMMENT '借出时间',
  `return_time` DATETIME COMMENT '归还时间',
  `uid` varchar(32) NOT NULL COMMENT '用户uid',
  `lend_staff_uid` VARCHAR(32) DEFAULT NULL COMMENT '借出店员uid',
  `lend_shop_id` INT(11) DEFAULT '0' COMMENT '借出门店ID',
  `return_staff_uid` VARCHAR(32) DEFAULT NULL COMMENT '归还店员uid',
  `return_shop_id` INT(11) DEFAULT '0' COMMENT '归还门店ID',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 0未完成 1已完成',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '状态更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn` (`sn`),
  KEY `battery_sn_idx` (`battery_sn`),
  KEY `ebike_sn_idx` (`ebike_sn`),
  KEY `uid_idx` (`uid`),
  KEY `lend_staff_uid_idx` (`lend_staff_uid`),
  KEY `lend_shop_id_idx` (`lend_shop_id`),
  KEY `return_staff_uid_idx` (`return_staff_uid`),
  KEY `return_shop_id_idx` (`return_shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='电池借用记录';


CREATE TABLE `purchase_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sn` varchar(64) NOT NULL COMMENT '订单编号 唯一',
  `product_id` INT(11) NOT NULL COMMENT '产品ID',
  `require_num` INT(11) NOT NULL COMMENT '需求数量',
  `permit_num` INT(11) COMMENT '审核通过数量',
  `staff_uid` varchar(32) NOT NULL COMMENT '店员uid',
  `shop_id` INT(11) NOT NULL COMMENT '门店ID',
  `permit_uid` VARCHAR(32) COMMENT '批准人',
  `departure_uid` VARCHAR(32) COMMENT '出库人',
  `take_over_uid` VARCHAR(32) COMMENT '接收店员',
  `purchase_time` DATETIME NOT NULL COMMENT '进货时间',
  `permit_time` DATETIME COMMENT '审核时间',
  `departure_time` DATETIME COMMENT '出库时间',
  `take_over_time` DATETIME COMMENT '接收时间',
  `status` TINYINT NOT NULL DEFAULT '0' COMMENT '状态 1已下单 2已审核 3已出库 4已到店',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单状态更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn` (`sn`),
  KEY `product_id_idx` (`product_id`),
  KEY `shop_id_idx` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='进货订单';

CREATE TABLE `shop_income_daily` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `shop_id` INT(11) UNSIGNED NOT NULL COMMENT '门店ID',
  `pay_date` date NOT NULL COMMENT '日期',
  `price` DECIMAL(8, 2) NOT NULL DEFAULT '0' COMMENT '金额',
  `currency` varchar(8) NOT NULL DEFAULT 'USD' COMMENT '货币类型',
  `status` TINYINT NOT NULL DEFAULT '0' COMMENT '状态 0无效 1正常',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `shop_pay_date_idx` (`shop_id`, `pay_date`),
  KEY `pay_date_idx` (`pay_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='收入日统计';

CREATE TABLE `book_battery` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` varchar(32) NOT NULL COMMENT '用户uid',
  `ebike_sn` VARCHAR(64) NOT NULL COMMENT '电单车sn',
  `shop_id` INT(11) UNSIGNED NOT NULL COMMENT '门店ID',
  `book_time` DATETIME NOT NULL COMMENT '预订时间',
  `expire_time` DATETIME NOT NULL COMMENT '过期时间',
  `battery_sn` VARCHAR(64) DEFAULT NULL COMMENT '电池sn',
  `lend_time` DATETIME DEFAULT NULL COMMENT '借出时间',
  `cancel_time` DATETIME DEFAULT NULL COMMENT '取消时间',
  `status` TINYINT NOT NULL DEFAULT '1' COMMENT '状态 0 已过期 1 正常 2 已借 3 已取消',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '状态更新时间',
  PRIMARY KEY (`id`),
  KEY `uid_status_expire_time_idx` (`uid`, `status`, `expire_time`),
  KEY `shop_id_status_expire_time_idx` (`shop_id`, `status`, `expire_time`),
  KEY `ebike_sn_status_expire_time_idx` (`ebike_sn`, `status`, `expire_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='预约电池';

CREATE TABLE `user_token` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` varchar(32) NOT NULL COMMENT '用户uid',
  `app_type` tinyint not null default '1' comment '1 用户端 2 员工端',
  `fcm_token` varchar(512) not null comment 'Firebase Cloud Messaging Token',
  `os_type` tinyint not null default '1' comment '1 Android 2 iOS',
  `apns_token` varchar(512) default null comment 'Apple APNS Token, iOS特有',
  `status` TINYINT NOT NULL DEFAULT '1' COMMENT '状态 1 正常',
  `refresh_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '刷新时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '状态更新时间',
  PRIMARY KEY (`id`),
  KEY `uid_app_type_idx` (`uid`, `app_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='推送Token';