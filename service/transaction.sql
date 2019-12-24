/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50720
Source Host           : 127.0.0.1:3306
Source Database       : transaction

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-12-26 16:09:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for attachment
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `attachment_name` varchar(200) DEFAULT NULL,
  `attachment_url` varchar(500) NOT NULL,
  `goods_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrw34boh3dba1i1yv6c1d0i3k7` (`goods_id`),
  CONSTRAINT `FKrw34boh3dba1i1yv6c1d0i3k7` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='小程序上传图片表';

-- ----------------------------
-- Records of attachment
-- ----------------------------
INSERT INTO `attachment` VALUES ('46', '89db12c8-292d-4dc7-a1df-b8079e2ee4ea.jpg', 'https://www.xiaoyuancun.store/images/89db12c8-292d-4dc7-a1df-b8079e2ee4ea.jpg', '43');
INSERT INTO `attachment` VALUES ('47', '027d83ba-c4ec-46e5-a9e9-fee8ac625431.jpg', 'https://www.xiaoyuancun.store/images/027d83ba-c4ec-46e5-a9e9-fee8ac625431.jpg', '43');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_date` datetime DEFAULT NULL,
  `content` varchar(500) NOT NULL,
  `goods_id` int(11) NOT NULL,
  `comment_read` int(11) DEFAULT NULL,
  `reply_comment_id` int(11) DEFAULT NULL,
  `reply_id` int(11) DEFAULT NULL,
  `comment_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKage29tnk8e1h3gj8l6vmam8bu` (`reply_id`),
  KEY `FKg2l4shfkbp38lr4gyhsk3ntfi` (`comment_user_id`),
  CONSTRAINT `FKage29tnk8e1h3gj8l6vmam8bu` FOREIGN KEY (`reply_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKg2l4shfkbp38lr4gyhsk3ntfi` FOREIGN KEY (`comment_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bulletin_date` datetime NOT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `goods_name` varchar(200) NOT NULL,
  `original_price` double DEFAULT NULL,
  `price` double NOT NULL,
  `spec` varchar(500) NOT NULL,
  `type` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKglqrwf2hm6bj7skpadm6u4v76` (`user_id`),
  CONSTRAINT `FKglqrwf2hm6bj7skpadm6u4v76` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='发布物品信息表';

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('43', '2018-12-19 17:21:47', null, '闲置鼠标', '120', '50', '多出来的无线鼠标，平时也用不着，故转给有需要的同学', '闲置数码', '32');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` varchar(36) NOT NULL,
  `content` varchar(50) DEFAULT NULL,
  `added_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES ('0bda9113-7421-49d6-b70d-705dd194c614', '小明同学，有人捡到了你的饭卡，请速到饭卡充值处领取你的饭卡哦！', '2018-12-09 21:22:50');

-- ----------------------------
-- Table structure for subscribe
-- ----------------------------
DROP TABLE IF EXISTS `subscribe`;
CREATE TABLE `subscribe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `avatar_url` varchar(200) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subscribe
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` varchar(36) NOT NULL,
  `account` varchar(50) DEFAULT NULL,
  `added_date` datetime DEFAULT NULL,
  `is_admin` char(1) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `salt` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '2018-12-08 15:51:06', 'N', '41717', '2018-12-09 20:41:01', '周靖富', 'e10adc3949ba59abbe56e057f20f883e', '18172505052', '');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `avatar_url` varchar(200) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `openid` varchar(255) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `qq` varchar(50) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='微信用户';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('32', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK1ap1qRk9nNVPFs1hCMnwS1jTIU2W0hFaibLRiawpmT1LmeWQY7iciam6E32agia86yGVzvYpupicHkPGQ/132', '2', '18172505054', '啊毛and狗几', 'oYwIZ4wasihCATkR325ga9m9_DF4', '2536ecda2c2bb8f6b6c2c39f0aaf9421', '1751218699', '0', '0', '韦菊蓝');
INSERT INTO `user` VALUES ('51', 'https://wx.qlogo.cn/mmopen/vi_32/RrvjIUqdGVy7PERpO96DUIvCmGa6iboMCDdODk3fZByxWM607nibCsXhROfwueOciafpLMmfGzSHZb8iaNiarPndCZw/132', '1', '18172505052', 'Seven.28', 'oYwIZ49CrdyKi9PFfPR_w52M1HJ8', 'e9f485ef65645e16addd0d7b3356fe84', '201500201149', '0', '0', '周靖富');

-- ----------------------------
-- Table structure for user_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_relation`;
CREATE TABLE `user_relation` (
  `active_id` int(11) NOT NULL,
  `passive_id` int(11) NOT NULL,
  PRIMARY KEY (`active_id`,`passive_id`),
  KEY `FK2c9evsph69u2x2p9j3ehfgrnf` (`passive_id`),
  CONSTRAINT `FK2c9evsph69u2x2p9j3ehfgrnf` FOREIGN KEY (`passive_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKi0kamf9qkhcj0cbkrrk113tob` FOREIGN KEY (`active_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户关注';

-- ----------------------------
-- Records of user_relation
-- ----------------------------
