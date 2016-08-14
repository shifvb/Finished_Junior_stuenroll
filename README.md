#  大三实训项目：stuenoll
辽宁省就业网转换系统v3

### 项目介绍
本项目于CentOS_7_x86_64上搭建，本人担任项目组中权限管理和用户管理模块的开发，并负责linux环境搭建任务

* 网络层
    * 基于JFinal技术开发

    * 使用了Shiro授权与认证模块

    * druid数据库连接池

    * 使用了Spring Bean

* 服务层
    * 使用了Kylin系统发送电子邮件功能(jodd)

    * 使用了RocketMQ实现系统间解耦

    * 使用了ActiveRecord实现事务

* 持久层
    * 使用了mycat数据库中间件+mariadb集群(目前是2主4从×２共12个)，实现了分布式数据库（读写分离，数据分片，主从备份）

    * 使用了MongoDB集群技术(ReplicaSet)，实现非重要信息（系统公告，图片信息等）的存储

    * 使用了Haproxy + Redis(entinel)集群提高了对于突发大访问量的承载能力