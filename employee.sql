/*
Navicat MySQL Data Transfer

Source Server         : MYSQL57
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : employee

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2020-05-29 17:40:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_department`
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `departmentNo` varchar(20) NOT NULL COMMENT 'departmentNo',
  `departmentName` varchar(50) NOT NULL COMMENT '部门名称',
  PRIMARY KEY (`departmentNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_department
-- ----------------------------
INSERT INTO `t_department` VALUES ('BM001', '财政所');
INSERT INTO `t_department` VALUES ('BM002', '土地所');
INSERT INTO `t_department` VALUES ('BM003', '水管站');
INSERT INTO `t_department` VALUES ('BM004', '计生办');
INSERT INTO `t_department` VALUES ('BM005', '林管站');

-- ----------------------------
-- Table structure for `t_employee`
-- ----------------------------
DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee` (
  `employeeNo` varchar(20) NOT NULL COMMENT 'employeeNo',
  `positionObj` int(11) NOT NULL COMMENT '职位',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `sex` varchar(4) NOT NULL COMMENT '性别',
  `employeePhoto` varchar(60) NOT NULL COMMENT '员工照片',
  `birthday` varchar(20) DEFAULT NULL COMMENT '出生日期',
  `schoolRecord` varchar(20) NOT NULL COMMENT '学历',
  `employeeDesc` varchar(5000) DEFAULT NULL COMMENT '员工介绍',
  PRIMARY KEY (`employeeNo`),
  KEY `positionObj` (`positionObj`),
  CONSTRAINT `t_employee_ibfk_1` FOREIGN KEY (`positionObj`) REFERENCES `t_position` (`positionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_employee
-- ----------------------------
INSERT INTO `t_employee` VALUES ('EM001', '1', '李明翠', '女', 'upload/2c17f1ec-2f44-4166-9976-b8e8fa401bdb.png', '1998-10-07', '大专', '精通财务管理');
INSERT INTO `t_employee` VALUES ('EM002', '4', '利尔', '男', 'upload/9e5e67dd-521c-478c-82f1-0dcd70ff85bd.png', '1998-10-06', '硕士', '电脑技术');
INSERT INTO `t_employee` VALUES ('EM003', '2', '小明', '男', 'upload/NoImage.jpg', '2020-04-15', '本科', '口才');

-- ----------------------------
-- Table structure for `t_employgg`
-- ----------------------------
DROP TABLE IF EXISTS `t_employgg`;
CREATE TABLE `t_employgg` (
  `employggNo` varchar(255) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `employggDay` date DEFAULT NULL,
  `employggDesc` varchar(255) NOT NULL,
  PRIMARY KEY (`employggNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_employgg
-- ----------------------------
INSERT INTO `t_employgg` VALUES ('BM001', '王明', '2020-04-02', '丰富回复呼唤扶额回复好烦好烦个人隔热台湾填入其为人温柔问题问题3问题3问题');
INSERT INTO `t_employgg` VALUES ('BM002', '小凯', '2020-04-13', '叫7 ');

-- ----------------------------
-- Table structure for `t_mission`
-- ----------------------------
DROP TABLE IF EXISTS `t_mission`;
CREATE TABLE `t_mission` (
  `missionName` varchar(255) NOT NULL,
  `missionPeople` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`missionName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_mission
-- ----------------------------
INSERT INTO `t_mission` VALUES ('卫生打扫', '王帅');
INSERT INTO `t_mission` VALUES ('放热峰', '小王');
INSERT INTO `t_mission` VALUES ('档案整理', '李明');
INSERT INTO `t_mission` VALUES ('种树', '小王');
INSERT INTO `t_mission` VALUES ('问卷调查', '小聪');

-- ----------------------------
-- Table structure for `t_position`
-- ----------------------------
DROP TABLE IF EXISTS `t_position`;
CREATE TABLE `t_position` (
  `positionId` int(11) NOT NULL AUTO_INCREMENT COMMENT '职位id',
  `departmentObj` varchar(20) NOT NULL COMMENT '所属部门',
  `positionName` varchar(50) NOT NULL COMMENT '职位名称',
  `baseSalary` float NOT NULL COMMENT '基本工资',
  `sellPercent` varchar(20) NOT NULL COMMENT '销售提成',
  PRIMARY KEY (`positionId`),
  KEY `departmentObj` (`departmentObj`),
  CONSTRAINT `t_position_ibfk_1` FOREIGN KEY (`departmentObj`) REFERENCES `t_department` (`departmentNo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_position
-- ----------------------------
INSERT INTO `t_position` VALUES ('1', 'BM001', '财务会计', '5600', '0');
INSERT INTO `t_position` VALUES ('2', 'BM002', '市场营销专员', '2000', '18%');
INSERT INTO `t_position` VALUES ('4', 'BM003', '技术总监', '15000', '10%');
INSERT INTO `t_position` VALUES ('5', 'BM004', '主任', '2400', '10');
