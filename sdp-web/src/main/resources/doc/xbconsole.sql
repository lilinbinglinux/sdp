/*
Navicat MySQL Data Transfer

Source Server         : 门户测试环境
Source Server Version : 50716
Source Host           : 172.16.13.94:31000
Source Database       : xbconsole

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2018-07-16 11:41:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for apipush
-- ----------------------------
DROP TABLE IF EXISTS `apipush`;
CREATE TABLE `apipush` (
  `id` varchar(255) DEFAULT NULL COMMENT 'id值',
  `name` varchar(255) DEFAULT NULL COMMENT '推送名称',
  `serid` varchar(255) DEFAULT NULL,
  `orderid` varchar(255) DEFAULT NULL COMMENT '流程图id（本库中服务id）',
  `context` varchar(255) DEFAULT '' COMMENT '上下文路径',
  `version` varchar(255) DEFAULT NULL COMMENT '版本',
  `visibility` varchar(255) DEFAULT NULL COMMENT '可见性（PUBLIC,PRIVATE,RESTRICTED,CONTROLLED）',
  `provider` varchar(255) DEFAULT NULL COMMENT '创建者',
  `parameters` longtext COMMENT '参数值'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of apipush
-- ----------------------------

-- ----------------------------
-- Table structure for codetable
-- ----------------------------
DROP TABLE IF EXISTS `codetable`;
CREATE TABLE `codetable` (
  `recId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(10) DEFAULT '' COMMENT '返回编码',
  `desc` varchar(1000) DEFAULT '' COMMENT '描述信息',
  `type` varchar(255) DEFAULT NULL COMMENT '(0为响应提示信息，1为码表描述信息)',
  `location` varchar(255) DEFAULT NULL COMMENT '归属页面信息',
  PRIMARY KEY (`recId`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of codetable
-- ----------------------------
INSERT INTO `codetable` VALUES ('1', '00000', '调用成功', '0', null);
INSERT INTO `codetable` VALUES ('2', '10001', '该ip不允许访问', '0', null);
INSERT INTO `codetable` VALUES ('3', '10002', '格式转换失败', '0', null);
INSERT INTO `codetable` VALUES ('4', '99999', '第三方集成平台内部服务异常', '0', null);
INSERT INTO `codetable` VALUES ('5', '10003', '用户无权访问该服务', '0', null);
INSERT INTO `codetable` VALUES ('6', '90001', '调用原始服务异常', '0', null);
INSERT INTO `codetable` VALUES ('7', '10004', '参数传入格式有误', '0', null);
INSERT INTO `codetable` VALUES ('8', '20002', '目前不支持的类型', '0', null);
INSERT INTO `codetable` VALUES ('9', '30001', '服务未审批通过', '0', null);
INSERT INTO `codetable` VALUES ('10', '90002', '服务编排异常', '0', null);
INSERT INTO `codetable` VALUES ('11', '10006', '条件判断不通过', '0', null);
INSERT INTO `codetable` VALUES ('12', '10007', 'appId不正确', null, null);
INSERT INTO `codetable` VALUES ('13', '10008', '租户id不正确', null, null);
INSERT INTO `codetable` VALUES ('14', '10009', 'logi_id不正确', null, null);
INSERT INTO `codetable` VALUES ('15', '10010', '访问的服务不存在', '0', null);
INSERT INTO `codetable` VALUES ('16', '90003', '服务流量控制出错', null, null);
INSERT INTO `codetable` VALUES ('17', '10012', '服务流量达到最大值', null, null);
INSERT INTO `codetable` VALUES ('18', '10013', '没有该订购服务', null, null);
INSERT INTO `codetable` VALUES ('19', '20003', '缺少系统参数', null, null);
INSERT INTO `codetable` VALUES ('20', '40001', '接口返回异常', null, null);

-- ----------------------------
-- Table structure for docs_user
-- ----------------------------
DROP TABLE IF EXISTS `docs_user`;
CREATE TABLE `docs_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `namespace` varchar(255) NOT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `user_autority` varchar(255) DEFAULT NULL,
  `user_cellphone` varchar(255) DEFAULT NULL,
  `user_createperson` bigint(20) DEFAULT NULL,
  `user_createtime` datetime DEFAULT NULL,
  `user_department` varchar(255) DEFAULT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  `user_employee_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_phone` varchar(255) DEFAULT NULL,
  `user_province` varchar(255) DEFAULT NULL,
  `user_real_name` varchar(255) DEFAULT NULL,
  `user_status` tinyint(4) NOT NULL,
  `user_updateperson` bigint(20) DEFAULT NULL,
  `user_updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of docs_user
-- ----------------------------

-- ----------------------------
-- Table structure for log_1
-- ----------------------------
DROP TABLE IF EXISTS `log_1`;
CREATE TABLE `log_1` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '日志记录的主键',
  `logType` int(2) DEFAULT NULL COMMENT '日志类型 0 代表服务，1代表 流程版本',
  `pubapiId` varchar(32) DEFAULT '' COMMENT '注册服务id',
  `orderid` varchar(32) DEFAULT '' COMMENT '订阅id',
  `url` varchar(250) DEFAULT NULL COMMENT 'url',
  `requestMsg` longtext COMMENT '请求报文',
  `responseMsg` longtext COMMENT '响应报文',
  `startTime` timestamp(3) NULL DEFAULT NULL COMMENT '开始时间',
  `endTime` timestamp(3) NULL DEFAULT NULL COMMENT '结束时间',
  `requestId` varchar(32) DEFAULT '' COMMENT '一次交互使用的id（调用一次接口分配一个requestID）',
  `useTime` int(11) DEFAULT NULL COMMENT '消耗时间（毫秒）',
  `dayTime` int(8) NOT NULL COMMENT '天数',
  `code` varchar(10) DEFAULT NULL COMMENT '成功失败详细编码',
  `instanceId` int(10) DEFAULT NULL COMMENT '实例全局id',
  `tenant_id` varchar(16) DEFAULT '' COMMENT '租户id',
  PRIMARY KEY (`id`,`dayTime`)
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_1
-- ----------------------------

-- ----------------------------
-- Table structure for log_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `log_dictionary`;
CREATE TABLE `log_dictionary` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '日志记录的主键',
  `dayDateTime` int(8) NOT NULL,
  `dayTotalCount` int(10) DEFAULT NULL,
  `tableName` varchar(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dic_dayDateTime` (`dayDateTime`) 
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_dictionary
-- ----------------------------
INSERT INTO `log_dictionary` VALUES ('46', '20180601', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('47', '20180605', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('48', '20180607', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('49', '20180612', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('50', '20180613', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('51', '20180614', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('52', '20180615', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('53', '20180619', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('54', '20180620', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('55', '20180621', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('56', '20180622', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('57', '20180625', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('58', '20180626', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('59', '20180627', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('60', '20180628', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('61', '20180629', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('62', '20180630', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('63', '20180702', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('64', '20180703', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('65', '20180704', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('66', '20180705', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('67', '20180706', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('68', '20180709', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('69', '20180710', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('70', '20180711', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('71', '20180712', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('72', '20180713', '0', 'log_1');
INSERT INTO `log_dictionary` VALUES ('73', '20180716', null, 'log_1');

-- ----------------------------
-- Table structure for log_ser_id_version_statistics
-- ----------------------------
DROP TABLE IF EXISTS `log_ser_id_version_statistics`;
CREATE TABLE `log_ser_id_version_statistics` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '统计表主键',
  `ser_id` varchar(32) DEFAULT NULL COMMENT '服务id',
  `ser_version` varchar(32) DEFAULT NULL COMMENT '版本id',
  `ser_name` varchar(50) DEFAULT NULL COMMENT '服务名称',
  `sendCount` int(11) DEFAULT NULL COMMENT '发送总量',
  `successCount` int(11) DEFAULT NULL COMMENT '成功发送总量',
  `failCount` int(11) DEFAULT NULL COMMENT '失败发送总量',
  `sendDate` int(8) DEFAULT NULL COMMENT '日期(yyyyMMdd)',
  `sourceType` varchar(2) DEFAULT NULL COMMENT '日志来源(0 同步，1 异步，2cas)',
  `tenant_id` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_ser_id_version_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for method_lock
-- ----------------------------
DROP TABLE IF EXISTS `method_lock`;
CREATE TABLE `method_lock` (
  `instanceId` int(10) NOT NULL COMMENT '实例全局id(具有唯一性和一次性)',
  `methodName` varchar(32) NOT NULL DEFAULT '' COMMENT '锁定的方法名',
  `methodDesc` varchar(64) NOT NULL DEFAULT '方法描述信息',
  `updateTime` int(8) NOT NULL COMMENT '保存时间 格式 yyyymmdd',
  UNIQUE KEY `methodName_updateTime` (`methodName`,`updateTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='锁定中的方法';

-- ----------------------------
-- Records of method_lock
-- ----------------------------

-- ----------------------------
-- Table structure for order_info_statistics
-- ----------------------------
DROP TABLE IF EXISTS `order_info_statistics`;
CREATE TABLE `order_info_statistics` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '统计表主键',
  `orderid` varchar(32) DEFAULT NULL COMMENT '订阅id',
  `sendCount` int(11) DEFAULT NULL COMMENT '发送总量',
  `code` varchar(10) DEFAULT NULL COMMENT '成功错误编码',
  `sendDate` int(8) DEFAULT NULL COMMENT '日期(yyyyMMdd)',
  `tenant_id` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_info_statistics
-- ----------------------------
INSERT INTO `order_info_statistics` VALUES ('1', '40282f8160d5329e0160d5438bee000b', '10', '00000', '20180201', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('2', '40282f81614fce6701614fd2144f0002', '3', '00000', '20180202', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('3', '40282f81614fce670161504d3f9e0020', '6', '00000', '20180202', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('4', '40282f8161505c56016150675317000f', '17', '00000', '20180202', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('5', '2c90dbe4615b285501615b2879320001', '3', '90002', '20180203', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('6', '2c90dbe4615b285501615b2879320001', '1', '00000', '20180203', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('7', '2c90dbe4615b285501615b2879320001', '1', '90002', '20180204', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('8', '2c90dbe4615b285501615b2879320001', '2', '00000', '20180204', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('9', '2c90dbe4615fac2b01615fac2b4b0000', '1', '00000', '20180204', 'poctest');
INSERT INTO `order_info_statistics` VALUES ('10', '40282f81614fce6701614fd2144f0002', '2', '00000', '20180205', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('11', '40282f81614fce6701614fd2144f0002', '4', '90002', '20180205', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('12', '40282f81614fce670161502efc0d0012', '13', '00000', '20180205', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('13', '2c90dbe4615b285501615b2879320001', '4', '00000', '20180208', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('14', '2c908b30622ca83601622cc035800009', '3759357', '00000', '20180316', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('15', '2c908b30622ca83601622cc035800009', '2003', '90002', '20180316', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('16', '2c908b30622ca83601622cc035800009', '4488134', '00000', '20180317', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('17', '2c908b30622ca83601622cc035800009', '236', '90002', '20180317', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('18', '2c908b30622ca83601622cc035800009', '1465914', '00000', '20180318', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('19', '2c908b30622ca83601622cc035800009', '3523648', '00000', '20180319', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('20', '2c908b30622ca83601622cc035800009', '3639963', '00000', '20180320', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('21', '2c908b30622ca83601622cc035800009', '43760', '90002', '20180320', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('22', '2c908b30622ca83601622cc035800009', '1807890', '00000', '20180321', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('23', '2c908b30622ca83601622cc035800009', '16162', '00000', '20180322', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('24', '2c908b30622ca83601622cc035800009', '109', '90002', '20180322', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('25', '000000006250c053016250f2814a000a', '27404', '90002', '20180323', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('26', '000000006250c053016250f2814a000a', '6232731', '00000', '20180323', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('27', '0000000062602fe50162603aac810009', '9199518', '00000', '20180326', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('28', '0000000062602fe50162603aac810009', '71360', '90002', '20180326', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('29', '0000000062602fe50162603aac810009', '5375117', '00000', '20180328', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('30', '0000000062602fe50162603aac810009', '181322', '90002', '20180328', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('31', '0000000062602fe50162603aac810009', '3225537', '00000', '20180403', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('32', '0000000062602fe50162603aac810009', '109079', '90002', '20180403', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('33', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180413', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('34', '4028826962be10d50162be4dc8bd0010', '0', '00000', '20180413', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('35', '4028826962be10d50162be6f53b00021', '0', '00000', '20180413', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('36', '4028826962be10d50162be6f53b00021', '0', '00000', '20180414', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('37', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180414', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('38', '4028826962be10d50162be6f53b00021', '0', '00000', '20180415', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('39', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180415', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('40', '4028826962be10d50162be6f53b00021', '0', '00000', '20180416', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('41', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180416', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('42', '4028826962be10d50162be6f53b00021', '0', '00000', '20180417', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('43', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180417', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('44', '4028826962be10d50162be6f53b00021', '0', '00000', '20180418', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('45', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180418', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('46', '4028826962be10d50162be6f53b00021', '0', '00000', '20180419', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('47', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180419', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('48', '4028826962be10d50162be6f53b00021', '0', '00000', '20180420', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('49', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180420', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('50', '4028826962be10d50162be6f53b00021', '0', '00000', '20180421', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('51', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180421', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('52', '4028826962be10d50162be6f53b00021', '0', '00000', '20180422', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('53', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180422', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('54', '4028826962be10d50162be6f53b00021', '0', '00000', '20180423', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('55', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180423', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('56', '4028826962be10d50162be6f53b00021', '0', '00000', '20180424', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('57', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180424', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('58', '4028826962be10d50162be6f53b00021', '0', '00000', '20180425', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('59', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180425', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('60', '4028826962be10d50162be6f53b00021', '0', '00000', '20180426', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('61', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180426', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('62', '4028826962be10d50162be6f53b00021', '0', '00000', '20180427', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('63', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180427', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('64', '4028826962be10d50162be6f53b00021', '0', '00000', '20180428', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('65', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180428', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('66', '4028031162d2a7dd01630b397128002d', '3', '90002', '20180428', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('67', '4028031162d2a7dd01630b4591580033', '3', '90002', '20180428', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('68', '4028031162d2a7dd01630b6d6738003b', '4', '90002', '20180428', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('69', '4028031162d2a7dd01630b7548ef0042', '2', '90002', '20180428', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('70', '4028031162d2a7dd01630b8fa7ed0049', '4', '90002', '20180428', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('71', '4028031162d2a7dd01630ba8a13e0053', '10', '00000', '20180428', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('72', '4028031162d2a7dd01630bb1d732005e', '1', '00000', '20180428', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('73', '4028031162d2a7dd01630bb50b120061', '1', '00000', '20180428', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('74', '4028031162d2a7dd01630bbf28020068', '1', '00000', '20180428', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('75', '4028826962be10d50162be6f53b00021', '0', '00000', '20180429', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('76', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180429', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('77', '4028826962be10d50162be6f53b00021', '0', '00000', '20180430', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('78', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180430', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('79', '4028826962be10d50162be6f53b00021', '0', '00000', '20180501', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('80', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180501', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('81', '4028826962be10d50162be6f53b00021', '0', '00000', '20180502', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('82', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180502', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('83', '4028826962be10d50162be6f53b00021', '0', '00000', '20180503', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('84', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180503', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('85', '4028826962be10d50162be6f53b00021', '0', '00000', '20180504', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('86', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180504', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('87', '4028826962be10d50162be6f53b00021', '0', '00000', '20180505', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('88', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180505', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('89', '4028826962be10d50162be6f53b00021', '0', '00000', '20180506', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('90', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180506', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('91', '4028826962be10d50162be6f53b00021', '0', '00000', '20180507', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('92', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180507', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('93', '4028826962be10d50162be6f53b00021', '0', '00000', '20180508', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('94', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180508', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('95', '4028826962be10d50162be6f53b00021', '0', '00000', '20180509', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('96', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180509', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('97', '4028826962be10d50162be6f53b00021', '0', '00000', '20180510', 'tenant_system');
INSERT INTO `order_info_statistics` VALUES ('98', '4028826962be10d50162be3d8f6f0009', '0', '00000', '20180510', 'tenant_system');

-- ----------------------------
-- Table structure for order_interface
-- ----------------------------
DROP TABLE IF EXISTS `order_interface`;
CREATE TABLE `order_interface` (
  `orderid` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '订阅服务id',
  `order_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `ordercode` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '编码',
  `protocal` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '所需请求协议',
  `url` varchar(200) COLLATE utf8_bin DEFAULT '' COMMENT '服务地址',
  `reqformat` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '提供的请求参数格式',
  `respformat` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '所需响应格式',
  `application_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '应用ID',
  `orderdesc` varchar(400) COLLATE utf8_bin DEFAULT NULL COMMENT '说明',
  `createdate` timestamp NULL DEFAULT NULL COMMENT '创建日期',
  `ser_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '注册服务id',
  `ser_version` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '版本ID',
  `limit_ip` varchar(2) COLLATE utf8_bin DEFAULT '0' COMMENT '是否限制ip访问（0不需要限制ip，1限制ip）',
  `appId` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '服务编码',
  `acc_freq` int(11) DEFAULT NULL COMMENT '访问频率',
  `acc_freq_type` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '访问频率时间类型（0为秒 1为分钟 2为小时 3为天）',
  `login_id` varchar(32) COLLATE utf8_bin DEFAULT '' COMMENT '当前登录用户login_id',
  `app_resume` longtext COLLATE utf8_bin COMMENT '订阅接口描述',
  `rep_flag` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '是否代订阅(0否 1是)',
  `rep_user_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '代订阅人',
  `checkstatus` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '审核状态（未审批0，审批通过1，审批不通过2，审批中3）',
  `tenant_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='服务订阅表';

-- ----------------------------
-- Records of order_interface
-- ----------------------------

-- ----------------------------
-- Table structure for order_total_statistics
-- ----------------------------
DROP TABLE IF EXISTS `order_total_statistics`;
CREATE TABLE `order_total_statistics` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '统计表主键',
  `orderid` varchar(32) DEFAULT NULL COMMENT '订阅id',
  `order_name` varchar(32) DEFAULT NULL COMMENT '名称',
  `sendCount` int(11) DEFAULT NULL COMMENT '发送总量',
  `successCount` int(11) DEFAULT NULL COMMENT '成功发送总量',
  `failCount` int(11) DEFAULT NULL COMMENT '失败发送总量',
  `sendDate` int(8) DEFAULT NULL COMMENT '日期(yyyyMMdd)',
  `ser_id` varchar(32) DEFAULT NULL COMMENT '服务id',
  `ser_version` varchar(32) DEFAULT NULL COMMENT '版本id',
  `tenant_id` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_total_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for page_model
-- ----------------------------
DROP TABLE IF EXISTS `page_model`;
CREATE TABLE `page_model` (
  `page_id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_date` datetime NOT NULL,
  `page_js` longtext,
  `page_name` varchar(50) NOT NULL,
  `page_sort_id` int(11) NOT NULL,
  `page_style` longtext,
  `page_text` longtext,
  `page_type_id` varchar(255) NOT NULL,
  `page_pure_text` longtext,
  PRIMARY KEY (`page_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of page_model
-- ----------------------------

-- ----------------------------
-- Table structure for page_module
-- ----------------------------
DROP TABLE IF EXISTS `page_module`;
CREATE TABLE `page_module` (
  `module_id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_date` datetime NOT NULL,
  `module_js` longtext,
  `module_name` varchar(50) DEFAULT NULL,
  `module_style` longtext,
  `module_text` longtext,
  `module_type_id` varchar(255) NOT NULL,
  `sort_id` int(11) NOT NULL,
  `pub_flag` int(11) NOT NULL,
  PRIMARY KEY (`module_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of page_module
-- ----------------------------

-- ----------------------------
-- Table structure for page_module_type
-- ----------------------------
DROP TABLE IF EXISTS `page_module_type`;
CREATE TABLE `page_module_type` (
  `module_type_id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_date` datetime NOT NULL,
  `module_parent_id` varchar(255) DEFAULT NULL,
  `module_type_name` varchar(255) NOT NULL,
  `pub_flag` int(11) NOT NULL,
  `sort_id` int(11) NOT NULL,
  PRIMARY KEY (`module_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of page_module_type
-- ----------------------------

-- ----------------------------
-- Table structure for page_res
-- ----------------------------
DROP TABLE IF EXISTS `page_res`;
CREATE TABLE `page_res` (
  `res_id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_date` datetime NOT NULL,
  `file_suffix` varchar(255) DEFAULT NULL,
  `res_name` varchar(50) DEFAULT NULL,
  `res_text` longblob,
  `res_type` int(11) NOT NULL,
  `res_type_id` varchar(255) NOT NULL,
  `sort_id` int(11) NOT NULL,
  PRIMARY KEY (`res_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of page_res
-- ----------------------------

-- ----------------------------
-- Table structure for page_res_join
-- ----------------------------
DROP TABLE IF EXISTS `page_res_join`;
CREATE TABLE `page_res_join` (
  `res_join_id` varchar(255) NOT NULL,
  `page_module_id` varchar(32) DEFAULT NULL,
  `res_id` varchar(32) DEFAULT NULL,
  `sort_id` int(11) NOT NULL,
  PRIMARY KEY (`res_join_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of page_res_join
-- ----------------------------

-- ----------------------------
-- Table structure for page_res_type
-- ----------------------------
DROP TABLE IF EXISTS `page_res_type`;
CREATE TABLE `page_res_type` (
  `res_type_id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_date` datetime NOT NULL,
  `pub_flag` int(11) NOT NULL,
  `res_parent_id` varchar(255) DEFAULT NULL,
  `res_type_name` varchar(255) NOT NULL,
  `sort_id` int(11) NOT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`res_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of page_res_type
-- ----------------------------

-- ----------------------------
-- Table structure for page_type
-- ----------------------------
DROP TABLE IF EXISTS `page_type`;
CREATE TABLE `page_type` (
  `page_type_id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_date` datetime NOT NULL,
  `page_parent_id` varchar(255) DEFAULT NULL,
  `page_sort_id` int(11) DEFAULT NULL,
  `page_type_name` varchar(50) NOT NULL,
  `page_type_path` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`page_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of page_type
-- ----------------------------

-- ----------------------------
-- Table structure for pro_data_api
-- ----------------------------
DROP TABLE IF EXISTS `pro_data_api`;
CREATE TABLE `pro_data_api` (
  `data_api_id` varchar(32) NOT NULL,
  `create_by` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `data_api_name` varchar(50) DEFAULT NULL,
  `data_res_id` varchar(32) DEFAULT NULL,
  `input_attr` text,
  `model_id` varchar(32) DEFAULT NULL,
  `output_attr` text,
  `sort_id` int(11) DEFAULT NULL,
  `sql_text` text,
  `tenant_id` varchar(32) DEFAULT NULL,
  `output_sample` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`data_api_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pro_data_api
-- ----------------------------

-- ----------------------------
-- Table structure for pro_model
-- ----------------------------
DROP TABLE IF EXISTS `pro_model`;
CREATE TABLE `pro_model` (
  `model_id` varchar(32) NOT NULL,
  `create_by` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `model_flag` varchar(2) DEFAULT NULL,
  `model_name` varchar(50) DEFAULT NULL,
  `model_type_path` varchar(1000) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `resume` text,
  `sort_id` int(11) DEFAULT NULL,
  `tenant_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pro_model
-- ----------------------------

-- ----------------------------
-- Table structure for pure_notice
-- ----------------------------
DROP TABLE IF EXISTS `pure_notice`;
CREATE TABLE `pure_notice` (
  `NOTICE_ID` varchar(32) NOT NULL DEFAULT '',
  `NOTICE_TITLE` varchar(32) DEFAULT NULL,
  `NOTICE_TYPE` varchar(4) DEFAULT NULL,
  `NOTICE_CONTENT` varchar(1024) DEFAULT NULL,
  `STATE` varchar(4) DEFAULT NULL COMMENT '1：发送成功，-1：发送失败，-2：草稿',
  `FROM_SIGN` varchar(4) DEFAULT NULL COMMENT '0：系统公告，1：管理员发送公告',
  `PUBDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PUB_USER_ID` varchar(32) DEFAULT NULL,
  `REC_ORG_ID` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`NOTICE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pure_notice
-- ----------------------------

-- ----------------------------
-- Table structure for pure_notice_user_ref
-- ----------------------------
DROP TABLE IF EXISTS `pure_notice_user_ref`;
CREATE TABLE `pure_notice_user_ref` (
  `NOTICE_ID` varchar(32) DEFAULT '',
  `USER_ID` varchar(32) DEFAULT NULL,
  `IS_READ` varchar(4) DEFAULT NULL COMMENT '1：已读，-1：未读'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pure_notice_user_ref
-- ----------------------------

-- ----------------------------
-- Table structure for pure_org
-- ----------------------------
DROP TABLE IF EXISTS `pure_org`;
CREATE TABLE `pure_org` (
  `ORG_ID` varchar(32) NOT NULL DEFAULT '',
  `ORG_NAME` varchar(128) DEFAULT NULL,
  `PARENT_ID` varchar(32) DEFAULT NULL,
  `ORD` decimal(8,0) DEFAULT NULL,
  `PATH` varchar(512) DEFAULT NULL,
  `TENANT_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ORG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pure_org
-- ----------------------------
INSERT INTO `pure_org` VALUES ('2c90bffd5a1803a5015a1b73be22000c', '产品部', '402881ec5a075c1c015a075c73fc0002', '2', '/402881ec59fe64850159fe6cd2770003/402881ec5a075c1c015a075c73fc0002/2c90bffd5a1803a5015a1b73be22000c', 'tenant_system');
INSERT INTO `pure_org` VALUES ('2c90dbc663d82f860163d83089530002', '研发部', '402881ec5a075c1c015a075c73fc0002', '1', '/402881ec59fe64850159fe6cd2770003/402881ec5a075c1c015a075c73fc0002/2c90dbc663d82f860163d83089530002', null);
INSERT INTO `pure_org` VALUES ('402881ec59fe64850159fe6cd2770003', '总公司', '', '2', '/402881ec59fe64850159fe6cd2770003', 'tenant_system');
INSERT INTO `pure_org` VALUES ('402881ec59fec7720159fec984b30003', '客服部', '402881ec59fe64850159fe6cd2770003', '2', '/402881ec59fe64850159fe6cd2770003/402881ec59fec7720159fec984b30003', 'tenant_system');
INSERT INTO `pure_org` VALUES ('402881ec59fec7720159fec9afaa0004', '销售部', '402881ec59fe64850159fe6cd2770003', '3', '/402881ec59fe64850159fe6cd2770003/402881ec59fec7720159fec9afaa0004', 'tenant_system');
INSERT INTO `pure_org` VALUES ('402881ec59fec7720159fecdf2ea0008', '测试部', '402881ec59fe64850159fe6cd2770003', '2', '/402881ec59fe64850159fe6cd2770003/402881ec59fec7720159fecdf2ea0008', 'tenant_system');
INSERT INTO `pure_org` VALUES ('402881ec5a075c1c015a075c73fc0002', '技术部', '402881ec59fe64850159fe6cd2770003', '1', '/402881ec59fe64850159fe6cd2770003/402881ec5a075c1c015a075c73fc0002', 'tenant_system');

-- ----------------------------
-- Table structure for pure_resources
-- ----------------------------
DROP TABLE IF EXISTS `pure_resources`;
CREATE TABLE `pure_resources` (
  `RESOURCES_ID` varchar(32) NOT NULL DEFAULT '',
  `PARENT_ID` varchar(32) DEFAULT NULL,
  `APP_SYSTEM_ID` varchar(32) DEFAULT NULL,
  `RESOURCES_TYPE_ID` varchar(32) DEFAULT NULL,
  `RESOURCES_NAME` varchar(32) DEFAULT NULL,
  `URL` varchar(512) DEFAULT NULL,
  `EXT1` varchar(512) DEFAULT NULL,
  `EXT2` varchar(512) DEFAULT NULL,
  `EXT3` varchar(512) DEFAULT NULL,
  `EXT4` varchar(512) DEFAULT NULL,
  `MEMO` varchar(512) DEFAULT NULL,
  `ORD` decimal(8,0) DEFAULT NULL,
  `PATH` varchar(512) DEFAULT NULL,
  `CREATE_ID` varchar(32) DEFAULT NULL,
  `ONLY_READ` decimal(8,0) DEFAULT NULL,
  `FROM_SIGN` varchar(225) DEFAULT NULL,
  `MODIFY_FIELDS` varchar(225) DEFAULT NULL,
  `TENANT_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`RESOURCES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pure_resources
-- ----------------------------
INSERT INTO `pure_resources` VALUES ('1', 'ROOT', '4', '1', '我的工作台', '/workbench/index', '&#xe650;', null, null, '1', null, '1', '', '1', null, null, null, null);
INSERT INTO `pure_resources` VALUES ('10074', '3', '4', '1', '组织部门', '/org/index', '', null, null, '1', null, '11', '', null, null, null, null, null);
INSERT INTO `pure_resources` VALUES ('12', '3', '4', '1', '角色管理', '/role/index', '', null, null, '1', null, '10', null, '1', null, null, null, null);
INSERT INTO `pure_resources` VALUES ('13', '3', '4', '1', '用户管理', '/user/index', null, null, null, '1', null, '2', null, '1', null, null, null, null);
INSERT INTO `pure_resources` VALUES ('14', '3', '4', '1', '菜单管理', '/resource/index', '', null, null, '1', null, '1', '', '1', null, null, null, null);
INSERT INTO `pure_resources` VALUES ('2c90bdd4641bca9101641bffebc70005', 'ROOT', null, '0', '数据模型', '', '&#xe6db;', null, null, null, null, '1', '/2c90bdd4641bca9101641bffebc70005', '4028ee335beae186015beae2e1e00002', null, null, null, null);
INSERT INTO `pure_resources` VALUES ('2c90bdd46421d29d016421d5ce610004', '2c90bdd4641bca9101641bffebc70005', null, '1', '数据建模', '/v1/sqlModel/manage/databaseIndex', '&#xe70e;', null, null, null, null, '1', '/2c90bdd4641bca9101641bffebc70005/2c90bdd46421d29d016421d5ce610004', 'admin', null, null, null, null);
INSERT INTO `pure_resources` VALUES ('2c90bdd4645dc43a01645e282a330009', '2c90bdd4641bca9101641bffebc70005', null, '1', '项目管理', '/v1/sqlModel/manage/dataBaseProject', '', null, null, null, null, '2', '/2c90bdd4641bca9101641bffebc70005/2c90bdd4645dc43a01645e282a330009', 'admin', null, null, null, null);
INSERT INTO `pure_resources` VALUES ('2c90bfb3641c0c6f01641c0d2ffa0003', 'ROOT', null, '0', '页面建模', '', '&#xe658;', null, null, null, null, '2', '/2c90bfb3641c0c6f01641c0d2ffa0003', 'admin', null, null, null, null);
INSERT INTO `pure_resources` VALUES ('2c90bfb3641c0c6f01641c0e58ab0005', '2c90bfb3641c0c6f01641c0d2ffa0003', null, '1', '页面管理', '/v1/pageType/pageTypes', '&#xe658;', null, null, null, null, '1', '/2c90bfb3641c0c6f01641c0d2ffa0003/2c90bfb3641c0c6f01641c0e58ab0005', 'admin', null, null, null, null);
INSERT INTO `pure_resources` VALUES ('2c90bfb3641c72ec01641c8fc4e6000e', '2c90bfb3641c0c6f01641c0d2ffa0003', null, '1', '组件管理', '/v1/pageModuleType/moduleClassify', '&#xe625;', null, null, null, null, '2', '/2c90bfb3641c0c6f01641c0d2ffa0003/2c90bfb3641c72ec01641c8fc4e6000e', '4028ee335beae186015beae2e1e00002', null, null, null, null);
INSERT INTO `pure_resources` VALUES ('3', 'ROOT', '4', '0', '系统配置', '', '&#xe606;', null, null, '1', null, '1000', '', '1', null, null, null, null);
INSERT INTO `pure_resources` VALUES ('4028d0816434ad26016434aed0f40002', 'ROOT', null, '0', '服务编排', '', '&#xe70e;', null, null, null, null, '4', '/4028d0816434ad26016434aed0f40002', '4028ee335beae186015beae2e1e00002', null, null, null, null);
INSERT INTO `pure_resources` VALUES ('4028d0816434ad26016434af6cf10003', '4028d0816434ad26016434aed0f40002', null, '1', '流程图编排', '/processTable/index', '', null, null, null, null, '1', '/4028d0816434ad26016434aed0f40002/4028d0816434ad26016434af6cf10003', '4028ee335beae186015beae2e1e00002', null, null, null, null);
INSERT INTO `pure_resources` VALUES ('4028ee335beafaa7015beb008940000b', '3', null, '1', '租户管理', '/tenant/index', '', null, null, null, null, '12', '/4028ee335beafaa7015beb008940000b', '4028ee335beae186015beae2e1e00002', null, null, null, null);
INSERT INTO `pure_resources` VALUES ('4028ee335beafaa7015beb00ee5f000c', '3', null, '1', '登录日志', '/userLoginLog/index', '&#xe64d;', null, null, null, null, '13', '/4028ee335beafaa7015beb00ee5f000c', '4028ee335beae186015beae2e1e00002', null, null, null, null);
INSERT INTO `pure_resources` VALUES ('8a8a8bb4643ad4fe01643aef3371001b', '2c90bfb3641c0c6f01641c0d2ffa0003', null, '1', '资源管理', '/v1/pageResType/resClassify', '&#xe625;', null, null, null, null, '3', '/2c90bfb3641c0c6f01641c0d2ffa0003/8a8a8bb4643ad4fe01643aef3371001b', 'admin', null, null, null, null);

-- ----------------------------
-- Table structure for pure_resources_log
-- ----------------------------
DROP TABLE IF EXISTS `pure_resources_log`;
CREATE TABLE `pure_resources_log` (
  `LOG_ID` varchar(32) NOT NULL DEFAULT '',
  `RESOURCES_ID` varchar(32) DEFAULT NULL,
  `USER_ID` varchar(32) DEFAULT NULL,
  `LOG_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pure_resources_log
-- ----------------------------

-- ----------------------------
-- Table structure for pure_resources_type
-- ----------------------------
DROP TABLE IF EXISTS `pure_resources_type`;
CREATE TABLE `pure_resources_type` (
  `RESOURCES_TYPE_ID` varchar(32) NOT NULL DEFAULT '',
  `RESOURCES_TYPE_NAME` varchar(32) DEFAULT NULL,
  `ORD` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`RESOURCES_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pure_resources_type
-- ----------------------------
INSERT INTO `pure_resources_type` VALUES ('0', '文件夹资源', '1');
INSERT INTO `pure_resources_type` VALUES ('1', '内连接资源', '2');
INSERT INTO `pure_resources_type` VALUES ('2', '外连接资源', '3');
INSERT INTO `pure_resources_type` VALUES ('3', '功能按钮资源', '4');

-- ----------------------------
-- Table structure for pure_role
-- ----------------------------
DROP TABLE IF EXISTS `pure_role`;
CREATE TABLE `pure_role` (
  `ROLE_ID` varchar(32) NOT NULL DEFAULT '',
  `PARENT_ID` varchar(32) DEFAULT NULL,
  `ROLE_NAME` varchar(32) DEFAULT NULL,
  `MEMO` varchar(512) DEFAULT NULL,
  `ORD` decimal(8,0) DEFAULT NULL,
  `TENANT_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pure_role
-- ----------------------------
INSERT INTO `pure_role` VALUES ('1', null, '超级管理员', null, '1', null);
INSERT INTO `pure_role` VALUES ('2c90bf095a161ff6015a175a36ad000f', null, '普通用户', 'confluence浏览用户', null, null);
INSERT INTO `pure_role` VALUES ('4028ee195b3d5e2a015b3d60b3db000c', null, '普通管理员', '可以系统配置', null, null);
INSERT INTO `pure_role` VALUES ('4028ee335beae186015beae3a8540005', null, 'x超级管理员', '系统集成管理员', null, null);

-- ----------------------------
-- Table structure for pure_role_auth
-- ----------------------------
DROP TABLE IF EXISTS `pure_role_auth`;
CREATE TABLE `pure_role_auth` (
  `ID` varchar(32) NOT NULL DEFAULT '',
  `ROLE_ID` varchar(32) DEFAULT NULL,
  `RESOURCES_ID` varchar(32) DEFAULT NULL,
  `OPT_TYPE` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pure_role_auth
-- ----------------------------
INSERT INTO `pure_role_auth` VALUES ('2c90bdd4645dc43a01645e28892b000b', '1', '1', null);
INSERT INTO `pure_role_auth` VALUES ('2c90bdd4645dc43a01645e28892d000c', '1', '2c90bdd4641bca9101641bffebc70005', null);
INSERT INTO `pure_role_auth` VALUES ('2c90bdd4645dc43a01645e28892f000d', '1', '2c90bdd46421d29d016421d5ce610004', null);
INSERT INTO `pure_role_auth` VALUES ('2c90bdd4645dc43a01645e288930000e', '1', '2c90bdd4645dc43a01645e282a330009', null);
INSERT INTO `pure_role_auth` VALUES ('2c90bdd4645dc43a01645e288932000f', '1', '2c90bfb3641c0c6f01641c0d2ffa0003', null);
INSERT INTO `pure_role_auth` VALUES ('2c90bdd4645dc43a01645e2889340010', '1', '2c90bfb3641c0c6f01641c0e58ab0005', null);
INSERT INTO `pure_role_auth` VALUES ('2c90bdd4645dc43a01645e2889360011', '1', '2c90bfb3641c72ec01641c8fc4e6000e', null);
INSERT INTO `pure_role_auth` VALUES ('2c90bdd4645dc43a01645e2889380012', '1', '8a8a8bb4643ad4fe01643aef3371001b', null);
INSERT INTO `pure_role_auth` VALUES ('2c90bdd4645dc43a01645e28893b0013', '1', '3', null);
INSERT INTO `pure_role_auth` VALUES ('2c90bdd4645dc43a01645e28893d0014', '1', '14', null);
INSERT INTO `pure_role_auth` VALUES ('2c90bdd4645dc43a01645e2889420015', '1', '13', null);
INSERT INTO `pure_role_auth` VALUES ('2c90bdd4645dc43a01645e2889430016', '1', '12', null);
INSERT INTO `pure_role_auth` VALUES ('4028d0816434ad26016434afc1920006', '4028ee335beae186015beae3a8540005', '1', null);
INSERT INTO `pure_role_auth` VALUES ('4028d0816434ad26016434afc1990007', '4028ee335beae186015beae3a8540005', '2c90bdd4641bca9101641bffebc70005', null);
INSERT INTO `pure_role_auth` VALUES ('4028d0816434ad26016434afc19d0008', '4028ee335beae186015beae3a8540005', '2c90bdd46421d29d016421d5ce610004', null);
INSERT INTO `pure_role_auth` VALUES ('4028d0816434ad26016434afc1ac000b', '4028ee335beae186015beae3a8540005', '2c90bfb3641c0c6f01641c0d2ffa0003', null);
INSERT INTO `pure_role_auth` VALUES ('4028d0816434ad26016434afc1b1000c', '4028ee335beae186015beae3a8540005', '2c90bfb3641c0c6f01641c0e58ab0005', null);
INSERT INTO `pure_role_auth` VALUES ('4028d0816434ad26016434afc1b5000d', '4028ee335beae186015beae3a8540005', '2c90bfb3641c72ec01641c8fc4e6000e', null);
INSERT INTO `pure_role_auth` VALUES ('4028d0816434ad26016434afc1ba000e', '4028ee335beae186015beae3a8540005', '4028d0816434ad26016434aed0f40002', null);
INSERT INTO `pure_role_auth` VALUES ('4028d0816434ad26016434afc1ce000f', '4028ee335beae186015beae3a8540005', '4028d0816434ad26016434af6cf10003', null);
INSERT INTO `pure_role_auth` VALUES ('4028d0816434ad26016434afc1dc0010', '4028ee335beae186015beae3a8540005', '3', null);
INSERT INTO `pure_role_auth` VALUES ('4028d0816434ad26016434afc1e30011', '4028ee335beae186015beae3a8540005', '14', null);
INSERT INTO `pure_role_auth` VALUES ('4028d0816434ad26016434afc1e90012', '4028ee335beae186015beae3a8540005', '13', null);
INSERT INTO `pure_role_auth` VALUES ('4028d0816434ad26016434afc1f10013', '4028ee335beae186015beae3a8540005', '12', null);
INSERT INTO `pure_role_auth` VALUES ('4028d0816434ad26016434afc1fb0014', '4028ee335beae186015beae3a8540005', '10074', null);
INSERT INTO `pure_role_auth` VALUES ('4028d0816434ad26016434afc2000015', '4028ee335beae186015beae3a8540005', '4028ee335beafaa7015beb008940000b', null);
INSERT INTO `pure_role_auth` VALUES ('4028d0816434ad26016434afc2050016', '4028ee335beae186015beae3a8540005', '4028ee335beafaa7015beb00ee5f000c', null);
INSERT INTO `pure_role_auth` VALUES ('ff8080815ba92c69015ba943952a005c', '4028ee195b3d5e2a015b3d60b3db000c', '3', null);
INSERT INTO `pure_role_auth` VALUES ('ff8080815ba92c69015ba9439540005d', '4028ee195b3d5e2a015b3d60b3db000c', '14', null);
INSERT INTO `pure_role_auth` VALUES ('ff8080815ba92c69015ba9439542005e', '4028ee195b3d5e2a015b3d60b3db000c', '13', null);
INSERT INTO `pure_role_auth` VALUES ('ff8080815ba92c69015ba9439544005f', '4028ee195b3d5e2a015b3d60b3db000c', '12', null);

-- ----------------------------
-- Table structure for pure_tenant
-- ----------------------------
DROP TABLE IF EXISTS `pure_tenant`;
CREATE TABLE `pure_tenant` (
  `TENANT_ID` varchar(32) NOT NULL DEFAULT '',
  `TENANT_NAME` varchar(32) DEFAULT NULL,
  `STATE` varchar(4) DEFAULT NULL,
  `MEMO` varchar(512) DEFAULT NULL,
  `ORD` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`TENANT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pure_tenant
-- ----------------------------
INSERT INTO `pure_tenant` VALUES ('2c90db0d60deeace0160def0d333000e', 'tenant_system', '1', '系统租户', null);

-- ----------------------------
-- Table structure for pure_user
-- ----------------------------
DROP TABLE IF EXISTS `pure_user`;
CREATE TABLE `pure_user` (
  `user_id` varchar(32) NOT NULL DEFAULT '',
  `login_id` varchar(32) DEFAULT NULL,
  `PASSWORD` varchar(32) DEFAULT NULL,
  `USER_NAME` varchar(32) DEFAULT NULL,
  `SEX` varchar(4) DEFAULT NULL,
  `EMALL` varchar(128) DEFAULT NULL,
  `MOBILE` varchar(32) DEFAULT NULL,
  `TELEPHONE` varchar(32) DEFAULT NULL,
  `STATE` varchar(4) DEFAULT NULL,
  `PWD_STATE` varchar(4) DEFAULT NULL,
  `MEMO` varchar(512) DEFAULT NULL,
  `REG_DATE` datetime DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `CREATER_ID` varchar(32) DEFAULT NULL,
  `ORG_ID` varchar(32) DEFAULT NULL,
  `DATA_AUTH` varchar(32) DEFAULT NULL,
  `LOCK_DATE` datetime DEFAULT NULL,
  `LOCK_LOGIN_TIMES` decimal(8,0) DEFAULT NULL,
  `PWD_VALID_STATE` decimal(8,0) DEFAULT NULL,
  `TENANT_ADMIN` decimal(8,0) DEFAULT NULL,
  `PWD_UPDATE_DATE` datetime DEFAULT NULL,
  `TENANT_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pure_user
-- ----------------------------
INSERT INTO `pure_user` VALUES ('2c90298161d0cffb0161d0cffb6c0000', 'lining', 'E10ADC3949BA59ABBE56E057F20F883E', '李宁', '', 'lining@bonc.com.cn', '17610609266', null, '1', '1', 'bcm user', '2018-02-26 14:32:26', null, null, null, null, null, '0', '0', '0', null, 'lining');
INSERT INTO `pure_user` VALUES ('2c90db0d60deeace0160deef2c87000a', 'test_role', 'E10ADC3949BA59ABBE56E057F20F883E', '测试角色用户', null, '12@qq.com', '13935172327', null, '1', '1', null, '2018-01-10 15:18:24', null, '4028ee335beae186015beae2e1e00002', null, null, null, '0', '0', '0', null, 'tenant_system');
INSERT INTO `pure_user` VALUES ('2c90db0d60df0dba0160df2046a70003', '测试租户用户', 'E10ADC3949BA59ABBE56E057F20F883E', 'test_tenantId', null, '12@qq.com', '13935172327', null, '1', '1', null, '2018-01-10 16:12:02', null, '4028ee335beae186015beae2e1e00002', null, null, null, '0', '0', '0', null, 'tenant_system');
INSERT INTO `pure_user` VALUES ('2c90dbc6648759a501648759a5f70000', 'test_zyz', 'E10ADC3949BA59ABBE56E057F20F883E', 'test_zyz', '', '', '', null, '1', '1', 'bcm user', '2018-07-11 11:19:16', null, null, null, null, null, '0', '0', '0', null, 'test_zyz');
INSERT INTO `pure_user` VALUES ('2c90dbe46163b556016163b65eb00002', 'poctest', 'E10ADC3949BA59ABBE56E057F20F883E', 'poctest', '1', '', '', null, '1', '1', 'bcm user', '2018-02-05 10:05:51', null, null, null, null, null, '0', '0', '0', null, 'poctest');
INSERT INTO `pure_user` VALUES ('4028031162d2a7dd0162fc9f7d9e0022', 'zzj', 'E10ADC3949BA59ABBE56E057F20F883E', 'zzj', '男', 'zzi@qq.com', '16619941870', null, '1', '1', 'bcm user', '2018-04-25 19:45:33', null, null, null, null, null, '0', '0', '0', null, 'tenant_system');
INSERT INTO `pure_user` VALUES ('4028031162d2a7dd0162fcaa5b460023', 'lyk', 'E10ADC3949BA59ABBE56E057F20F883E', 'lyk', '男', 'lyk@163.com', '15101082234', null, '1', '1', 'bcm user', '2018-04-25 19:57:25', null, null, null, null, null, '0', '0', '0', null, 'tenant_system');
INSERT INTO `pure_user` VALUES ('4028031162d2a7dd016301b8f2340024', 'lez', 'E10ADC3949BA59ABBE56E057F20F883E', 'lez', '', '1@163.com', '12121212121', null, '1', '1', 'bcm user', '2018-04-26 19:31:27', null, null, null, null, null, '0', '0', '0', null, 'tenant_system');
INSERT INTO `pure_user` VALUES ('4028031162d2a7dd01630208eb680025', 'zzz_z', 'E10ADC3949BA59ABBE56E057F20F883E', 'zzz_z', '男', '466049810@qq.com', '16619941870', null, '1', '1', 'bcm user', '2018-04-26 20:58:49', null, null, null, null, null, '0', '0', '0', null, 'tenant_system');
INSERT INTO `pure_user` VALUES ('4028031162d2a7dd016304adba5a0026', 'zzz_y', 'E10ADC3949BA59ABBE56E057F20F883E', 'zzz_y', '女', '466049810@qq.com', '16619941870', null, '1', '1', 'bcm user', '2018-04-27 09:18:04', null, null, null, null, null, '0', '0', '0', null, 'tenant_system');
INSERT INTO `pure_user` VALUES ('4028031162d2a7dd01631e9504660071', 'test123', 'E10ADC3949BA59ABBE56E057F20F883E', 'test', '', '', '', null, '1', '1', 'bcm user', '2018-05-02 10:01:12', null, null, null, null, null, '0', '0', '0', null, 'test123');
INSERT INTO `pure_user` VALUES ('40287dbd5f043cf4015f043e113f003a', 'uni000', 'E10ADC3949BA59ABBE56E057F20F883E', 'uni000', '1', '', '', null, '1', '1', 'bcm user', '2017-10-10 11:04:57', null, null, null, null, null, '0', '0', '0', null, 'tenant_system');
INSERT INTO `pure_user` VALUES ('4028d0815f04346f015f043933340001', 'unitest', 'E10ADC3949BA59ABBE56E057F20F883E', 'unitest', '0', null, null, null, '1', '1', 'bcm user', '2017-10-10 10:59:38', null, null, null, null, null, '0', '0', '0', null, 'tenant_system');
INSERT INTO `pure_user` VALUES ('4028ee185d35eeee015d35f6bad70005', 'zhangyang', 'E10ADC3949BA59ABBE56E057F20F883E', 'zhangyang', null, 'zhangyang@bonc.com.cn', '13111107717', null, '1', '1', null, '2017-07-12 16:42:28', null, '4028ee335beae186015beae2e1e00002', '', null, null, '0', '0', '0', null, 'tenant_system');
INSERT INTO `pure_user` VALUES ('4028ee195b3d6bff015b3d7db3d6001d', 'bonc', '373BE862C3FC6FC706594015B262FA12', 'bonc', null, 'admin@qq.com', '18335731234', null, '1', '1', null, '2017-04-05 17:41:39', '2017-04-06 14:09:42', 'admin', '', null, '1000-01-01 00:00:00', '2', '0', '0', '2017-04-06 14:09:42', 'tenant_system');
INSERT INTO `pure_user` VALUES ('4028ee335beae186015beae2e1e00002', 'xadmin', 'E10ADC3949BA59ABBE56E057F20F883E', 'xadmin', null, 'xadmin@bonc.com.cn', '13725896547', null, '1', '1', null, '2017-05-09 09:46:29', null, 'admin', '', null, null, '0', '0', '0', null, 'tenant_system');
INSERT INTO `pure_user` VALUES ('4028ee8a6058e709016058fd0f590017', 'test', 'E10ADC3949BA59ABBE56E057F20F883E', 'test', null, 'stet@dfg.jhg', '18334701366', null, '1', '1', null, '2017-12-15 15:04:27', null, '4028ee335beae186015beae2e1e00002', null, null, null, '0', '0', '0', null, 'tenant_system');
INSERT INTO `pure_user` VALUES ('admin', 'admin', '373BE862C3FC6FC706594015B262FA12', '超级管理员', '男', 'admin@bonc.com.cn', '15942411681', '66666', '1', '1', null, '2017-02-03 21:02:09', '2017-02-05 15:24:18', null, null, null, '2017-02-05 15:24:13', '5', '0', '0', '2017-02-04 20:16:24', 'tenant_system');

-- ----------------------------
-- Table structure for pure_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `pure_user_auth`;
CREATE TABLE `pure_user_auth` (
  `ID` varchar(32) NOT NULL DEFAULT '',
  `USER_ID` varchar(32) DEFAULT NULL,
  `RESOURCES_ID` varchar(32) DEFAULT NULL,
  `OPT_TYPE` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pure_user_auth
-- ----------------------------

-- ----------------------------
-- Table structure for pure_user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `pure_user_login_log`;
CREATE TABLE `pure_user_login_log` (
  `USER_ID` varchar(32) DEFAULT '',
  `LOGIN_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LOGIN_IP` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pure_user_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for pure_user_org_ref
-- ----------------------------
DROP TABLE IF EXISTS `pure_user_org_ref`;
CREATE TABLE `pure_user_org_ref` (
  `ORG_ID` varchar(32) DEFAULT '',
  `USER_ID` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pure_user_org_ref
-- ----------------------------
INSERT INTO `pure_user_org_ref` VALUES ('402881ec59fe64850159fe6cd2770003', 'admin');
INSERT INTO `pure_user_org_ref` VALUES ('402881ec5a075c1c015a075c73fc0002', '');
INSERT INTO `pure_user_org_ref` VALUES ('402881ec59fe64850159fe6cd2770003', '4028ee8a6058e709016058fd0f590017');
INSERT INTO `pure_user_org_ref` VALUES ('402881ec5a075c1c015a075c73fc0002', '4028ee8a6058e709016058fd0f590017');
INSERT INTO `pure_user_org_ref` VALUES ('2c90bffd5a1803a5015a1b73be22000c', '4028ee8a6058e709016058fd0f590017');
INSERT INTO `pure_user_org_ref` VALUES ('402881ec59fec7720159fec984b30003', '4028ee8a6058e709016058fd0f590017');
INSERT INTO `pure_user_org_ref` VALUES ('402881ec59fec7720159fecdf2ea0008', '4028ee8a6058e709016058fd0f590017');
INSERT INTO `pure_user_org_ref` VALUES ('402881ec59fec7720159fec9afaa0004', '4028ee8a6058e709016058fd0f590017');

-- ----------------------------
-- Table structure for pure_user_role
-- ----------------------------
DROP TABLE IF EXISTS `pure_user_role`;
CREATE TABLE `pure_user_role` (
  `ROLE_ID` varchar(32) DEFAULT '',
  `USER_ID` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pure_user_role
-- ----------------------------
INSERT INTO `pure_user_role` VALUES ('2c90bf095a161ff6015a175a36ad000f', '2c90bf1f5a1b4913015a1b5794d0000c');
INSERT INTO `pure_user_role` VALUES ('2c90bf095a161ff6015a175a36ad000f', '1002');
INSERT INTO `pure_user_role` VALUES ('2c90bf095a161ff6015a175a36ad000f', '4028ee195b3d6bff015b3d7db3d6001d');
INSERT INTO `pure_user_role` VALUES ('1', 'admin');
INSERT INTO `pure_user_role` VALUES ('4028ee335beae186015beae3a8540005', 'admin');
INSERT INTO `pure_user_role` VALUES ('1', '4028ee335beae186015beae2e1e00002');
INSERT INTO `pure_user_role` VALUES ('2c90bf095a161ff6015a175a36ad000f', '4028ee335beae186015beae2e1e00002');
INSERT INTO `pure_user_role` VALUES ('4028ee195b3d5e2a015b3d60b3db000c', '4028ee335beae186015beae2e1e00002');
INSERT INTO `pure_user_role` VALUES ('4028ee335beae186015beae3a8540005', '4028ee335beae186015beae2e1e00002');
INSERT INTO `pure_user_role` VALUES ('4028ee335beae186015beae3a8540005', '4028ee8a6058e709016058fd0f590017');
INSERT INTO `pure_user_role` VALUES ('4028ee335beae186015beae3a8540005', '2c90db0d60deeace0160deef2c87000a');

-- ----------------------------
-- Table structure for sequence
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `seq_name` varchar(50) NOT NULL,
  `current_val` int(11) NOT NULL,
  `increment_val` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`seq_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sequence
-- ----------------------------
INSERT INTO `sequence` VALUES ('seq_instance_resgister', '1515', '1');

-- ----------------------------
-- Table structure for service_log
-- ----------------------------
DROP TABLE IF EXISTS `service_log`;
CREATE TABLE `service_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志记录的主键',
  `pubid` varchar(50) DEFAULT '' COMMENT '注册服务id',
  `layoutSerId` varchar(50) DEFAULT '' COMMENT '归属的编排服务id',
  `requestMsg` varchar(2048) DEFAULT '' COMMENT '请求报文',
  `responseMsg` varchar(9000) DEFAULT '' COMMENT '响应报文',
  `startTime` timestamp(3) NULL DEFAULT NULL COMMENT '开始时间',
  `endTime` timestamp(3) NULL DEFAULT NULL COMMENT '结束时间',
  `tenant_id` varchar(50) DEFAULT '' COMMENT '租户id',
  `requestID` varchar(255) DEFAULT '' COMMENT '一次交互使用的id（调用一次接口分配一个requestID）',
  `usetime` int(11) DEFAULT NULL COMMENT '消耗时间（毫秒）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1997 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of service_log
-- ----------------------------

-- ----------------------------
-- Table structure for ser_api
-- ----------------------------
DROP TABLE IF EXISTS `ser_api`;
CREATE TABLE `ser_api` (
  `api_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '接口ID',
  `api_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '接口名称',
  `apitypeid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '接口类型',
  `api_agreement` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '接口协议(0 http 1 soap 2 socket)',
  `api_request` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '请求参数格式(0 json 1 xml 2 text)',
  `api_response` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '响应参数格式(0 json 1 xml 2 text)',
  `api_restype` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '请求方式(0 get 1 post)',
  `api_point` varchar(5) COLLATE utf8_bin DEFAULT NULL COMMENT '端口',
  `api_resume` longtext COLLATE utf8_bin COMMENT '接口数据描述',
  `shareflag` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '是否公共接口（0否  1是）',
  `creatime` datetime DEFAULT NULL COMMENT '创建时间',
  `delflag` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '是否删除(0 正常 1删除)',
  `stopflag` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '是否停用(0 正常 1删除)',
  `tenant_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`api_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='接口表';

-- ----------------------------
-- Records of ser_api
-- ----------------------------

-- ----------------------------
-- Table structure for ser_apitype
-- ----------------------------
DROP TABLE IF EXISTS `ser_apitype`;
CREATE TABLE `ser_apitype` (
  `apitypeid` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '接口类型ID',
  `type_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '类型名称',
  `parentid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '父ID',
  `shareflag` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '是否共享(0否 1是)',
  `creat_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `creatime` datetime DEFAULT NULL COMMENT '创建时间',
  `delflag` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '是否删除(0 正常 1删除)',
  `stopflag` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '是否停用(0 正常 1删除)',
  `tenant_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`apitypeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='接口类型';

-- ----------------------------
-- Records of ser_apitype
-- ----------------------------
INSERT INTO `ser_apitype` VALUES ('4028eea85f757815015f757bc94a0002', '接口类型1', 'root', '1', 'xadmin', '2017-11-01 10:49:27', '0', '0', null);
INSERT INTO `ser_apitype` VALUES ('4028eea85f757815015f757be1b20003', '接口类型2', 'root', '1', 'xadmin', '2017-11-01 10:49:33', '0', '0', null);
INSERT INTO `ser_apitype` VALUES ('4028eea85f757815015f757c0d290004', '接口类型3', 'root', '1', 'xadmin', '2017-11-01 10:49:45', '0', '0', null);
INSERT INTO `ser_apitype` VALUES ('root', '接口类型', '', '1', 'xadmin', '2017-10-26 10:29:17', '0', '0', null);

-- ----------------------------
-- Table structure for ser_application
-- ----------------------------
DROP TABLE IF EXISTS `ser_application`;
CREATE TABLE `ser_application` (
  `application_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '应用ID',
  `application_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '应用名称',
  `creat_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `creatime` datetime DEFAULT NULL COMMENT '创建时间',
  `delflag` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '是否删除(0 正常 1删除)',
  `tenant_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`application_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='应用表';

-- ----------------------------
-- Records of ser_application
-- ----------------------------
INSERT INTO `ser_application` VALUES ('2c90dbe460da2b410160da3b573a0006', '应用3', 'xadmin', '2018-01-09 17:23:30', '0', 'tenant_system');
INSERT INTO `ser_application` VALUES ('2c90dbe460da2b410160da3c022f0007', '应用2', 'xadmin', '2018-01-09 17:24:13', '0', 'tenant_system');
INSERT INTO `ser_application` VALUES ('2c90dbfa606871ee016068c06f0f000a', '测试类型1', 'test', '2017-12-18 16:32:09', '0', 'tenant_system');
INSERT INTO `ser_application` VALUES ('4028eea85faa62e9015faa63493e0003', '应用1', 'xadmin', '2017-11-11 17:22:34', '0', 'tenant_system');

-- ----------------------------
-- Table structure for ser_ip_limit
-- ----------------------------
DROP TABLE IF EXISTS `ser_ip_limit`;
CREATE TABLE `ser_ip_limit` (
  `ip_id` varchar(32) DEFAULT NULL COMMENT 'id',
  `app_id` varchar(32) DEFAULT NULL,
  `order_id` varchar(32) DEFAULT NULL,
  `ip_addr` varchar(32) DEFAULT NULL COMMENT 'ip地址（all 不限制）',
  `name_type` varchar(32) DEFAULT NULL COMMENT '名单类型（0 白名单 1 黑名单）',
  `creatime` datetime DEFAULT NULL COMMENT '创建时间',
  `tenant_id` varchar(16) DEFAULT NULL COMMENT '租户id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ser_ip_limit
-- ----------------------------

-- ----------------------------
-- Table structure for ser_kafka_config
-- ----------------------------
DROP TABLE IF EXISTS `ser_kafka_config`;
CREATE TABLE `ser_kafka_config` (
  `topic_id` varchar(32) NOT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT '0',
  `run_status` int(2) DEFAULT '0',
  `deleteflag` varchar(2) DEFAULT '0',
  `tenant_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ser_kafka_config
-- ----------------------------
INSERT INTO `ser_kafka_config` VALUES ('test1', '2018-07-16 11:36:04', '130121', '1', '0', 'system');
INSERT INTO `ser_kafka_config` VALUES ('test2', '2018-07-16 11:41:02', '114701', '1', '0', 'system');
INSERT INTO `ser_kafka_config` VALUES ('test3', '2018-07-16 10:12:21', '109103', '1', '0', 'system');
INSERT INTO `ser_kafka_config` VALUES ('test4', '2018-07-16 10:14:47', '61032', '1', '0', 'system');
INSERT INTO `ser_kafka_config` VALUES ('test5', '2018-07-13 15:19:07', '36276', '1', '0', 'system');
INSERT INTO `ser_kafka_config` VALUES ('test6', '2018-07-13 15:22:18', '61244', '1', '0', 'system');
INSERT INTO `ser_kafka_config` VALUES ('test7', '2018-05-10 05:03:55', '59628', '1', '0', 'system');
INSERT INTO `ser_kafka_config` VALUES ('test8', '2018-04-17 06:48:58', '2465', '1', '0', 'system');
INSERT INTO `ser_kafka_config` VALUES ('test9', '2018-04-12 03:17:59', '12824', '1', '0', 'system');

-- ----------------------------
-- Table structure for ser_main
-- ----------------------------
DROP TABLE IF EXISTS `ser_main`;
CREATE TABLE `ser_main` (
  `ser_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '服务ID',
  `ser_name` varchar(100) COLLATE utf8_bin DEFAULT '' COMMENT '服务名称',
  `ser_type` varchar(32) COLLATE utf8_bin DEFAULT '' COMMENT '服务类型',
  `ser_code` varchar(32) COLLATE utf8_bin DEFAULT '' COMMENT '服务编码',
  `ser_version` varchar(32) COLLATE utf8_bin DEFAULT '' COMMENT '最新版本',
  `ser_path` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'url地址',
  `ser_resume` longtext COLLATE utf8_bin COMMENT '接口描述',
  `creatime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `synch_flag` varchar(2) COLLATE utf8_bin DEFAULT '' COMMENT '同步、异步（0同步 1异步）',
  `delflag` varchar(2) COLLATE utf8_bin DEFAULT '0' COMMENT '是否删除(0 正常 1删除)',
  `stopflag` varchar(2) COLLATE utf8_bin DEFAULT '0' COMMENT '是否停用(0 正常 1删除)',
  `tenant_id` varchar(32) COLLATE utf8_bin DEFAULT '' COMMENT '租户ID',
  `login_id` varchar(32) COLLATE utf8_bin DEFAULT '' COMMENT '用户login_id',
  PRIMARY KEY (`ser_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='服务表';

-- ----------------------------
-- Records of ser_main
-- ----------------------------

-- ----------------------------
-- Table structure for ser_sp_login
-- ----------------------------
DROP TABLE IF EXISTS `ser_sp_login`;
CREATE TABLE `ser_sp_login` (
  `sp_loginid` varchar(32) NOT NULL COMMENT '单点登录ID',
  `sp_name` varchar(100) DEFAULT NULL COMMENT '单点登录名称',
  `sp_type` varchar(32) DEFAULT NULL COMMENT '服务类型',
  `sp_code` varchar(255) DEFAULT NULL,
  `sp_path` varchar(100) DEFAULT NULL COMMENT 'url地址',
  `sp_resume` varchar(100) DEFAULT NULL COMMENT '链接描述',
  `sp_agreement` varchar(2) DEFAULT NULL COMMENT '服务协议(0 http 1 soap 2 socket)',
  `sp_restype` varchar(2) DEFAULT NULL COMMENT '请求方式(0 get 1 post)',
  `sp_flow` longtext COMMENT '编排流程',
  `creatime` datetime DEFAULT NULL COMMENT '创建时间',
  `delflag` varchar(2) DEFAULT NULL COMMENT '是否删除(0 正常 1删除)',
  `stopflag` varchar(2) DEFAULT NULL COMMENT '是否停用(0 正常 1删除)',
  `tenant_id` varchar(32) DEFAULT NULL COMMENT '租户ID',
  `login_id` varchar(32) DEFAULT NULL COMMENT 'loginId',
  PRIMARY KEY (`sp_loginid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ser_sp_login
-- ----------------------------

-- ----------------------------
-- Table structure for ser_type
-- ----------------------------
DROP TABLE IF EXISTS `ser_type`;
CREATE TABLE `ser_type` (
  `ser_type_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '服务类型ID',
  `ser_type_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '类型名称',
  `parentid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '父ID',
  `id_path` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'ID层级路径',
  `name_path` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '名称层级路径',
  `creat_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `creatime` datetime DEFAULT NULL COMMENT '创建时间',
  `delflag` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '是否删除(0 正常 1删除)',
  `stopflag` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '是否停用(0 正常 1删除)',
  `tenant_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`ser_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='服务类型';

-- ----------------------------
-- Records of ser_type
-- ----------------------------

-- ----------------------------
-- Table structure for ser_version
-- ----------------------------
DROP TABLE IF EXISTS `ser_version`;
CREATE TABLE `ser_version` (
  `ser_ver_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '版本ID',
  `ser_version` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '版本代码',
  `ser_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '服务ID',
  `ser_agreement` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '服务协议(0 http 1 soap 2 socket)',
  `ser_request` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '请求参数格式(0 json 1 xml 2 text)',
  `ser_response` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '响应参数格式(0 json 1 xml 2 text)',
  `ser_restype` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '请求方式(0 get 1 post)',
  `ser_point` varchar(5) COLLATE utf8_bin DEFAULT NULL COMMENT '端口',
  `input_resume` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '入参说明',
  `out_resume` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '出参说明',
  `ser_flow` longtext COLLATE utf8_bin COMMENT '编排流程',
  `creatime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `delflag` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '是否删除(0 正常 1删除)',
  `stopflag` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '是否停用(0 正常 1删除)',
  `tenant_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '租户ID',
  `login_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用户login_id',
  `push_state` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ser_ver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='服务版本';

-- ----------------------------
-- Records of ser_version
-- ----------------------------

-- ----------------------------
-- Table structure for ser_version_test
-- ----------------------------
DROP TABLE IF EXISTS `ser_version_test`;
CREATE TABLE `ser_version_test` (
  `ser_ver_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '版本ID',
  `ser_version` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '版本代码',
  `ser_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '服务ID',
  `ser_agreement` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '服务协议(0 http 1 soap 2 socket)',
  `ser_request` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '请求参数格式(0 json 1 xml 2 text)',
  `ser_response` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '响应参数格式(0 json 1 xml 2 text)',
  `ser_restype` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '请求方式(0 get 1 post)',
  `ser_point` varchar(5) COLLATE utf8_bin DEFAULT NULL COMMENT '端口',
  `input_resume` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '入参说明',
  `out_resume` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '出参说明',
  `ser_flow` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '编排流程',
  `creatime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `delflag` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '是否删除(0 正常 1删除)',
  `stopflag` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '是否停用(0 正常 1删除)',
  `tenant_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '租户ID',
  `login_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用户login_id',
  PRIMARY KEY (`ser_ver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='服务版本';

-- ----------------------------
-- Records of ser_version_test
-- ----------------------------
INSERT INTO `ser_version_test` VALUES ('', null, null, null, null, null, null, null, null, null, '123123', '2017-12-08 15:29:43', null, null, null, null);

-- ----------------------------
-- Table structure for sql_code
-- ----------------------------
DROP TABLE IF EXISTS `sql_code`;
CREATE TABLE `sql_code` (
  `code_id` varchar(32) NOT NULL,
  `code_item` varchar(100) DEFAULT NULL,
  `code_name` varchar(100) DEFAULT NULL,
  `code_path` varchar(1000) DEFAULT NULL,
  `code_resume` varchar(1000) DEFAULT NULL,
  `code_type_id` varchar(32) DEFAULT NULL,
  `create_by` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `sort_id` int(11) DEFAULT NULL,
  `tenant_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sql_code
-- ----------------------------

-- ----------------------------
-- Table structure for sql_code_type
-- ----------------------------
DROP TABLE IF EXISTS `sql_code_type`;
CREATE TABLE `sql_code_type` (
  `code_type_id` varchar(32) NOT NULL,
  `code_sql` varchar(1000) DEFAULT NULL,
  `code_type_name` varchar(50) DEFAULT NULL,
  `create_by` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `pub_flag` varchar(2) DEFAULT NULL,
  `sort_id` int(11) DEFAULT NULL,
  `sql_flag` varchar(2) DEFAULT NULL,
  `tenant_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`code_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sql_code_type
-- ----------------------------

-- ----------------------------
-- Table structure for sql_data_res
-- ----------------------------
DROP TABLE IF EXISTS `sql_data_res`;
CREATE TABLE `sql_data_res` (
  `data_res_id` varchar(32) NOT NULL,
  `data_res_desc` varchar(100) DEFAULT NULL,
  `data_res_type_id` varchar(32) DEFAULT NULL,
  `data_res_type_name` varchar(100) DEFAULT NULL,
  `data_res_url` varchar(100) DEFAULT NULL,
  `data_status` varchar(2) DEFAULT NULL,
  `is_default` varchar(2) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `resume` varchar(1000) DEFAULT NULL,
  `sort_num` int(11) DEFAULT NULL,
  `tenant_id` varchar(32) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `table_schema` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`data_res_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sql_data_res
-- ----------------------------

-- ----------------------------
-- Table structure for sql_data_res_type
-- ----------------------------
DROP TABLE IF EXISTS `sql_data_res_type`;
CREATE TABLE `sql_data_res_type` (
  `data_res_type_id` varchar(32) NOT NULL,
  `data_driver` varchar(100) DEFAULT NULL,
  `data_res_type_name` varchar(100) DEFAULT NULL,
  `data_stutas` varchar(2) DEFAULT NULL,
  `res_type` varchar(10) DEFAULT NULL,
  `resume` varchar(1000) DEFAULT NULL,
  `sort_num` int(11) DEFAULT NULL,
  `tenant_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`data_res_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sql_data_res_type
-- ----------------------------

-- ----------------------------
-- Table structure for sql_field
-- ----------------------------
DROP TABLE IF EXISTS `sql_field`;
CREATE TABLE `sql_field` (
  `field_id` varchar(32) NOT NULL,
  `field_key` varchar(2) DEFAULT NULL,
  `codesetid` varchar(2) DEFAULT NULL,
  `create_by` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `field_digit` int(11) DEFAULT NULL,
  `field_len` int(11) DEFAULT NULL,
  `field_name` varchar(50) DEFAULT NULL,
  `field_resume` varchar(1000) DEFAULT NULL,
  `field_sql_name` varchar(50) DEFAULT NULL,
  `field_type` varchar(2) DEFAULT NULL,
  `sort_id` int(11) DEFAULT NULL,
  `sort_index` varchar(2) DEFAULT NULL,
  `table_id` varchar(32) DEFAULT NULL,
  `tenant_id` varchar(32) DEFAULT NULL,
  `date_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`field_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sql_field
-- ----------------------------

-- ----------------------------
-- Table structure for sql_table
-- ----------------------------
DROP TABLE IF EXISTS `sql_table`;
CREATE TABLE `sql_table` (
  `table_id` varchar(32) NOT NULL,
  `create_by` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `data_res_id` varchar(32) DEFAULT NULL,
  `sort_id` int(11) DEFAULT NULL,
  `table_main_flag` varchar(32) DEFAULT NULL,
  `table_name` varchar(50) DEFAULT NULL,
  `table_resume` varchar(1000) DEFAULT NULL,
  `table_sql_name` varchar(50) DEFAULT NULL,
  `table_type_id` varchar(32) DEFAULT NULL,
  `table_x` float(10,2) DEFAULT '0.00',
  `table_y` float(10,2) DEFAULT '0.00',
  `tenant_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sql_table
-- ----------------------------

-- ----------------------------
-- Table structure for sql_table_foreignkey
-- ----------------------------
DROP TABLE IF EXISTS `sql_table_foreignkey`;
CREATE TABLE `sql_table_foreignkey` (
  `foreign_key_id` varchar(32) NOT NULL,
  `join_field` varchar(32) DEFAULT NULL,
  `join_table` varchar(32) DEFAULT NULL,
  `main_field` varchar(32) DEFAULT NULL,
  `main_table` varchar(32) DEFAULT NULL,
  `line_start` varchar(10) DEFAULT NULL,
  `line_end` varchar(10) DEFAULT NULL,
  `tenant_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`foreign_key_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sql_table_foreignkey
-- ----------------------------

-- ----------------------------
-- Table structure for sql_table_ser
-- ----------------------------
DROP TABLE IF EXISTS `sql_table_ser`;
CREATE TABLE `sql_table_ser` (
  `idds` bigint(10) NOT NULL COMMENT '计数',
  `createTimes` datetime DEFAULT NULL COMMENT '序号',
  `age` float(10,2) DEFAULT NULL COMMENT '年龄',
  `ides` int(10) NOT NULL COMMENT '序号',
  `teachers` varchar(10) NOT NULL COMMENT '人数',
  PRIMARY KEY (`idds`,`ides`,`teachers`),
  KEY `idds` (`idds`) ,
  KEY `ides` (`ides`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sql_table_ser
-- ----------------------------

-- ----------------------------
-- Table structure for sql_table_type
-- ----------------------------
DROP TABLE IF EXISTS `sql_table_type`;
CREATE TABLE `sql_table_type` (
  `table_type_id` varchar(32) NOT NULL,
  `create_by` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `sort_id` int(11) DEFAULT NULL,
  `table_type_name` varchar(50) DEFAULT NULL,
  `table_type_path` varchar(1000) DEFAULT NULL,
  `tenant_id` varchar(32) DEFAULT NULL,
  `data_res_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`table_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sql_table_type
-- ----------------------------

-- ----------------------------
-- Table structure for statistics_lock
-- ----------------------------
DROP TABLE IF EXISTS `statistics_lock`;
CREATE TABLE `statistics_lock` (
  `instanceId` int(10) NOT NULL COMMENT '实例全局id(具有唯一性和一次性)',
  `updateTime` varchar(12) NOT NULL COMMENT '保存时间 格式 yyyymmddhhmm',
  UNIQUE KEY `updateTime` (`updateTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='锁定中的方法';

-- ----------------------------
-- Records of statistics_lock
-- ----------------------------

-- ----------------------------
-- Table structure for sys_common_cfg
-- ----------------------------
DROP TABLE IF EXISTS `sys_common_cfg`;
CREATE TABLE `sys_common_cfg` (
  `cfg_key` varchar(100) NOT NULL COMMENT '配置KEY',
  `cfg_value` varchar(4096) NOT NULL COMMENT '配置值',
  `desc` varchar(256) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`cfg_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置表';

-- ----------------------------
-- Records of sys_common_cfg
-- ----------------------------
INSERT INTO `sys_common_cfg` VALUES ('1000', 'http://172.16.11.59/xIntegration/v1/apisEmploy/apis?tenantId=%s&appId=%s', '同步订阅url(http)');
INSERT INTO `sys_common_cfg` VALUES ('1001', 'http://172.16.11.59/xIntegration/soap/apis?tenantId=%s&appId=%s', '同步订阅webservice');
INSERT INTO `sys_common_cfg` VALUES ('1002', 'http://172.16.11.59/xIntegration/v1/apisEmploy/apisPublish?tenantId=%s&serId=%s', '异步订阅url(http)');
INSERT INTO `sys_common_cfg` VALUES ('1003', 'http://172.16.11.59/xIntegration/soap/apisPublish?tenantId=%s&serId=%s', '异步订阅webservice');
INSERT INTO `sys_common_cfg` VALUES ('1004', 'http://172.16.11.59/xIntegration/v1/apisEmploy/apisPublish', '异步订阅url(http,无参数版)');
INSERT INTO `sys_common_cfg` VALUES ('1005', 'http://172.16.11.59/xIntegration/soap/apisPublish', '异步订阅webservice(无参数版)');
INSERT INTO `sys_common_cfg` VALUES ('1006', 'http://172.16.11.59/xIntegration/v1/apisEmploy/casPublish?tenantId=%s&appId=%s', 'cas单点（http）');

