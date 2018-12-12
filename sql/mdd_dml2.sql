DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `leaf` bit(1) DEFAULT NULL COMMENT '是不是叶子菜单',
  `orders` int(2) DEFAULT NULL COMMENT '次序',
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `root` bit(1) DEFAULT NULL COMMENT '是否是根菜单',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单的url',
  `type` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源类型',
  `auth_type` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '可访问类型',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '目录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resource
-- ----------------------------
/* 签约、凭证、结算、收益待完成*/
INSERT INTO `resource` VALUES (1, b'1', 1, '用户管理', 0, b'1', '/user/userList', 'M', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (2, b'0', 2, '用户编辑', 1, b'0', '/user/editUser.html', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (3, b'0', 3, '用户添加', 1, b'0', '/user/addUser.html', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (4, b'0', 4, '用户导出', 1, b'0', '/user/exports.html', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (5, b'0', 5, '用户批量停用', 1, b'0', '/user/closeUser.do', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (6, b'0', 6, '用户批量启用', 1, b'0', '/user/openUser.do', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (7, b'1', 7, '部门列表', 0, b'1', '/department/departmentList', 'M', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (8, b'0', 8, '部门新增', 7, b'0', '/department/toAddDepartment', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (9, b'0', 9, '部门编辑', 7, b'0', '/department/editDepartment', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (10, b'0', 10, '部门删除', 7, b'0', '/department/deleteDepartment', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (11, b'1', 11, '客户列表', 0, b'1', '/custom/customList', 'M', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (12, b'0', 12, '客户新增', 11, b'0', '/custom/addCustom.html', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (13, b'0', 13, '客户编辑', 11, b'0', '/custom/editCustom.html', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (14, b'0', 14, '客户转出', 11, b'0', '/custom/customChange.html', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (15, b'0', 15, '客户导出', 11, b'0', '/custom/export.html', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (16, b'0', 16, '客户导入', 11, b'0', '/custom/customImport.html', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (17, b'1', 17, '产品列表', 0, b'1', '/product/productList', 'M', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (18, b'0', 18, '产品新增', 17, b'0', '/product/toAddProduct.html', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (19, b'0', 19, '产品编辑', 17, b'0', '/product/toEditProduct.html', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (20, b'0', 20, '产品状态修改', 17, b'0', '/product/editProductStruts', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (21, b'0', 21, '产品详情', 17, b'0', '/product/toProductDetail.html', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (22, b'1', 22, '操作日志列表', 0, b'1', '/operation/operationList', 'M', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (23, b'0', 23, '操作日志导出', 22, b'0', '/operation/export.html', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (24, b'1', 24, '权限列表', 0, b'1', '/permission/permissionList', 'M', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (25, b'1', 25, '签约列表', 0, b'1', '/contractApply/contractApplyList', 'M', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (26, b'0', 26, '新增签约', 25, b'0', '/sign/signAdd.html', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (27, b'0', 27, '导出签约', 25, b'0', '/sign/exports', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (28, b'0', 28, '编辑签约', 25, b'0', '/sign/signEdit.html', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (29, b'0', 29, '临退签约', 25, b'0', '/sign/signQui.html', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (30, b'0', 30, '签约详情', 25, b'0', '/sign/auditSign.html', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (31, b'0', 31, '转换签约', 25, b'0', '/sign/signChange.html', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (32, b'1', 32, '凭证列表', 0, b'1', '/credential/credentialList', 'M', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (33, b'1', 33, '结算列表', 0, b'1', '/settlement/settlementList', 'M', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (34, b'1', 34, '收益列表', 0, b'1', '/income/incomeList', 'M', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (35, b'0', 35, '收益导出', 34, b'0', '/income/export.html', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (36, b'0', 36, '签约审批通过', 25, b'0', '/sign/approvedAgreeSign.do', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (37, b'0', 37, '签约审批拒绝', 25, b'0', '/sign/approvedRejectSign.do', 'B', 'A', NULL, NULL, 0);
INSERT INTO `resource` VALUES (38, b'0', 38, '续签签约', 25, b'0', '/sign/signContinue.html', 'B', 'A', NULL, NULL, 0);



INSERT INTO `privilege` VALUES (1, 'P0000001', '员工列表', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (2, 'P0000002', '员工编辑', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (3, 'P0000003', '员工添加', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (4, 'P0000004', '员工导出', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (5, 'P0000005', '员工批量停用', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (6, 'P0000006', '员工批量启用', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (7, 'P0000007', '部门列表', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (8, 'P0000008', '部门新增', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (9, 'P0000009', '部门编辑', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (10, 'P00000010', '部门删除', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (11, 'P00000011', '客户列表', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (12, 'P00000012', '客户新增', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (13, 'P00000013', '客户编辑', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (14, 'P00000014', '客户转出', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (15, 'P00000015', '客户导出', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (16, 'P00000016', '客户导入', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (17, 'P00000017', '产品列表', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (18, 'P00000018', '产品新增', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (19, 'P00000019', '产品编辑', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (20, 'P00000020', '产品状态修改', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (21, 'P00000021', '产品详情', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (22, 'P00000022', '操作日志列表', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (23, 'P00000023', '操作日志导出', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (24, 'P00000024', '权限列表', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (25, 'P00000025', '签约列表', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (26, 'P00000026', '新增签约', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (27, 'P00000027', '导出签约', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (28, 'P00000028', '编辑签约', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (29, 'P00000029', '临退签约', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (30, 'P00000030', '签约详情', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (31, 'P00000031', '转换签约', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (32, 'P00000032', '凭证列表', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (33, 'P00000033', '结算列表', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (34, 'P00000034', '收益列表', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (35, 'P00000035', '收益导出', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (36, 'P00000036', '签约审批通过', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (37, 'P00000037', '签约审批拒绝', NULL, NULL, 0);
INSERT INTO `privilege` VALUES (38, 'P00000038', '续签签约', NULL, NULL, 0);



INSERT INTO `role_privilege` VALUES (1, 1);
INSERT INTO `role_privilege` VALUES (3, 1);
INSERT INTO `role_privilege` VALUES (6, 1);
INSERT INTO `role_privilege` VALUES (1, 2);
INSERT INTO `role_privilege` VALUES (6, 2);
INSERT INTO `role_privilege` VALUES (1, 3);
INSERT INTO `role_privilege` VALUES (6, 3);
INSERT INTO `role_privilege` VALUES (1, 4);
INSERT INTO `role_privilege` VALUES (6, 4);
INSERT INTO `role_privilege` VALUES (1, 5);
INSERT INTO `role_privilege` VALUES (6, 5);
INSERT INTO `role_privilege` VALUES (1, 6);
INSERT INTO `role_privilege` VALUES (6, 6);
INSERT INTO `role_privilege` VALUES (1, 7);
INSERT INTO `role_privilege` VALUES (4, 7);
INSERT INTO `role_privilege` VALUES (5, 7);
INSERT INTO `role_privilege` VALUES (6, 7);
INSERT INTO `role_privilege` VALUES (1, 8);
INSERT INTO `role_privilege` VALUES (6, 8);
INSERT INTO `role_privilege` VALUES (1, 9);
INSERT INTO `role_privilege` VALUES (6, 9);
INSERT INTO `role_privilege` VALUES (1, 10);
INSERT INTO `role_privilege` VALUES (6, 10);
INSERT INTO `role_privilege` VALUES (1, 11);
INSERT INTO `role_privilege` VALUES (2, 11);
INSERT INTO `role_privilege` VALUES (3, 11);
INSERT INTO `role_privilege` VALUES (4, 11);
INSERT INTO `role_privilege` VALUES (5, 11);
INSERT INTO `role_privilege` VALUES (6, 11);
INSERT INTO `role_privilege` VALUES (1, 12);
INSERT INTO `role_privilege` VALUES (2, 12);
INSERT INTO `role_privilege` VALUES (3, 12);
INSERT INTO `role_privilege` VALUES (6, 12);
INSERT INTO `role_privilege` VALUES (1, 13);
INSERT INTO `role_privilege` VALUES (2, 13);
INSERT INTO `role_privilege` VALUES (3, 13);
INSERT INTO `role_privilege` VALUES (6, 13);
INSERT INTO `role_privilege` VALUES (1, 14);
INSERT INTO `role_privilege` VALUES (2, 14);
INSERT INTO `role_privilege` VALUES (3, 14);
INSERT INTO `role_privilege` VALUES (6, 14);
INSERT INTO `role_privilege` VALUES (1, 15);
INSERT INTO `role_privilege` VALUES (2, 15);
INSERT INTO `role_privilege` VALUES (3, 15);
INSERT INTO `role_privilege` VALUES (6, 15);
INSERT INTO `role_privilege` VALUES (1, 16);
INSERT INTO `role_privilege` VALUES (2, 16);
INSERT INTO `role_privilege` VALUES (3, 16);
INSERT INTO `role_privilege` VALUES (6, 16);
INSERT INTO `role_privilege` VALUES (1, 17);
INSERT INTO `role_privilege` VALUES (2, 17);
INSERT INTO `role_privilege` VALUES (3, 17);
INSERT INTO `role_privilege` VALUES (4, 17);
INSERT INTO `role_privilege` VALUES (5, 17);
INSERT INTO `role_privilege` VALUES (6, 17);
INSERT INTO `role_privilege` VALUES (1, 18);
INSERT INTO `role_privilege` VALUES (6, 18);
INSERT INTO `role_privilege` VALUES (1, 19);
INSERT INTO `role_privilege` VALUES (6, 19);
INSERT INTO `role_privilege` VALUES (1, 20);
INSERT INTO `role_privilege` VALUES (6, 20);
INSERT INTO `role_privilege` VALUES (1, 21);
INSERT INTO `role_privilege` VALUES (2, 21);
INSERT INTO `role_privilege` VALUES (3, 21);
INSERT INTO `role_privilege` VALUES (4, 21);
INSERT INTO `role_privilege` VALUES (5, 21);
INSERT INTO `role_privilege` VALUES (6, 21);
INSERT INTO `role_privilege` VALUES (1, 22);
INSERT INTO `role_privilege` VALUES (2, 22);
INSERT INTO `role_privilege` VALUES (3, 22);
INSERT INTO `role_privilege` VALUES (4, 22);
INSERT INTO `role_privilege` VALUES (5, 22);
INSERT INTO `role_privilege` VALUES (6, 22);
INSERT INTO `role_privilege` VALUES (1, 23);
INSERT INTO `role_privilege` VALUES (3, 23);
INSERT INTO `role_privilege` VALUES (6, 23);
INSERT INTO `role_privilege` VALUES (1, 24);
INSERT INTO `role_privilege` VALUES (6, 24);
INSERT INTO `role_privilege` VALUES (1, 25);
INSERT INTO `role_privilege` VALUES (2, 25);
INSERT INTO `role_privilege` VALUES (3, 25);
INSERT INTO `role_privilege` VALUES (4, 25);
INSERT INTO `role_privilege` VALUES (5, 25);
INSERT INTO `role_privilege` VALUES (6, 25);
INSERT INTO `role_privilege` VALUES (1, 26);
INSERT INTO `role_privilege` VALUES (2, 26);
INSERT INTO `role_privilege` VALUES (3, 26);
INSERT INTO `role_privilege` VALUES (6, 26);
INSERT INTO `role_privilege` VALUES (1, 27);
INSERT INTO `role_privilege` VALUES (2, 27);
INSERT INTO `role_privilege` VALUES (3, 27);
INSERT INTO `role_privilege` VALUES (6, 27);
INSERT INTO `role_privilege` VALUES (1, 28);
INSERT INTO `role_privilege` VALUES (2, 28);
INSERT INTO `role_privilege` VALUES (3, 28);
INSERT INTO `role_privilege` VALUES (6, 28);
INSERT INTO `role_privilege` VALUES (1, 29);
INSERT INTO `role_privilege` VALUES (2, 29);
INSERT INTO `role_privilege` VALUES (3, 29);
INSERT INTO `role_privilege` VALUES (6, 29);
INSERT INTO `role_privilege` VALUES (1, 30);
INSERT INTO `role_privilege` VALUES (2, 30);
INSERT INTO `role_privilege` VALUES (3, 30);
INSERT INTO `role_privilege` VALUES (4, 30);
INSERT INTO `role_privilege` VALUES (5, 30);
INSERT INTO `role_privilege` VALUES (6, 30);
INSERT INTO `role_privilege` VALUES (1, 31);
INSERT INTO `role_privilege` VALUES (2, 31);
INSERT INTO `role_privilege` VALUES (3, 31);
INSERT INTO `role_privilege` VALUES (4, 31);
INSERT INTO `role_privilege` VALUES (5, 31);
INSERT INTO `role_privilege` VALUES (6, 31);
INSERT INTO `role_privilege` VALUES (1, 32);
INSERT INTO `role_privilege` VALUES (4, 32);
INSERT INTO `role_privilege` VALUES (5, 32);
INSERT INTO `role_privilege` VALUES (6, 32);
INSERT INTO `role_privilege` VALUES (1, 33);
INSERT INTO `role_privilege` VALUES (2, 33);
INSERT INTO `role_privilege` VALUES (3, 33);
INSERT INTO `role_privilege` VALUES (4, 33);
INSERT INTO `role_privilege` VALUES (5, 33);
INSERT INTO `role_privilege` VALUES (6, 33);
INSERT INTO `role_privilege` VALUES (1, 34);
INSERT INTO `role_privilege` VALUES (2, 34);
INSERT INTO `role_privilege` VALUES (3, 34);
INSERT INTO `role_privilege` VALUES (4, 34);
INSERT INTO `role_privilege` VALUES (5, 34);
INSERT INTO `role_privilege` VALUES (6, 34);
INSERT INTO `role_privilege` VALUES (1, 35);
INSERT INTO `role_privilege` VALUES (2, 35);
INSERT INTO `role_privilege` VALUES (3, 35);
INSERT INTO `role_privilege` VALUES (6, 35);
INSERT INTO `role_privilege` VALUES (1, 36);
INSERT INTO `role_privilege` VALUES (3, 36);
INSERT INTO `role_privilege` VALUES (4, 36);
INSERT INTO `role_privilege` VALUES (5, 36);
INSERT INTO `role_privilege` VALUES (6, 36);
INSERT INTO `role_privilege` VALUES (1, 37);
INSERT INTO `role_privilege` VALUES (3, 37);
INSERT INTO `role_privilege` VALUES (4, 37);
INSERT INTO `role_privilege` VALUES (5, 37);
INSERT INTO `role_privilege` VALUES (6, 37);