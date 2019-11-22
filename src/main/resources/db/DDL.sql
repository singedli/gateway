-- 后台系统表
CREATE TABLE
IF NOT EXISTS `backon` (
	`id` INT (11) NOT NULL AUTO_INCREMENT,
	`system` VARCHAR (255) NOT NULL DEFAULT '' COMMENT '系统标识',
	`domain` VARCHAR (255) NOT NULL COMMENT '后台系统的域名或ip',
	`suffix` VARCHAR (20) DEFAULT '' COMMENT '后缀,系统接口的后缀如 .htm 或.do等',
	`status` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '状态信息:1表示有效，0表示失效',
	`description` varchar(255) DEFAULT NULL COMMENT '描述信息',
	`success_code` varchar(50) DEFAULT NULL COMMENT '返回码键',
	`success_value` varchar(50) DEFAULT NULL COMMENT '表成功的返回值',
	`is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标志位，1表示已删除，0表示未删除',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`create_by` VARCHAR (40) DEFAULT NULL,
	`update_by` VARCHAR (40) DEFAULT NULL
PRIMARY KEY (`id`),
  UNIQUE KEY `pk_backon_system` (`system`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- 后台系统接口表
CREATE TABLE
IF NOT EXISTS `backon_interface` (
	`id` INT (11) NOT NULL AUTO_INCREMENT,
	`name` varchar(255) NOT NULL,
	`system` VARCHAR (255) NOT NULL DEFAULT '' COMMENT '接口所属的系统，对应于backon表的system字段',
	`url` varchar(255) NOT NULL DEFAULT '',
	`status` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '状态信息:1表示有效，0表示失效',
	`http_method` varchar(10) NOT NULL DEFAULT 'post' COMMENT 'get or post',
	`version` varchar(10) DEFAULT '1.0.0',
	`is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标志位，1表示已删除，0表示未删除',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`create_by` varchar(40) DEFAULT NULL,
	`update_by` varchar(40) DEFAULT NULL
	PRIMARY KEY (`id`),
	UNIQUE KEY `pk_backon_system` (`system`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;


-- 自定义接口表
CREATE TABLE
IF NOT EXISTS `gateway_interface` (
	`id` INT (11) NOT NULL AUTO_INCREMENT,
	`name` varchar(255) NOT NULL,
	`url` varchar(255) NOT NULL DEFAULT '',
	`type` varchar(50) DEFAULT NULL COMMENT '1.表示透传接口 2.串行化组合接口 3.并行化组合接口 4.复杂逻辑组合接口',
	`status` tinyint(1) DEFAULT NULL COMMENT '状态信息:1 表示启用,0 表示停用',
	`http_method` varchar(10) NOT NULL DEFAULT 'post' COMMENT '请求后台的方式get or post',
	`system` varchar(50) NOT NULL COMMENT '该接口调用的系统,如涉及多个系统则用逗号隔开',
	`version` varchar(10) DEFAULT '1.0.0',
	`is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标志位，1表示已删除，0表示未删除',
	`invoke_config` text,
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`create_by` varchar(40) DEFAULT NULL,
	`update_by` varchar(40) DEFAULT NULL
	PRIMARY KEY (`id`),
	UNIQUE KEY `pk_tran_url` (`url`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

