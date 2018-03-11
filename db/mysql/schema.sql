CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增唯一id',
  `uid` varchar(32) NOT NULL COMMENT 'ID',
  `tel` varchar(12) NOT NULL COMMENT '手机号',
  `avatar` varchar(128) DEFAULT NULL COMMENT '头像',
  `nickname` varchar(32) DEFAULT NULL COMMENT '用户昵称',
  `is_real` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否进行了 0未实名认证 1 实名认证 2是外籍身份 3审核中 4审核不通过',
  `real_name` varchar(100) DEFAULT NULL COMMENT '真实姓名',
  `gender` tinyint(2) NOT NULL DEFAULT '0' COMMENT '性别  1男  2女 0未知',
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
  `status` tinyint(1) DEFAULT '1' COMMENT '门店状态:0禁用 1正常',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `geohash_idx` (`geohash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='门店表';

CREATE TABLE `shop_staff` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增唯一id',
  `uid` varchar(32) NOT NULL COMMENT 'ID',
  `tel` varchar(12) NOT NULL COMMENT '手机号',
  `avatar` varchar(128) DEFAULT NULL COMMENT '头像',
  `real_name` varchar(100) DEFAULT NULL COMMENT '真实姓名',
  `gender` tinyint(2) DEFAULT '1' COMMENT '性别  1男  0女 2隐藏',
  `money` int(11) DEFAULT '0' COMMENT '充值金额，只包括充值的金额（允许为负）',
  `shop_id` int(11) unsigned DEFAULT NULL COMMENT '所属商店ID',
  `is_shop_owner` tinyint(1) DEFAULT '0' COMMENT '是否店长:0否 1是',
  `status` tinyint(1) DEFAULT '1' COMMENT '用户状态:0禁用 1正常',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tel` (`tel`) USING BTREE,
  UNIQUE KEY `uid_idx` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店员表';

CREATE TABLE `ebike` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增唯一id',
  `sn` varchar(64) NOT NULL COMMENT '二维码包含的信息，一般情况为车辆编码',
  `name` varchar(32) DEFAULT NULL COMMENT '车辆名称',
  `note` varchar(128) DEFAULT NULL COMMENT '车辆备注信息',
  `shop_id` int(11) unsigned DEFAULT NULL COMMENT '商店ID',
  `uid` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `buy_time` DATETIME DEFAULT NULL COMMENT '购买时间',
  `is_membership` tinyint(2) unsigned DEFAULT '0' COMMENT '是否参加会员',
  `membership` int(5) unsigned DEFAULT '0' COMMENT '会员费',
  `month_fee` int(5) unsigned DEFAULT '0' COMMENT '包月费',
  `month_start_time` datetime DEFAULT NULL COMMENT '包月开始时间',
  `month_end_time` datetime DEFAULT NULL COMMENT '包月结束时间',
  `battery_id` int(11) unsigned DEFAULT NULL COMMENT '电池ID',
  `status` tinyint(1) DEFAULT '1' COMMENT '车辆状态: 0无效 1正常，用户使用中 2 销售中 3 在仓库',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn_idx` (`sn`),
  KEY `uid_idx` (`uid`),
  KEY `shop_idx` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电单车表';

CREATE TABLE `ebike_detail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增唯一id',
  `sn` varchar(64) NOT NULL COMMENT '二维码包含的信息，一般情况为车辆编码',
  `warehouse_start_time` datetime DEFAULT NULL COMMENT '入库时间',
  `warehouse_end_time` datetime DEFAULT NULL COMMENT '出库时间',
  `shop_id` int(11) unsigned DEFAULT '0' COMMENT '商店ID',
  `shop_start_time` datetime DEFAULT NULL COMMENT '入店时间',
  `shop_end_time` datetime DEFAULT NULL COMMENT '卖出时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn_idx` (`sn`),
  KEY `shop_idx` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电单车详情表';

CREATE TABLE `battery` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增唯一id',
  `sn` varchar(64) NOT NULL COMMENT '二维码包含的信息，一般情况为电池编码',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `note` varchar(128) DEFAULT NULL COMMENT '备注信息',
  `shop_id` int(11) unsigned DEFAULT NULL COMMENT '商店ID',
  `ebike_id` int(11) unsigned DEFAULT NULL COMMENT '电单车ID',
  `uid` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `battery` int(5) unsigned DEFAULT '0' COMMENT '电量',
  `status` tinyint(1) DEFAULT '1' COMMENT '车辆状态: 0无效 1正常，用户使用中 2 门店-充满 3 门店-充电中 4 仓库',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn_idx` (`sn`),
  KEY `uid_idx` (`uid`),
  KEY `shop_idx` (`shop_id`),
  KEY `ebike_idx` (`ebike_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电池表';

CREATE TABLE `payment_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` varchar(32) NOT NULL DEFAULT '' COMMENT '用户uid',
  `sn` varchar(64) NOT NULL COMMENT '订单编号 唯一',
  `type` int(2) NOT NULL COMMENT '订单类型  1给自己充值  2给他人充值',
  `pay_channel` int(2) DEFAULT '0' COMMENT '支付类型  1 xxx',
  `amount` int(8) NOT NULL DEFAULT '0' COMMENT '金额',
  `currency` varchar(8) DEFAULT NULL COMMENT '货币类型',
  `paid_amount` int(8) NOT NULL DEFAULT '0' COMMENT '真实支付的金额   用于异步传输过来的数据存储',
  `trade_no` varchar(64) DEFAULT NULL COMMENT '第三方支付流水号',
  `name` varchar(64) DEFAULT '' COMMENT '订单名称',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 0未支付 1已支付',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单状态更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn` (`sn`),
  KEY `uid_idx` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='支付订单';
