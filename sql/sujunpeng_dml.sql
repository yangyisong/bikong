INSERT INTO `resource`
(`id`,`version`, `leaf`, `orders`, `name`, `parent_id`, `root`, `url`,`type`, `auth_type`)
VALUES ('1', '0', b'1', '1', '用户管理', '0', b'1', '/user/userList.html','M', 'A');

INSERT INTO `privilege`
(`id`, `version`, `code`, `desc`)
VALUES ('1','0', 'P0000001', '系统管理');

INSERT INTO `resource_privilege` (`resource_id`, `privilege_id`)
VALUES ('1', '1');
