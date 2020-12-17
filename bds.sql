/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50712
 Source Host           : localhost:3306
 Source Schema         : bds

 Target Server Type    : MySQL
 Target Server Version : 50712
 File Encoding         : 65001

 Date: 17/12/2020 13:04:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for answer
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer`  (
  `answer_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `question_id` int(11) NOT NULL,
  PRIMARY KEY (`answer_id`) USING BTREE,
  INDEX `FKfiomvt17psxodcis3d8nmopx8`(`question_id`) USING BTREE,
  CONSTRAINT `FKfiomvt17psxodcis3d8nmopx8` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of answer
-- ----------------------------
INSERT INTO `answer` VALUES (1, 'A+', 1);
INSERT INTO `answer` VALUES (2, 'A-', 1);
INSERT INTO `answer` VALUES (3, 'B+', 1);
INSERT INTO `answer` VALUES (4, 'B-', 1);
INSERT INTO `answer` VALUES (5, 'O+', 1);
INSERT INTO `answer` VALUES (6, 'O-', 1);
INSERT INTO `answer` VALUES (7, 'AB+', 1);
INSERT INTO `answer` VALUES (8, 'AB-', 1);
INSERT INTO `answer` VALUES (9, 'Yes', 2);
INSERT INTO `answer` VALUES (10, 'No', 2);
INSERT INTO `answer` VALUES (11, '18-25', 3);
INSERT INTO `answer` VALUES (12, '25-35', 3);
INSERT INTO `answer` VALUES (13, '35-45', 3);
INSERT INTO `answer` VALUES (14, 'Above 45', 3);
INSERT INTO `answer` VALUES (15, 'Every 3 months', 4);
INSERT INTO `answer` VALUES (16, 'Once a Year', 4);
INSERT INTO `answer` VALUES (17, 'When someone asks', 4);
INSERT INTO `answer` VALUES (18, 'I never donated before', 4);
INSERT INTO `answer` VALUES (19, 'Male', 5);
INSERT INTO `answer` VALUES (20, 'Female', 5);
INSERT INTO `answer` VALUES (21, 'Religious View', 6);
INSERT INTO `answer` VALUES (22, 'Voluntary', 6);
INSERT INTO `answer` VALUES (23, 'Relatives/ Friends/ Co-workers', 6);
INSERT INTO `answer` VALUES (24, 'Token Gift/ Money', 6);
INSERT INTO `answer` VALUES (25, 'No Reason', 6);
INSERT INTO `answer` VALUES (26, 'Positive', 7);
INSERT INTO `answer` VALUES (27, 'Negative', 7);
INSERT INTO `answer` VALUES (28, 'Neutral', 7);
INSERT INTO `answer` VALUES (29, 'Yes', 8);
INSERT INTO `answer` VALUES (30, 'No', 8);
INSERT INTO `answer` VALUES (31, 'Yes', 9);
INSERT INTO `answer` VALUES (32, 'No', 9);
INSERT INTO `answer` VALUES (33, 'Yes', 10);
INSERT INTO `answer` VALUES (34, 'No', 10);

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`question_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (1, 'What is your Blood group?', NULL);
INSERT INTO `question` VALUES (2, 'Have you ever volunteered to donate blood before?', NULL);
INSERT INTO `question` VALUES (3, 'What is your age category?', NULL);
INSERT INTO `question` VALUES (4, 'How often do you donate blood?', NULL);
INSERT INTO `question` VALUES (5, 'What is your gender?', NULL);
INSERT INTO `question` VALUES (6, 'What is the reason to donate blood?', NULL);
INSERT INTO `question` VALUES (7, 'What is your feeling about donating blood?', NULL);
INSERT INTO `question` VALUES (8, 'Do you think that money should be paid to blood donors?', NULL);
INSERT INTO `question` VALUES (9, 'Do you think that token gift should be given to donors?', NULL);
INSERT INTO `question` VALUES (10, 'Are you willing to donate blood?', NULL);

-- ----------------------------
-- Table structure for response
-- ----------------------------
DROP TABLE IF EXISTS `response`;
CREATE TABLE `response`  (
  `response_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NULL DEFAULT NULL,
  `answer_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`response_id`) USING BTREE,
  INDEX `FKf985j5yq9eua3kn0wakeoeqhw`(`answer_id`) USING BTREE,
  INDEX `FK8orsb3yjy6wg78kipuismxp1o`(`user_id`) USING BTREE,
  CONSTRAINT `FK8orsb3yjy6wg78kipuismxp1o` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKf985j5yq9eua3kn0wakeoeqhw` FOREIGN KEY (`answer_id`) REFERENCES `answer` (`answer_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKfn7mdmqjwn7x5ila5mqiq6fm7` FOREIGN KEY (`answer_id`) REFERENCES `answer` (`answer_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKpooqp2aox2vbhrj0wswvgv0kf` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 261 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of response
-- ----------------------------
INSERT INTO `response` VALUES (151, '2020-11-22', 3, 56);
INSERT INTO `response` VALUES (152, '2020-11-22', 10, 56);
INSERT INTO `response` VALUES (153, '2020-11-22', 11, 56);
INSERT INTO `response` VALUES (154, '2020-11-22', 16, 56);
INSERT INTO `response` VALUES (155, '2020-11-22', 19, 56);
INSERT INTO `response` VALUES (156, '2020-11-22', 25, 56);
INSERT INTO `response` VALUES (157, '2020-11-22', 26, 56);
INSERT INTO `response` VALUES (158, '2020-11-22', 30, 56);
INSERT INTO `response` VALUES (159, '2020-11-22', 31, 56);
INSERT INTO `response` VALUES (160, '2020-11-22', 33, 56);
INSERT INTO `response` VALUES (161, '2020-11-22', 7, 57);
INSERT INTO `response` VALUES (162, '2020-11-22', 10, 57);
INSERT INTO `response` VALUES (163, '2020-11-22', 12, 57);
INSERT INTO `response` VALUES (164, '2020-11-22', 15, 57);
INSERT INTO `response` VALUES (165, '2020-11-22', 19, 57);
INSERT INTO `response` VALUES (166, '2020-11-22', 22, 57);
INSERT INTO `response` VALUES (167, '2020-11-22', 27, 57);
INSERT INTO `response` VALUES (168, '2020-11-22', 29, 57);
INSERT INTO `response` VALUES (169, '2020-11-22', 32, 57);
INSERT INTO `response` VALUES (170, '2020-11-22', 34, 57);
INSERT INTO `response` VALUES (171, '2020-11-22', 5, 58);
INSERT INTO `response` VALUES (172, '2020-11-22', 10, 58);
INSERT INTO `response` VALUES (173, '2020-11-22', 12, 58);
INSERT INTO `response` VALUES (174, '2020-11-22', 15, 58);
INSERT INTO `response` VALUES (175, '2020-11-22', 20, 58);
INSERT INTO `response` VALUES (176, '2020-11-22', 23, 58);
INSERT INTO `response` VALUES (177, '2020-11-22', 26, 58);
INSERT INTO `response` VALUES (178, '2020-11-22', 30, 58);
INSERT INTO `response` VALUES (179, '2020-11-22', 32, 58);
INSERT INTO `response` VALUES (180, '2020-11-22', 33, 58);
INSERT INTO `response` VALUES (181, '2020-11-22', 8, 59);
INSERT INTO `response` VALUES (182, '2020-11-22', 9, 59);
INSERT INTO `response` VALUES (183, '2020-11-22', 14, 59);
INSERT INTO `response` VALUES (184, '2020-11-22', 18, 59);
INSERT INTO `response` VALUES (185, '2020-11-22', 19, 59);
INSERT INTO `response` VALUES (186, '2020-11-22', 22, 59);
INSERT INTO `response` VALUES (187, '2020-11-22', 28, 59);
INSERT INTO `response` VALUES (188, '2020-11-22', 29, 59);
INSERT INTO `response` VALUES (189, '2020-11-22', 32, 59);
INSERT INTO `response` VALUES (190, '2020-11-22', 33, 59);
INSERT INTO `response` VALUES (191, '2020-11-22', 3, 60);
INSERT INTO `response` VALUES (192, '2020-11-22', 10, 60);
INSERT INTO `response` VALUES (193, '2020-11-22', 11, 60);
INSERT INTO `response` VALUES (194, '2020-11-22', 15, 60);
INSERT INTO `response` VALUES (195, '2020-11-22', 20, 60);
INSERT INTO `response` VALUES (196, '2020-11-22', 23, 60);
INSERT INTO `response` VALUES (197, '2020-11-22', 27, 60);
INSERT INTO `response` VALUES (198, '2020-11-22', 30, 60);
INSERT INTO `response` VALUES (199, '2020-11-22', 32, 60);
INSERT INTO `response` VALUES (200, '2020-11-22', 33, 60);
INSERT INTO `response` VALUES (201, '2020-11-22', 8, 61);
INSERT INTO `response` VALUES (202, '2020-11-22', 9, 61);
INSERT INTO `response` VALUES (203, '2020-11-22', 13, 61);
INSERT INTO `response` VALUES (204, '2020-11-22', 16, 61);
INSERT INTO `response` VALUES (205, '2020-11-22', 20, 61);
INSERT INTO `response` VALUES (206, '2020-11-22', 21, 61);
INSERT INTO `response` VALUES (207, '2020-11-22', 27, 61);
INSERT INTO `response` VALUES (208, '2020-11-22', 30, 61);
INSERT INTO `response` VALUES (209, '2020-11-22', 32, 61);
INSERT INTO `response` VALUES (210, '2020-11-22', 33, 61);
INSERT INTO `response` VALUES (211, '2020-11-22', 6, 62);
INSERT INTO `response` VALUES (212, '2020-11-22', 10, 62);
INSERT INTO `response` VALUES (213, '2020-11-22', 12, 62);
INSERT INTO `response` VALUES (214, '2020-11-22', 15, 62);
INSERT INTO `response` VALUES (215, '2020-11-22', 19, 62);
INSERT INTO `response` VALUES (216, '2020-11-22', 23, 62);
INSERT INTO `response` VALUES (217, '2020-11-22', 27, 62);
INSERT INTO `response` VALUES (218, '2020-11-22', 29, 62);
INSERT INTO `response` VALUES (219, '2020-11-22', 32, 62);
INSERT INTO `response` VALUES (220, '2020-11-22', 33, 62);
INSERT INTO `response` VALUES (221, '2020-11-22', 3, 63);
INSERT INTO `response` VALUES (222, '2020-11-22', 10, 63);
INSERT INTO `response` VALUES (223, '2020-11-22', 11, 63);
INSERT INTO `response` VALUES (224, '2020-11-22', 15, 63);
INSERT INTO `response` VALUES (225, '2020-11-22', 19, 63);
INSERT INTO `response` VALUES (226, '2020-11-22', 22, 63);
INSERT INTO `response` VALUES (227, '2020-11-22', 27, 63);
INSERT INTO `response` VALUES (228, '2020-11-22', 30, 63);
INSERT INTO `response` VALUES (229, '2020-11-22', 31, 63);
INSERT INTO `response` VALUES (230, '2020-11-22', 33, 63);
INSERT INTO `response` VALUES (231, '2020-11-22', 2, 64);
INSERT INTO `response` VALUES (232, '2020-11-22', 10, 64);
INSERT INTO `response` VALUES (233, '2020-11-22', 14, 64);
INSERT INTO `response` VALUES (234, '2020-11-22', 17, 64);
INSERT INTO `response` VALUES (235, '2020-11-22', 19, 64);
INSERT INTO `response` VALUES (236, '2020-11-22', 24, 64);
INSERT INTO `response` VALUES (237, '2020-11-22', 26, 64);
INSERT INTO `response` VALUES (238, '2020-11-22', 29, 64);
INSERT INTO `response` VALUES (239, '2020-11-22', 31, 64);
INSERT INTO `response` VALUES (240, '2020-11-22', 34, 64);
INSERT INTO `response` VALUES (241, '2020-11-22', 6, 65);
INSERT INTO `response` VALUES (242, '2020-11-22', 10, 65);
INSERT INTO `response` VALUES (243, '2020-11-22', 12, 65);
INSERT INTO `response` VALUES (244, '2020-11-22', 16, 65);
INSERT INTO `response` VALUES (245, '2020-11-22', 20, 65);
INSERT INTO `response` VALUES (246, '2020-11-22', 24, 65);
INSERT INTO `response` VALUES (247, '2020-11-22', 26, 65);
INSERT INTO `response` VALUES (248, '2020-11-22', 30, 65);
INSERT INTO `response` VALUES (249, '2020-11-22', 32, 65);
INSERT INTO `response` VALUES (250, '2020-11-22', 33, 65);
INSERT INTO `response` VALUES (251, '2020-11-26', 8, 67);
INSERT INTO `response` VALUES (252, '2020-11-26', 10, 67);
INSERT INTO `response` VALUES (253, '2020-11-26', 12, 67);
INSERT INTO `response` VALUES (254, '2020-11-26', 16, 67);
INSERT INTO `response` VALUES (255, '2020-11-26', 19, 67);
INSERT INTO `response` VALUES (256, '2020-11-26', 21, 67);
INSERT INTO `response` VALUES (257, '2020-11-26', 28, 67);
INSERT INTO `response` VALUES (258, '2020-11-26', 29, 67);
INSERT INTO `response` VALUES (259, '2020-11-26', 31, 67);
INSERT INTO `response` VALUES (260, '2020-11-26', 33, 67);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `full_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 80 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'jamith@mail.com', 'Jamith Nimantha', '0776644682', 'yQ6pjinvsfIWF/TCOR5oJRj3BampFwupmzVB7zjpFuU=', 'OFPQjYWSaOd6zHjm', 'ADMIN');
INSERT INTO `user` VALUES (56, 'mali@lk.com', 'Lasith Malinga', '0981233212', NULL, NULL, 'USER');
INSERT INTO `user` VALUES (57, 'pers@jm.com', 'Sugath Perera', '098237645', NULL, NULL, 'USER');
INSERT INTO `user` VALUES (58, 'pium@mail.com', 'Piumi Mali', '072348756', NULL, NULL, 'USER');
INSERT INTO `user` VALUES (59, 'moha@gmail.com', 'Mohan Sajith', '0765432123', NULL, NULL, 'USER');
INSERT INTO `user` VALUES (60, 'jk@mail.com', 'JK Yolwer', '0732376957', NULL, NULL, 'USER');
INSERT INTO `user` VALUES (61, 'niki@yahoo.com', 'Nikini Binari', '0823123783', NULL, NULL, 'USER');
INSERT INTO `user` VALUES (62, 'suja@menu.com', 'Sujatha Menuwa', '093472893', NULL, NULL, 'USER');
INSERT INTO `user` VALUES (63, 'sij@hmail.com', 'Sithum Pathum', '524423432332', NULL, NULL, 'USER');
INSERT INTO `user` VALUES (64, 'bin@isis.com', 'Mohommed Bin Laden', '0943824233', NULL, NULL, 'USER');
INSERT INTO `user` VALUES (65, 'hitler@nazi.gh', 'A. Hitler', '98422243343', NULL, NULL, 'USER');
INSERT INTO `user` VALUES (66, 'test@mail.com', 'Jamith', '12345678', NULL, NULL, 'USER');
INSERT INTO `user` VALUES (67, 'Lasi@mail.com', 'Lasith Malinga', '0932356832', NULL, NULL, 'USER');

SET FOREIGN_KEY_CHECKS = 1;
