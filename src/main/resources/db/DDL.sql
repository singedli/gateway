-- 后台系统表
CREATE TABLE
IF NOT EXISTS `backon` (
	`id` VARCHAR (64) NOT NULL,
	`system` VARCHAR (255) NOT NULL DEFAULT '' COMMENT '系统标识',
	`domain` VARCHAR (255) NOT NULL COMMENT '后台系统的域名或ip',
	`suffix` VARCHAR (20) NOT NULL DEFAULT '' COMMENT '后缀,系统接口的后缀如 .htm 或.do等,默认为空',
	`status` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '状态信息:1表示有效，0表示失效',
	`description` varchar(255) DEFAULT NULL COMMENT '描述信息',
	`success_code` varchar(50) NOT NULL COMMENT '返回码键',
	`success_value` varchar(50) NOT NULL COMMENT '表成功的返回值',
	`pre_interceptor` text COMMENT '系统级别接口调用前的拦截器的配置，使用JsonArray格式存储,默认为空',
  `post_interceptor` text COMMENT '系统级别接口调用后的拦截器的配置，使用JsonArray格式存储,默认为空',
	`is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标志位，1表示已删除，0表示未删除',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`create_by` VARCHAR (40) DEFAULT NULL,
	`update_by` VARCHAR (40) DEFAULT NULL,
PRIMARY KEY (`id`),
  UNIQUE KEY `pk_backon_system` (`system`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- 后台系统接口表
CREATE TABLE
IF NOT EXISTS `backon_interface` (
	`id` VARCHAR (64) NOT NULL,
	`name` varchar(255) NOT NULL,
	`system` VARCHAR (255) NOT NULL DEFAULT '' COMMENT '接口所属的系统，对应于backon表的system字段',
	`url` varchar(255) NOT NULL DEFAULT '',
	`status` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '状态信息:1表示有效，0表示失效',
	`http_method` varchar(10) NOT NULL DEFAULT 'post' COMMENT 'get or post',
	`version` varchar(10) DEFAULT '1.0.0',
	`http_header` varchar(255) DEFAULT 'application/json; charset=utf-8' COMMENT 'http请求头',
	`pre_interceptor` text COMMENT '接口调用前的拦截器的配置，多个拦截器使用逗号隔开,默认为空',
  `post_interceptor` text COMMENT '接口调用后的拦截器的配置，多个拦截器使用逗号隔开,默认为空',
	`is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标志位，1表示已删除，0表示未删除',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`create_by` varchar(40) DEFAULT NULL,
	`update_by` varchar(40) DEFAULT NULL,
	PRIMARY KEY (`id`),
-- 	UNIQUE KEY `pk_backon_system` (`system`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;


-- 自定义接口表
CREATE TABLE
IF NOT EXISTS `gateway_interface` (
	`id` VARCHAR (64) NOT NULL,
	`name` varchar(255) NOT NULL,
	`url` varchar(255) NOT NULL DEFAULT '',
	`backon_url` text NOT NULL COMMENT '该接口需要调用的后台系统接口,并发和复杂类型用jsonArray的存储',
	`type` varchar(50) NOT NULL COMMENT 'PASS表示透传接口 ，CONCURRENT表示并行化组合接口 COMPLICATE表示复杂逻辑组合接口',
	`status` tinyint(1) DEFAULT '1' COMMENT '状态信息:1 表示启用,0 表示停用',
-- 	`http_method` varchar(10) NOT NULL DEFAULT 'post' COMMENT '请求后台的方式get or post',
	`system` varchar(50) NOT NULL COMMENT '该接口调用的系统,如涉及多个系统则用逗号隔开',
	`version` varchar(10) DEFAULT '1.0.0',
	`pre_interceptors` text COMMENT '接口调用前的拦截器的配置，多个拦截器使用逗号隔开,默认为空',
  `post_interceptors` text COMMENT '接口调用后的拦截器的配置，多个拦截器使用逗号隔开,默认为空',
	`is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标志位，1表示已删除，0表示未删除',
	`invoke_config` text,
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`create_by` varchar(40) DEFAULT NULL,
	`update_by` varchar(40) DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `pk_tran_url` (`url`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;


-- 网关缓存配置表
CREATE TABLE
IF NOT EXISTS `gateway_cache` (
	`id` VARCHAR (64) NOT NULL,
	`name` varchar(255) NOT NULL,
	`url` varchar(255) NOT NULL ,
	`backon_url` text NOT NULL COMMENT '该接口需要调用的后台系统接口,并发和复杂类型用jsonArray的存储',
	`request_body` text NOT NULL COMMENT '接口请求参数key配置，以配置的key作为缓存的field',
	`response_body` text DEFAULT '' COMMENT '接口响应字段配置 ，缓存只存接口返回数据中配置的数据',
	`status` tinyint(1) DEFAULT '1' COMMENT '缓存开关:1 表示启用,0 表示停用',
	`result_num` int(225)  DEFAULT 20 COMMENT '缓存条数：默认为20条以内',
  `expire_time` int(225) DEFAULT 30 COMMENT '缓存过期时间（单位分钟）：默认为30分钟',
	`create_time` timestamp DEFAULT CURRENT_TIMESTAMP,
	`update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`create_by` varchar(40) DEFAULT NULL,
	`update_by` varchar(40) DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `pk_tran_url` (`url`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- 网关接口配置表
CREATE TABLE
IF NOT EXISTS `gateway_cache` (
	`id` VARCHAR (64) NOT NULL,
	`url` varchar(255) NOT NULL  DEFAULT '' COMMENT '接口地址，对应gateway_interface中的url',
	`time_unit` varchar(5) NOT NULL COMMENT '单位时间',
	`key_limit` varchar(255) NOT NULL COMMENT '该接口需要验证的参数的key',
	`max_count` double(50) NOT NULL COMMENT '单位时间内最大的访问次数',
	`status` tinyint(1) DEFAULT '1' COMMENT '状态信息:1 表示启用,0 表示停用',
	`create_time` timestamp DEFAULT CURRENT_TIMESTAMP,
	`update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`create_by` varchar(40) DEFAULT NULL,
	`update_by` varchar(40) DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `pk_tran_url` (`url`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

--请求来源来源类型表
CREATE TABLE
IF NOT EXISTS `request_type` (
  `id` int(36) NOT NULL AUTO_INCREMENT,
  `type` varchar(36) NOT NULL COMMENT '请求来源，浏览器或者app',
  `agent` varchar(50)  NOT NULL COMMENT 'request请求头中的参数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- 网关报文转换的配置表
CREATE TABLE
IF NOT EXISTS `message_converter` (
	`id` VARCHAR (64) NOT NULL,
	`name` varchar(255) NOT NULL,
	`url` varchar(255) NOT NULL ,
	`backon_url` text NOT NULL COMMENT '该接口需要调用的后台系统接口,并发和复杂类型用jsonArray的存储',
	`request_config` text NOT NULL COMMENT '请求报文配置:A = a,B.C = b.c (=左边为网关请求的报文字段，右边为要转换成的报文字段)',
	`response_config` text NOT NULL COMMENT '响应报文配置:A = a,B.C = b.c (=左边为网关响应的报文字段，右边为要转换成的报文字段)',
	`request_struct` text NOT NULL COMMENT '请求报文格式配置',
	`response_struct` text NOT NULL COMMENT '响应报文格式配置',
	`status` tinyint(1) DEFAULT '1' COMMENT '1 表示启用,0 表示停用',
	`is_deleted` tinyint(1)  DEFAULT '0' COMMENT '逻辑删除标志位，1表示已删除，0表示未删除',
	`create_time` timestamp DEFAULT CURRENT_TIMESTAMP,
	`update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`create_by` varchar(40) DEFAULT NULL,
	`update_by` varchar(40) DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `pk_tran_url` (`url`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;