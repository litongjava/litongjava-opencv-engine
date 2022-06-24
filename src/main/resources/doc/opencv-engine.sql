/*
SQLyog Ultimate v8.32 
MySQL - 5.5.62-log : Database - opencv_engine
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`opencv_engine` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `opencv_engine`;

/*Table structure for table `mail_receiver` */

DROP TABLE IF EXISTS `mail_receiver`;

CREATE TABLE `mail_receiver` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `to` varchar(255) NOT NULL COMMENT '收件人',
  `desc` varchar(255) NOT NULL COMMENT '收件人描述',
  `status` tinyint(1) DEFAULT NULL COMMENT '是否开启',
  `is_del` char(1) DEFAULT '0' COMMENT '是否删除,1删除,0未删除',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='邮件发送记录';

/*Data for the table `mail_receiver` */

insert  into `mail_receiver`(`id`,`to`,`desc`,`status`,`is_del`,`create_by`,`create_time`,`update_by`,`update_time`,`remarks`) values (1,'xxx@qq.com','李通',1,'0',NULL,'2020-09-25 20:38:49',NULL,'2020-09-26 09:30:32',NULL);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `number` int(11) unsigned DEFAULT NULL COMMENT '编号',
  `login_name` varchar(100) DEFAULT NULL COMMENT '登录名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '昵称',
  `register_ip` varchar(255) DEFAULT NULL COMMENT '注册ip',
  `is_del` char(1) DEFAULT '0' COMMENT '是否删除,1删除,0未删除',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`number`,`login_name`,`password`,`nick_name`,`register_ip`,`is_del`,`create_by`,`create_time`,`update_by`,`update_time`,`remarks`) values ('afce722c6dd849ab97bc59b4b43bc1bf',1,'admin','c7540eb7e65b553ec1ba6b20de79608',NULL,NULL,'0',NULL,'2020-08-29 21:59:07',NULL,'2020-08-29 21:59:07',NULL);

/*Table structure for table `t_cron4j_task_info` */

DROP TABLE IF EXISTS `t_cron4j_task_info`;

CREATE TABLE `t_cron4j_task_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_desc` varchar(255) NOT NULL COMMENT '任务信息',
  `task_mode` varchar(255) NOT NULL COMMENT '任务模式',
  `cron` varchar(255) NOT NULL COMMENT '表达式',
  `charge` varchar(255) NOT NULL COMMENT '负责人',
  `class_name` varchar(255) NOT NULL COMMENT '类名',
  `status` tinyint(1) DEFAULT NULL COMMENT '是否开启',
  `is_del` char(1) DEFAULT '0' COMMENT '是否删除,1删除,0未删除',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_cron4j_task_info` */

insert  into `t_cron4j_task_info`(`id`,`task_desc`,`task_mode`,`cron`,`charge`,`class_name`,`status`,`is_del`,`create_by`,`create_time`,`update_by`,`update_time`,`remarks`) values (1,'测试数据连接','default','*/30 * * * *','李通','com.litong.modules.monitoring.db.task.DbConnectService',1,'0',NULL,'2020-09-26 19:53:47',NULL,'2020-09-27 09:57:38',NULL);

/*Table structure for table `t_db_connect_info` */

DROP TABLE IF EXISTS `t_db_connect_info`;

CREATE TABLE `t_db_connect_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `jdbc_url` varchar(255) NOT NULL COMMENT '数据库地址',
  `jdbc_user` varchar(255) NOT NULL COMMENT '用户名',
  `jdbc_pswd` varchar(255) DEFAULT NULL COMMENT '密码',
  `status` int(1) unsigned DEFAULT '1' COMMENT '状态 0关闭,1开启',
  `is_del` char(1) DEFAULT '0' COMMENT '是否删除,1删除,0未删除',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_db_connect_info` */

insert  into `t_db_connect_info`(`id`,`name`,`jdbc_url`,`jdbc_user`,`jdbc_pswd`,`status`,`is_del`,`create_by`,`create_time`,`update_by`,`update_time`,`remarks`) values (1,'李通本机数据库','jdbc:mysql://localhost/monitoring_db?characterEncoding=UTF8','root','',1,'0',NULL,'2020-09-26 15:52:36',NULL,'2020-09-27 15:35:36',NULL);

/*Table structure for table `t_log_db_connected` */

DROP TABLE IF EXISTS `t_log_db_connected`;

CREATE TABLE `t_log_db_connected` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `url` varchar(255) NOT NULL COMMENT '连接地址',
  `result` varchar(32) DEFAULT NULL COMMENT '连接结果,1 成功或者0失败',
  `is_del` char(1) DEFAULT '0' COMMENT '是否删除,1删除,0未删除',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='数据库连接记录';

/*Data for the table `t_log_db_connected` */

/*Table structure for table `t_log_mail_send` */

DROP TABLE IF EXISTS `t_log_mail_send`;

CREATE TABLE `t_log_mail_send` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `to` varchar(255) NOT NULL COMMENT '收件人',
  `subject` varchar(255) NOT NULL COMMENT '主题',
  `content` text NOT NULL COMMENT '邮件内容',
  `is_del` char(1) DEFAULT '0' COMMENT '是否删除,1删除,0未删除',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='邮件发送记录';

/*Data for the table `t_log_mail_send` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
