-- 需要修改此表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(200) DEFAULT NULL COMMENT '密码',
  `account_non_locked` bit(1)  DEFAULT NULL COMMENT '账户是否锁定',
  `account_non_expired` bit(1)  DEFAULT NULL COMMENT '是否已过期',
  `credentials_non_expired` bit(1)  DEFAULT NULL COMMENT '证书有没有过期',
  `enabled` bit(1)  DEFAULT NULL COMMENT '账户是否有效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `leaf` bit(1) DEFAULT NULL COMMENT '是不是叶子菜单',
  `orders` int(2) DEFAULT NULL COMMENT '次序',
  `name` varchar(60) DEFAULT NULL COMMENT '名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `root` bit(1) DEFAULT NULL COMMENT '是否是根菜单',
  `url` varchar(255) DEFAULT NULL COMMENT '菜单的url',
  `type` varchar(1) DEFAULT NULL COMMENT '资源类型',
  `auth_type` varchar(1) DEFAULT NULL COMMENT '可访问类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='目录';

DROP TABLE IF EXISTS `privilege`;
CREATE TABLE `privilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(10) DEFAULT NULL COMMENT '权限代码',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQE_PRIVILEGE_CODE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=10081 DEFAULT CHARSET=utf8mb4 COMMENT='权限';

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- 资源权限表
DROP TABLE IF EXISTS `resource_privilege`;
CREATE TABLE `resource_privilege` (
  `resource_id` bigint(20) NOT NULL COMMENT '资源id',
  `privilege_id` bigint(20) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`resource_id`,`privilege_id`),
    KEY `FKdkwbrwb5r8h74m1v7dqmhp29c` (`resource_id`),
  CONSTRAINT `FKdkwbrwb5r8h74m1v7dqmhp99c` FOREIGN KEY (`privilege_id`) REFERENCES `privilege` (`id`),
  CONSTRAINT `FKsykrtrdngu5iexmbti7lu9xa` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



DROP TABLE IF EXISTS `role_privilege`;
CREATE TABLE `role_privilege` (
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `privilege_id` bigint(20) NOT NULL COMMENT '授权id',
  PRIMARY KEY (`role_id`,`privilege_id`),
  KEY `FKdkwbrwb5r8h74m1v7dqmhp91c` (`privilege_id`),
  CONSTRAINT `FKdkwbrwb5r8h34m1v7dqmhp99c` FOREIGN KEY (`privilege_id`) REFERENCES `privilege` (`id`),
  CONSTRAINT `FKsykrtrdngu2iexmbti7lu9xa` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限';


DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户的id',
  `role_id` bigint(20) NOT NULL COMMENT '角色的id',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色';
