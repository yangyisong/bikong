DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `realname` varchar(20) DEFAULT NULL COMMENT '真是姓名',
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `job_number` varchar(20) DEFAULT NULL COMMENT '工号',
  `last_login` datetime(0) DEFAULT NULL COMMENT '最后登录时间',
  `account_non_locked` bit(1) DEFAULT NULL COMMENT '账户是否锁定',
  `account_non_expired` bit(1) DEFAULT NULL COMMENT '是否已过期',
  `credentials_non_expired` bit(1) DEFAULT NULL COMMENT '证书有没有过期',
  `enabled` bit(1) DEFAULT NULL COMMENT '账户是否有效',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

DROP TABLE IF EXISTS `approved_application`;
CREATE TABLE `approved_application`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '申请人',
  `contract_id` bigint(20) DEFAULT NULL COMMENT '合同id',
  `settle_number` varchar(20) DEFAULT NULL COMMENT '结算编号',
  `type` varchar(20) DEFAULT NULL COMMENT '审批类型',
  `pen_number` INTEGER(5) DEFAULT NULL COMMENT '审批类型',
  `apply_date` datetime(0) DEFAULT NULL COMMENT '申请时间',
  `struts` VARCHAR(5) DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审批申请';

DROP TABLE IF EXISTS `approved_recording`;
CREATE TABLE `approved_recording`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `application_id` bigint(20) DEFAULT NULL COMMENT '申请id',
  `seq` int(2) DEFAULT NULL COMMENT '审批排序',
  `role_id` bigint(20) DEFAULT NULL COMMENT '审批层级',
  `user_id` bigint(20) DEFAULT NULL COMMENT '审批人',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `struts` bit(1) DEFAULT NULL COMMENT '审批结果',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `approved_time` datetime(0) DEFAULT NULL COMMENT '审批时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审批记录';

DROP TABLE IF EXISTS `approved_type`;
CREATE TABLE `approved_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `role_id` bigint(10) DEFAULT NULL COMMENT '审批角色',
  `seq` int(2) DEFAULT NULL COMMENT '排序',
  `struts` bit(1) DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审批类型';

DROP TABLE IF EXISTS `voucher`;
CREATE TABLE `voucher`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` text DEFAULT NULL COMMENT '内容',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `number` varchar(20) DEFAULT NULL COMMENT '编号',
  `united` int(5) DEFAULT NULL COMMENT '联数',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='凭证';

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `gender` VARCHAR(5) DEFAULT NULL COMMENT '性别',
  `idcard` varchar(20) DEFAULT NULL COMMENT '身份证号码',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `birthday` datetime(0) DEFAULT NULL COMMENT '出生年月',
  `occupation` varchar(20) DEFAULT NULL COMMENT '职业',
  `tel` varchar(15) DEFAULT NULL COMMENT '座机',
  `wechat` varchar(30) DEFAULT NULL COMMENT '微信号',
  `qq` varchar(20) DEFAULT NULL COMMENT 'qq',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `source` varchar(10) DEFAULT NULL COMMENT '客户来源',
  `buy_times` int(5) DEFAULT NULL COMMENT '交易次数',
  `user_id` bigint(20) DEFAULT NULL COMMENT '录入人员',
  `crash_name` varchar(20) DEFAULT NULL COMMENT '紧急联系人',
  `crash_phone` varchar(12) DEFAULT NULL COMMENT '紧急联系人电话',
  `address` varchar(50) DEFAULT NULL COMMENT '住址',
  `type` varchar(20) DEFAULT NULL COMMENT '客户类型',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户信息';

DROP TABLE IF EXISTS `buy`;
CREATE TABLE `buy`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL COMMENT '产品',
  `customer_id` bigint(20) DEFAULT NULL COMMENT '客户',
  `time_period` varchar(20) DEFAULT NULL COMMENT '购买时段（预计和曾经）',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='凭证';

DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL COMMENT '产品',
  `customer_id` bigint(20) DEFAULT NULL COMMENT '客户',
  `user_id` bigint(20) DEFAULT NULL COMMENT '业务员',
  `number` varchar(20) DEFAULT NULL COMMENT '编号',
  `product_name` varchar(20) DEFAULT NULL COMMENT '产品名称',
  `amount` DECIMAL (10, 2) DEFAULT NULL COMMENT '金额',
  `change` bit(1) DEFAULT NULL COMMENT '是否转换',
  `change_contract_id` bigint(20) DEFAULT NULL COMMENT '转换合同id',
  `earning_ratio` decimal(5, 2) DEFAULT NULL COMMENT '收益比',
  `earnings_day_amount` decimal(10, 2) DEFAULT NULL COMMENT '每天收益',
  `start_time` datetime(0) DEFAULT NULL COMMENT '生效时间',
  `end_time` datetime(0) DEFAULT NULL COMMENT '结束时间',
  `open_bank` VARCHAR(20) DEFAULT NULL COMMENT '开户银行',
  `open_name` VARCHAR(20) DEFAULT NULL COMMENT '户名',
  `bank_account` VARCHAR(30) DEFAULT NULL COMMENT '账号',
  `source` VARCHAR(20) DEFAULT NULL COMMENT '来源',
  `duration` int(3) DEFAULT NULL COMMENT '合同时长',
  `type` varchar(10) DEFAULT NULL COMMENT '类型',
  `other` varchar(255) DEFAULT NULL COMMENT '自定义资料内容',
  `struts` bit(1) DEFAULT NULL COMMENT '状态',
  `remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同';

DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '部门名称',
  `struts` bit(1) DEFAULT NULL COMMENT '状态',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门id',
  `employee_num` int(10) DEFAULT NULL COMMENT '部门员工数',
  `head_id` bigint(20) DEFAULT NULL COMMENT '部门负责人id',
  `head` bit(1) DEFAULT NULL COMMENT '是否为总部',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门';

DROP TABLE IF EXISTS `earnings`;
CREATE TABLE `earnings`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL COMMENT '产品id',
  `min_range` decimal(20, 2) DEFAULT NULL COMMENT '起始额度',
  `max_range` decimal(20, 2) DEFAULT NULL COMMENT '上限额度',
  `out_earnings_ratio` decimal(5, 2) DEFAULT NULL COMMENT '外部收益比',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品阶梯收益';

DROP TABLE IF EXISTS `personal_income`;
CREATE TABLE `personal_income`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `income` decimal(10, 2) DEFAULT NULL COMMENT '收益',
  `settle_start_time` datetime(0) DEFAULT NULL COMMENT '结算开始时间',
  `settle_end_time` datetime(0) DEFAULT NULL COMMENT '结算结束时间',
  `user_id` bigint(20) DEFAULT NULL COMMENT '操作人',
  `contract_number` VARCHAR(20) DEFAULT NULL COMMENT '合同编号',
  `customer_id` bigint(20) DEFAULT NULL COMMENT '客户id',
  `struts` VARCHAR(10) DEFAULT NULL COMMENT '状态',
  `seq` int(3) DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='个人收益';

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `number` varchar(20) DEFAULT NULL COMMENT '产品编号',
  `name` varchar(20) DEFAULT NULL COMMENT '产品名',
  `type` varchar(20) DEFAULT NULL COMMENT '产品类型',
  `sales_amount` decimal(20, 2) DEFAULT NULL COMMENT '销售总额度',
  `out_start_amount` decimal(20, 2) DEFAULT NULL COMMENT '外部起购额度',
  `buy_count` int(11) DEFAULT NULL COMMENT '购买次数',
  `content` text DEFAULT NULL COMMENT '产品介绍',
  `support_voucher` bit(1) DEFAULT NULL COMMENT '是否打印凭证',
  `support_manager_audit` bit(1) DEFAULT NULL COMMENT '是否业务经理审核',
  `support_inter` bit(1) DEFAULT NULL COMMENT '是否支持内部',
  `support_conversion` bit(1) DEFAULT NULL COMMENT '是否支持转换',
  `support_quit` bit(1) DEFAULT NULL COMMENT '是否支持随时退',
  `billing_cycle` varchar(5) DEFAULT NULL COMMENT '结算周期',
  `cycle` decimal(5, 2) DEFAULT NULL COMMENT '产品周期',
  `quite_fee_type` varchar(10) DEFAULT NULL COMMENT '退出手续费类型',
  `quite_fee_value` decimal(5, 2) DEFAULT NULL COMMENT '退出手续费计算值',
  `open_time` datetime(0) DEFAULT NULL COMMENT '起购时间',
  `close_time` datetime(0) DEFAULT NULL COMMENT '退出时间',
  `inter_quota` decimal(20, 2) DEFAULT NULL COMMENT '内部额度',
  `inter_quit` BIT(1) DEFAULT NULL COMMENT '内部临退',
  `inter_earnings_ratio` decimal(5, 2) DEFAULT NULL COMMENT '内部收益比',
  `inter_cycle` varchar(5) DEFAULT NULL COMMENT '内部结算周期',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `struts` bit(1) DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品';

DROP TABLE IF EXISTS `product_voucher_mid`;
CREATE TABLE `product_voucher_mid`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL COMMENT '产品',
  `voucher_id` bigint(20) DEFAULT NULL COMMENT '凭证',
  `struts` bit(1) DEFAULT NULL COMMENT '状态',
	PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品凭证中间表';

DROP TABLE IF EXISTS `operation`;
CREATE TABLE `operation` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `operator_name` varchar(30) DEFAULT NULL COMMENT '操作人名称',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `operate_ip` varchar(30) DEFAULT NULL COMMENT '客户端ip',
  `content` varchar(100) DEFAULT NULL COMMENT '操作内容',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志';

DROP TABLE IF EXISTS `product_sign_info`;
CREATE TABLE `product_sign_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(11) DEFAULT NULL COMMENT '产品id',
  `code` varchar(30) DEFAULT NULL COMMENT '名称',
  `type` varchar(30) DEFAULT NULL COMMENT '类型',
  `value` varchar(255) DEFAULT NULL COMMENT '类型值',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品签约自定义信息表';

DROP TABLE IF EXISTS `contract_sign_info`;
CREATE TABLE `contract_sign_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `contract_id` bigint(11) DEFAULT NULL COMMENT '合同id',
  `code` varchar(30) DEFAULT NULL COMMENT '名称',
  `type` varchar(30) DEFAULT NULL COMMENT '类型',
  `value` varchar(100) DEFAULT NULL COMMENT '类型值',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同签约信息表';

DROP TABLE IF EXISTS `contract_quit_info`;
CREATE TABLE `contract_quit_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `contract_id` bigint(11) DEFAULT NULL COMMENT '合同id',
  `qiut_date` datetime DEFAULT NULL COMMENT '临退时间',
  `quit_interest` decimal(5, 2) DEFAULT NULL COMMENT '临退结息',
  `quit_poundage` decimal(5, 2) DEFAULT NULL COMMENT '临退手续费',
  `quit_amount` decimal(10, 2) DEFAULT NULL COMMENT '应退总额',
  `principal_amount` decimal(10, 2) DEFAULT NULL COMMENT '本金',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同临退信息表';

DROP TABLE IF EXISTS `contract_cofirm_info`;
CREATE TABLE `contract_cofirm_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contract_id` bigint(20) DEFAULT NULL COMMENT '合同id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色',
  `info_confirm` bit(1) DEFAULT NULL COMMENT '信息确认',
  `receive_confirm` bit(1) DEFAULT NULL COMMENT '收款确认',
  `amount_confirm` bit(1) DEFAULT NULL COMMENT '金额确认',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同确认信息';


ALTER TABLE customer ADD entry_user VARCHAR(20) COMMENT'录入人员' AFTER `qq`;
ALTER TABLE product ADD earnings_type VARCHAR(20) COMMENT'收益比类型' AFTER `id`;
ALTER TABLE product ADD remaining_amount decimal(20, 2) COMMENT'剩余额度' AFTER `sales_amount`;
ALTER TABLE product ADD inter_remaining_amount decimal(20, 2) COMMENT'内部剩余额度' AFTER `inter_quota`;
ALTER TABLE personal_income ADD income_number VARCHAR(20) COMMENT'编号' AFTER `id`;
ALTER TABLE personal_income ADD customer_name VARCHAR(20) COMMENT'客户姓名' AFTER `id`;
ALTER TABLE personal_income ADD user_realname VARCHAR(20) COMMENT'业务员姓名' AFTER `id`;
ALTER TABLE personal_income ADD day_nums INTEGER(3) COMMENT'计息天数' AFTER `customer_id`;
ALTER TABLE personal_income ADD income_type VARCHAR(10) COMMENT'结息类型' AFTER `day_nums`;
ALTER TABLE personal_income ADD end_time VARCHAR(30) COMMENT'结束时间' AFTER `day_nums`;
ALTER TABLE personal_income ADD product_id bigint(11) COMMENT'产品id' AFTER `customer_id`;
ALTER TABLE personal_income ADD principal bit(1) COMMENT'是否为本金' AFTER `customer_id`;
ALTER TABLE personal_income ADD quit bit(1) COMMENT'是否为临退' AFTER `customer_id`;
ALTER TABLE user ADD systemer bit(1) COMMENT'是否为系统管理员' AFTER `realname`;
ALTER TABLE contract ADD validity bit(1) COMMENT'是否有效' AFTER `struts`;
ALTER TABLE contract_apply ADD operation bit(1) COMMENT'是否已操作' AFTER `effective`;
ALTER TABLE contract_apply ADD type VARCHAR(10) COMMENT'合同类型' AFTER `contract_id`;
ALTER TABLE contract ADD change_contract_id bigint(11) COMMENT'转换合同id' AFTER `changes`;
