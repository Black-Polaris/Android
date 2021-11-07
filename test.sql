/*
 Navicat Premium Data Transfer

 Source Server         : sqb
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 07/11/2021 22:09:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `课程号` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `课程名` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `授课老师工号` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `学分` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('课程号', '课程名', '授课老师工号', '学分');
INSERT INTO `course` VALUES ('001', '数据库', '18001', '5');
INSERT INTO `course` VALUES ('002', '软件工程', '18002', '5');
INSERT INTO `course` VALUES ('003', 'web', '18003', '5');

-- ----------------------------
-- Table structure for exam
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam`  (
  `考试科目` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `考试时间` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `考试年级` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `考试班级` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `授课教师` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam
-- ----------------------------
INSERT INTO `exam` VALUES ('考试科目', '考试时间', '考试年级', '考试班级', '授课教师');
INSERT INTO `exam` VALUES ('数据库', '2020-10-10', '17', '2', '数据库老师');
INSERT INTO `exam` VALUES ('软件工程', '2020-10-10', '18', '3', '软件工程老师');
INSERT INTO `exam` VALUES ('web', '2020-10-10', '18', '4', 'web老师');

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `学号` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `课程名` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `分数` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `获得学分` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES ('学号', '课程名', '分数', '获得学分');
INSERT INTO `score` VALUES ('2017001', '数据库', '90', '4');
INSERT INTO `score` VALUES ('2017001', '软件工程', '80', '3');
INSERT INTO `score` VALUES ('2017001', 'web', '90', '4');
INSERT INTO `score` VALUES ('2017002', '数据库', '85', '3.5');
INSERT INTO `score` VALUES ('2017002', '软件工程', '80', '3');
INSERT INTO `score` VALUES ('2017002', 'web', '90', '4');
INSERT INTO `score` VALUES ('2017003', '数据库', '90', '4');
INSERT INTO `score` VALUES ('2017003', '软件工程', '80', '3');
INSERT INTO `score` VALUES ('2017003', 'web', '85', '3.5');
INSERT INTO `score` VALUES ('2018001', '数据库', '80', '3');
INSERT INTO `score` VALUES ('2018001', '软件工程', '80', '3');
INSERT INTO `score` VALUES ('2018001', 'web', '80', '3');
INSERT INTO `score` VALUES ('2018002', '数据库', '90', '4');
INSERT INTO `score` VALUES ('2018002', '软件工程', '90', '4');
INSERT INTO `score` VALUES ('2018002', 'web', '90', '4');
INSERT INTO `score` VALUES ('2018003', '数据库', '85', '3.5');
INSERT INTO `score` VALUES ('2018003', '软件工程', '85', '3.5');
INSERT INTO `score` VALUES ('2018003', 'web', '85', '3.5');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `账号` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `姓名` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `性别` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `班级` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `手机号` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `年龄` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `出生日期` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `学院` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `年级` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `登录密码` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('账号', '姓名', '性别', '班级', '手机号', '年龄', '出生日期', '学院', '年级', '登录密码');
INSERT INTO `student` VALUES ('2017001', '17张三', '男', '1', '13500000001', '20', '2000-01-01', '计算科学学院', '17', '123456');
INSERT INTO `student` VALUES ('2017002', '17李四', '女', '2', '13500000002', '20', '2000-02-02', '计算科学学院', '17', '123456');
INSERT INTO `student` VALUES ('2017003', '17王五', '男', '1', '13500000003', '20', '2000-03-03', '计算科学学院', '17', '123456');
INSERT INTO `student` VALUES ('2018001', '18张三', '男', '1', '13700000001', '20', '2000-04-04', '计算科学学院', '18', '123456');
INSERT INTO `student` VALUES ('2018002', '18李四', '女', '1', '13700000002', '20', '2000-05-05', '计算科学学院', '18', '123456');
INSERT INTO `student` VALUES ('2018003', '18王五', '女', '2', '13700000003', '20', '2000-06-06', '计算科学学院', '18', '123456');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `工号` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `姓名` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `性别` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `教授课程` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `手机号` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `年龄` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `出生日期` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `学院` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `登录密码` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('工号', '姓名', '性别', '教授课程', '手机号', '年龄', '出生日期', '学院', '登录密码');
INSERT INTO `teacher` VALUES ('18001', '数据库老师', '男', '数据库', '1300000001', '30', '1990-01-01', '计算科学学院', '123456');
INSERT INTO `teacher` VALUES ('18002', '软件工程老师', '男', '软件工程', '1300000002', '30', '1990-02-02', '计算科学学院', '123456');
INSERT INTO `teacher` VALUES ('18003', 'web老师', '男', 'web', '1300000003', '30', '1990-03-03', '计算科学学院', '123456');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `账号` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `姓名` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `密码` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `身份类型` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('账号', '姓名', '密码', '身份类型');
INSERT INTO `user` VALUES ('000', 'admin', '123456', '0');
INSERT INTO `user` VALUES ('18001', '数据库老师', '123456', '1');
INSERT INTO `user` VALUES ('18002', '软件工程老师', '123456', '1');
INSERT INTO `user` VALUES ('18003', 'web老师', '123456', '1');
INSERT INTO `user` VALUES ('2017001', '17张三', '123456', '2');
INSERT INTO `user` VALUES ('2017002', '17李四', '123456', '2');
INSERT INTO `user` VALUES ('2017003', '17王五', '123456', '2');
INSERT INTO `user` VALUES ('2018001', '18张三', '123456', '2');
INSERT INTO `user` VALUES ('2018002', '18李四', '123456', '2');
INSERT INTO `user` VALUES ('2018003', '18王五', '123456', '2');

SET FOREIGN_KEY_CHECKS = 1;
