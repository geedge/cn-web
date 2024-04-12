SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `sys_leader_election`;
CREATE TABLE `sys_leader_election`  (
    `election_key` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
    `leader_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '当前主 ID',
    `laset_active_ts` bigint(20) NOT NULL COMMENT '最后活跃时间戳',
    PRIMARY KEY (`election_key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
