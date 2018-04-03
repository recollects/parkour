
CREATE TABLE `xxx_asyn_executor_cmd` (
`id`  bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'id主键' ,
`gmt_create`  timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间' ,
`gmt_modified`  timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间' ,
`creator`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '创建人' ,
`modifier`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '修改时间' ,
`business_no`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '业务编号' ,
`cmd_type`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '命令类型' ,
`status`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '命令状态' ,
`retry_count`  int(11) NULL DEFAULT NULL COMMENT '重试次数' ,
`next_execute_time`  timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '下次执行时间' ,
`context`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '执行产生上下文' ,
`exception_context`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '执行发生的异常信息' ,
`host_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT 'host名称' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
COMMENT='异步命令表'
AUTO_INCREMENT=1
ROW_FORMAT=DYNAMIC
;