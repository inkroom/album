-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.15 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 image 的数据库结构
CREATE DATABASE IF NOT EXISTS `image` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `image`;

-- 导出  表 image.account 结构
CREATE TABLE IF NOT EXISTS `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) COLLATE utf8_bin NOT NULL,
  `username` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `password` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `salt` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `size` bigint(20) NOT NULL DEFAULT '0' COMMENT '相册已用大小',
  `capacity` bigint(20) NOT NULL DEFAULT '10240000' COMMENT '相册容量',
  `role` varchar(5) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='账号表';

-- 正在导出表  image.account 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` (`id`, `nickname`, `username`, `password`, `salt`, `create_time`, `size`, `capacity`, `role`) VALUES
	(1, 'GOSICK', 'inkbox', '79190a6239e63cb82953d3583cbcf703', 'jk9blnkc', '2017-03-01 12:31:31', -241691, 10240000, '0'),
	(4, '你猜一猜', 'test1', '5f7ca59c7a1b9b66cb608b7efd6d04b8', '2hl7c03o', '2017-09-11 11:34:20', 0, 10240000, '1'),
	(5, '测试注册', '注册', '366315892fc9bc210df4917b9d9500ab', 'feuxa9hn', '2017-09-11 11:38:17', 0, 10240000, '1');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;

-- 导出  表 image.album 结构
CREATE TABLE IF NOT EXISTS `album` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '相册名',
  `authority` bigint(20) NOT NULL DEFAULT '0' COMMENT '权限 0：所有人可见，1：仅自己可见，大于等于2：回答问题可见，数字即问题id',
  `owner` bigint(20) NOT NULL DEFAULT '0' COMMENT '拥有者',
  `cover` bigint(20) NOT NULL DEFAULT '0' COMMENT '封面——图片路径',
  `number` int(11) NOT NULL DEFAULT '0' COMMENT '图片数量',
  `size` bigint(20) NOT NULL DEFAULT '0' COMMENT '已用空间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `change_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近修改时间——上传图片',
  `content` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '相册说明',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '分类',
  PRIMARY KEY (`id`),
  KEY `FK_album_account` (`owner`),
  CONSTRAINT `FK_album_account` FOREIGN KEY (`owner`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='相册';

-- 正在导出表  image.album 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `album` DISABLE KEYS */;
INSERT INTO `album` (`id`, `name`, `authority`, `owner`, `cover`, `number`, `size`, `create_time`, `change_time`, `content`, `type`) VALUES
	(3, 'K', 1, 1, 34, 9, -45087, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '这个相册叫k', 0),
	(4, '灰色', 0, 1, 15, 3, 168, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '来自灰色三部曲', 0),
	(20, '随便创建的', 12, 1, 44, 43, 0, '2017-09-12 11:22:35', '2017-09-12 11:22:35', '真的是随便来的', 0);
/*!40000 ALTER TABLE `album` ENABLE KEYS */;

-- 导出  表 image.config 结构
CREATE TABLE IF NOT EXISTS `config` (
  `type` int(11) NOT NULL,
  `path` varchar(100) COLLATE utf8_bin NOT NULL,
  `content` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  UNIQUE KEY `type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配置表';

-- 正在导出表  image.config 的数据：~9 rows (大约)
/*!40000 ALTER TABLE `config` DISABLE KEYS */;
INSERT INTO `config` (`type`, `path`, `content`) VALUES
	(1, '1000', '清空数据库存储的ip 的时间间隔，单位秒'),
	(2, '200', '单位时间内允许的最高访问次数'),
	(3, '60000', '限制访问的单位时间，单位毫秒'),
	(4, '60000', 'redis存储的清空ip等数据的时长，即配置更新的最长时间'),
	(5, 'E:\\娱乐\\图片\\test\\', '默认图片存储路径'),
	(6, '14608800', '图片最大大小'),
	(403, 'E:\\403.jpg', '图片403（没权限）时默认返回路径'),
	(404, 'E:\\test.jpg', '图片404时默认返回路径'),
	(406, 'E:\\test.jpg', '图片406（参数错误）时默认返回路径');
/*!40000 ALTER TABLE `config` ENABLE KEYS */;

-- 导出  表 image.ip 结构
CREATE TABLE IF NOT EXISTS `ip` (
  `ip_address` varchar(15) COLLATE utf8_bin NOT NULL,
  `count` int(11) NOT NULL,
  `start_time` datetime NOT NULL,
  `last_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用来做访问频率限制';


-- 导出  表 image.question 结构
CREATE TABLE IF NOT EXISTS `question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '相册题干',
  `answer` varchar(50) COLLATE utf8_bin NOT NULL,
  `owner` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_question_album` (`owner`),
  CONSTRAINT `FK_question_album` FOREIGN KEY (`owner`) REFERENCES `album` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='回答问题存放的答案';

-- 导出  表 image.upload 结构
CREATE TABLE IF NOT EXISTS `upload` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `path` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT NULL COMMENT '状态',
  `owner` bigint(20) NOT NULL COMMENT '属于哪个相册',
  `md5` varchar(300) NOT NULL COMMENT '文件md5值，防止相同图片重复上传',
  PRIMARY KEY (`id`),
  KEY `FK_upload_album` (`owner`),
  CONSTRAINT `FK_upload_album` FOREIGN KEY (`owner`) REFERENCES `album` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='上传文件';


-- 导出  过程 image.delete_image 结构
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_image`(
	IN `owner_id` BIGINT,
	IN `album_id` BIGINT,
	IN `image_id` BIGINT,
	IN `size` BIGINT
)
    COMMENT '删除图片'
BEGIN
	update account,album set account.size=account.size-size,album.size=album.size-size
	  where account.id=owner_id and album.id=album_id
	 and album.owner = account.id;
	 
	 delete from upload where upload.id = image_id;
END//
DELIMITER ;

-- 导出  过程 image.update_ip 结构
DELIMITER //
CREATE DEFINER=`image`@`%` PROCEDURE `update_ip`(
	IN `ip_a` VARCHAR(20)


)
BEGIN
	declare error int(1);
	set error = 0;
	update ip set ip.count=ip.count+1,ip.last_time=now() where ip.ip_address=ip_a;
	set error=error+row_count();
	
	if error = 0 then
		insert into ip values(ip_a,1,now(),now());
	end if;
	select ip_address as ip,count,start_time as startTime,last_time as lastTime from ip where ip.ip_address = ip_a limit 1;
	
END//
DELIMITER ;

-- 导出  触发器 image.album_after_insert 结构
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `album_after_insert` AFTER INSERT ON `album` FOR EACH ROW BEGIN
	if new.authority>=2 then
	 update question set question.owner = new.id where question.id = new.authority  limit 1;
	 end if;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 导出  触发器 image.album_after_update 结构
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `album_after_update` AFTER UPDATE ON `album` FOR EACH ROW BEGIN
	if new.authority!= old.authority and old.authority>=2 then
	delete from  question where question.id = old.authority limit 1;
	update question set owner = new.id where question.id = new.authority;
	end if;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

INSERT INTO `album` ( `name`, `authority`, `owner`,  `create_time`, `change_time`, `content`, `type`) VALUES
	('GOSICK', 1, 1,'2017-08-15 23:41:29', '2017-08-15 23:41:29', '这个相册叫k', 0),
	( 'K', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '来自灰色三部曲', 0),
	( '壁纸', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '来自灰色三部曲', 0),
	( '从零开始的异世界生活', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '来自灰色三部曲', 0),
	( '刀剑神域', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '来自灰色三部曲', 0),
	( '丹特丽安的书架', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '来自灰色三部曲', 0),
	( '动物', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '来自灰色三部曲', 0),
	( '二次元', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '来自灰色三部曲', 0),
	( '黑执事', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '来自灰色三部曲', 0),
	( '灰色', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '来自灰色三部曲', 0),
	( '绝园的暴风雨', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '绝园的暴风雨', 0),
	( '魁拔', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '魁拔', 0),
	( '琉璃神社', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '琉璃神社', 0),
	( '命运石之门', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '命运石之门', 0),
	( '魔法禁书目录', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '魔法禁书目录', 0),
	( '千反田爱榴', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '千反田爱榴', 0),
	( '秦时明月', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '秦时明月', 0),
	( 'rewrite', 12,1, '2017-09-12 11:22:35', '2017-09-12 11:22:35', 'rewrite', 0),
	( '神的记事本', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '神的记事本', 0),
	( '素描', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '素描', 0),
	( '雪乃雪之下', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '雪乃雪之下', 0),
	( '妖精的尾巴', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '妖精的尾巴', 0),
	( '缘之空', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '缘之空', 0),
	( '战场原黑仪', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '战场原黑仪', 0),
	( '终结的炽天使', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '终结的炽天使', 0),
	( '专列', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '专列', 0),
	( '罪恶王冠', 0, 1, '2017-08-15 23:41:29', '2017-08-15 23:41:29', '罪恶王冠', 0);



-- 2018-08-30
alter table album add column last_modify datetime;
UPDATE album SET last_modify = now();
ALTER TABLE album modify COLUMN last_modify datetime NOT NULL ;


