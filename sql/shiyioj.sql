/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80402
 Source Host           : localhost:3306
 Source Schema         : shiyioj

 Target Server Type    : MySQL
 Target Server Version : 80402
 File Encoding         : 65001

 Date: 20/02/2025 19:40:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for post_favour
-- ----------------------------
DROP TABLE IF EXISTS `post_favour`;
CREATE TABLE `post_favour`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `postId` bigint NOT NULL COMMENT '帖子 id',
  `userId` bigint NOT NULL COMMENT '创建用户 id',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_postId`(`postId` ASC) USING BTREE,
  INDEX `idx_userId`(`userId` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子收藏' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post_favour
-- ----------------------------

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `tags` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标签列表（json 数组）',
  `answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '答案',
  `submitNum` int NULL DEFAULT 0 COMMENT '题目提交数',
  `acceptNum` int NULL DEFAULT 0 COMMENT '题目通过数',
  `judgeCase` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '测试用例(json 数组)',
  `judgeConfig` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '评测配置 (json 对象)',
  `thumbNum` int NOT NULL DEFAULT 0 COMMENT '点赞数',
  `favourNum` int NOT NULL DEFAULT 0 COMMENT '收藏数',
  `userId` bigint NOT NULL COMMENT '创建用户 id',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_userId`(`userId` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '帖子' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (1, 'A + D', '\n# Zzuli-search Quickstart Guide\n\n下载压缩包解压点击start就行\n\n# About Zzuli-search\n\n*这是一个可以从zzuli官网搜索特定信息的客户端*\n\n前端：javafx23+Spring6+ikonli+materialfx+AnimateFX\n\n后端：SpringBoot3+elasticsearch8+htmlunit\n\n虽然采用了最新的graalvm进行构建，但是结果并不令人满意，但还是将graalvm构建出来的程序发布出来，如果有人了解graalvm并有办法改善目前的情况，请及时联系我，我会尝试重新构建\n\n由于本人学业繁忙，此项目也只是抽空完成的，许多功能和优化尚未完善，如有问题可以留下issue，如果项目得到足够关注，我可能抽空进行更新。如果存在有能力的人可以完善此项目并希望您进行开源，我将会非常感谢您。\n', '[\"二叉树\"]', '新的答案11', 0, 0, '[{\"input\":\"1 2\",\"output\":\"1\"}]', '{\"timeLimit\":30000,\"memoryLimit\":300000,\"stackLimit\":1}', 0, 0, 1, '2025-01-10 19:57:02', '2025-02-14 21:19:59', 0);
INSERT INTO `question` VALUES (2, 'A + B', '暴力', '[\"栈\",\"简单\"]', '暴力', 0, 0, '[{\"input\":\"1 2\",\"output\":\"3 4\"}]', '{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}', 0, 0, 1, '2025-01-18 20:54:26', '2025-01-20 13:55:18', 1);
INSERT INTO `question` VALUES (3, '1', '1', '[\"1\"]', '1', 0, 0, '[{\"input\":\"1\",\"output\":\"1\"}]', '{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}', 0, 0, 1, '2025-01-20 20:09:53', '2025-01-20 20:09:53', 0);
INSERT INTO `question` VALUES (4, '1', '1', '[\"1\",\"2\"]', '1', 0, 0, '[{\"input\":\"1\",\"output\":\"2\"}]', '{\"timeLimit\":10001,\"memoryLimit\":10001,\"stackLimit\":10001}', 0, 0, 1, '2025-01-20 20:10:08', '2025-01-20 20:10:08', 0);

-- ----------------------------
-- Table structure for question_submit
-- ----------------------------
DROP TABLE IF EXISTS `question_submit`;
CREATE TABLE `question_submit`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `language` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '提交语言',
  `questionId` int NOT NULL COMMENT '题目 id',
  `code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '代码',
  `judgeInfo` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '判题信息(json 数组)',
  `status` int NOT NULL DEFAULT 0 COMMENT '状态：0-未判题，1-判题中 2-通过，3-未通过',
  `userId` bigint NOT NULL COMMENT '用户 id',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_postId`(`questionId` ASC) USING BTREE,
  INDEX `idx_userId`(`userId` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1890390650754985986 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子点赞' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question_submit
-- ----------------------------
INSERT INTO `question_submit` VALUES (1877933894738604033, 'java', 1, '1111', '{}', 0, 1, '2025-01-11 12:21:45', '2025-01-11 12:21:45', 0);
INSERT INTO `question_submit` VALUES (1881684107415068674, 'java', 1, '@SpringBootApplication(exclude = {RedisAutoConfiguration.class})\r\n@MapperScan(\"com.shiyi.shiyioj.mapper\")\r\n@EnableScheduling\r\n@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)\r\npublic class MainApplication {\r\n\r\n    public static void main(String[] args) {\r\n        SpringApplication.run(MainApplication.class, args);\r\n    }\r\n\r\n}', '{}', 0, 1, '2025-01-21 20:43:45', '2025-01-21 20:43:45', 0);
INSERT INTO `question_submit` VALUES (1883487849319804930, 'java', 1, '', '{}', 0, 1, '2025-01-26 20:11:11', '2025-01-26 20:11:11', 0);
INSERT INTO `question_submit` VALUES (1883488422395994114, 'java', 1, '', '{\"message\":\"Wrong Answer\",\"memory\":1,\"time\":1}', 2, 1, '2025-01-26 20:13:28', '2025-01-26 20:13:28', 0);
INSERT INTO `question_submit` VALUES (1883493622682775553, 'java', 1, '111111', '{\"message\":\"Wrong Answer\",\"memory\":1,\"time\":1}', 2, 1, '2025-01-26 20:34:07', '2025-01-26 20:34:07', 0);
INSERT INTO `question_submit` VALUES (1890365997474967554, 'java', 1, 'import java.io.PrintStream;\r\nimport java.nio.charset.StandardCharsets;\r\nimport java.util.Scanner;\r\n\r\npublic class Main\r\n{\r\n            public static void main(String args[]) throws Exception\r\n            {\r\n                    Scanner cin=new Scanner(System.in);\r\n                    int a=cin.nextInt(),b=cin.nextInt();\r\n//                    System.out.println(\"结果:\"+a+b);\r\n                System.out.println(\"中文\" + args[0]);\r\n            }\r\n}\r\n', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":198}', 2, 1, '2025-02-14 19:42:29', '2025-02-14 19:42:30', 0);
INSERT INTO `question_submit` VALUES (1890370095121248258, 'java', 1, 'import java.io.PrintStream;\r\nimport java.nio.charset.StandardCharsets;\r\nimport java.util.Scanner;\r\n\r\npublic class Main\r\n{\r\n            public static void main(String args[]) throws Exception\r\n            {\r\n                    Scanner cin=new Scanner(System.in);\r\n                    int a=cin.nextInt(),b=cin.nextInt();\r\n//                    System.out.println(\"结果:\"+a+b);\r\n                System.out.println(\"中文\" + args[0]);\r\n            }\r\n}\r\n', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":196}', 2, 1, '2025-02-14 19:58:46', '2025-02-14 19:58:47', 0);
INSERT INTO `question_submit` VALUES (1890370659875893250, 'java', 1, 'import java.io.PrintStream;\r\nimport java.nio.charset.StandardCharsets;\r\nimport java.util.Scanner;\r\n\r\npublic class Main\r\n{\r\n            public static void main(String args[]) throws Exception\r\n            {\r\n                    Scanner cin=new Scanner(System.in);\r\n                    int a=cin.nextInt(),b=cin.nextInt();\r\n//                    System.out.println(\"结果:\"+a+b);\r\n                System.out.println(\"中文\" + args[0]);\r\n            }\r\n}\r\n', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":219}', 2, 1, '2025-02-14 20:01:01', '2025-02-14 20:01:02', 0);
INSERT INTO `question_submit` VALUES (1890370850045636609, 'java', 1, 'import java.io.PrintStream;\r\nimport java.nio.charset.StandardCharsets;\r\nimport java.util.Scanner;\r\n\r\npublic class Main\r\n{\r\n            public static void main(String args[]) throws Exception\r\n            {\r\n                    Scanner cin=new Scanner(System.in);\r\n                    int a=cin.nextInt(),b=cin.nextInt();\r\n//                    System.out.println(\"结果:\"+a+b);\r\n                System.out.println(\"中文\" + args[0]);\r\n            }\r\n}\r\n', '{\"message\":\"Wrong Answer\",\"memory\":0,\"time\":271}', 2, 1, '2025-02-14 20:01:46', '2025-02-14 20:01:47', 0);
INSERT INTO `question_submit` VALUES (1890371218930479105, 'java', 1, 'import java.io.PrintStream;\r\nimport java.nio.charset.StandardCharsets;\r\nimport java.util.Scanner;\r\n\r\npublic class Main\r\n{\r\n            public static void main(String args[]) throws Exception\r\n            {\r\n                    Scanner cin=new Scanner(System.in);\r\n                    int a=cin.nextInt(),b=cin.nextInt();\r\n//                    System.out.println(\"结果:\"+a+b);\r\n                System.out.println(\"中文\" + args[0]);\r\n            }\r\n}\r\n', '{\"message\":\"Wrong Answer\",\"memory\":42980,\"time\":227}', 2, 1, '2025-02-14 20:03:14', '2025-02-14 20:03:18', 0);
INSERT INTO `question_submit` VALUES (1890387571934523394, 'java', 1, 'import java.io.PrintStream;\r\nimport java.nio.charset.StandardCharsets;\r\nimport java.util.Scanner;\r\n\r\npublic class Main\r\n{\r\n            public static void main(String args[]) throws Exception\r\n            {\r\n                    Scanner cin=new Scanner(System.in);\r\n                    int a=cin.nextInt(),b=cin.nextInt();\r\n//                    System.out.println(\"结果:\"+a+b);\r\n                System.out.println(args[0]);\r\n            }\r\n}\r\n', '{\"message\":\"Wrong Answer\",\"memory\":43136,\"time\":197}', 2, 1, '2025-02-14 21:08:13', '2025-02-14 21:08:14', 0);
INSERT INTO `question_submit` VALUES (1890388485713981441, 'java', 1, 'import java.io.PrintStream;\r\nimport java.nio.charset.StandardCharsets;\r\nimport java.util.Scanner;\r\n\r\npublic class Main\r\n{\r\n            public static void main(String args[]) throws Exception\r\n            {\r\n                    Scanner cin=new Scanner(System.in);\r\n                    int a=cin.nextInt(),b=cin.nextInt();\r\n//                    System.out.println(\"结果:\"+a+b);\r\n                System.out.println(args[0]);\r\n            }\r\n}\r\n', '{\"message\":\" Memory Limit Exceeded\",\"memory\":43380,\"time\":230}', 2, 1, '2025-02-14 21:11:51', '2025-02-14 21:11:52', 0);
INSERT INTO `question_submit` VALUES (1890388781651488770, 'java', 1, 'import java.io.PrintStream;\r\nimport java.nio.charset.StandardCharsets;\r\nimport java.util.Scanner;\r\n\r\npublic class Main\r\n{\r\n            public static void main(String args[]) throws Exception\r\n            {\r\n                    Scanner cin=new Scanner(System.in);\r\n                    int a=cin.nextInt(),b=cin.nextInt();\r\n//                    System.out.println(\"结果:\"+a+b);\r\n                System.out.println(args[0]);\r\n            }\r\n}\r\n', '{\"message\":\" Memory Limit Exceeded\",\"memory\":42960,\"time\":212}', 2, 1, '2025-02-14 21:13:01', '2025-02-14 21:13:02', 0);
INSERT INTO `question_submit` VALUES (1890388982965497858, 'java', 1, 'import java.io.PrintStream;\r\nimport java.nio.charset.StandardCharsets;\r\nimport java.util.Scanner;\r\n\r\npublic class Main\r\n{\r\n            public static void main(String args[]) throws Exception\r\n            {\r\n                    Scanner cin=new Scanner(System.in);\r\n                    int a=cin.nextInt(),b=cin.nextInt();\r\n//                    System.out.println(\"结果:\"+a+b);\r\n                System.out.println(args[0]);\r\n            }\r\n}\r\n', '{\"message\":\" Memory Limit Exceeded\",\"memory\":43044,\"time\":215}', 2, 1, '2025-02-14 21:13:49', '2025-02-14 21:13:50', 0);
INSERT INTO `question_submit` VALUES (1890390650754985986, 'java', 1, 'import java.io.PrintStream;\r\nimport java.nio.charset.StandardCharsets;\r\nimport java.util.Scanner;\r\n\r\npublic class Main\r\n{\r\n            public static void main(String args[]) throws Exception\r\n            {\r\n                    Scanner cin=new Scanner(System.in);\r\n                    int a=cin.nextInt(),b=cin.nextInt();\r\n//                    System.out.println(\"结果:\"+a+b);\r\n                System.out.println(args[0]);\r\n            }\r\n}\r\n', '{\"message\":\"Accepted\",\"memory\":42972,\"time\":261}', 2, 1, '2025-02-14 21:20:27', '2025-02-14 21:20:28', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userAccount` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `userPassword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `unionId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '微信开放平台id',
  `mpOpenId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公众号openId',
  `userName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `userAvatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
  `userProfile` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户简介',
  `userRole` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_unionId`(`unionId` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'shiyi', '34847524a1bf0f0cba18c1c0c72c42b0', NULL, NULL, '失意', NULL, NULL, 'admin', '2024-12-28 20:22:35', '2025-01-10 20:47:12', 0);

SET FOREIGN_KEY_CHECKS = 1;
