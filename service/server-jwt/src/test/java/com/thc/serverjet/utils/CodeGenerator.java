package com.thc.serverjet.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

public class CodeGenerator {

    // 要生成的表名
    private static String[] tables = { "t_teacher"};

    // 输出的地方
    private static String outputDir = "/Users/thc/Desktop/master/watch/sample/document" ;


    // 数据库配置四要素
    private static String driverName = "com.mysql.cj.jdbc.Driver";
    // 注意库名
    private static String url = "jdbc:mysql://127.0.0.1:3306/watch_db?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2b8";
    private static String username = "root";
    private static String password = "thcasdlkj";

    // 包配置 parent.modulesName
    private static String modulesName = "watchapi" ; // 模块名
    private static String parent = "com.thc" ; // 父包名

    // 策略配置
    private static String TablePrefix = "" ; // 表前缀

    // entity 基础类公共字段
    // private static String[] entityColumns = { "id", "is_deleted", "gmt_create", "gmt_modified" };

    // 这里默认的基类中有范型，生成后要手动去掉
    // private static String baseEntityName = "com.thc.eduservice.entity.base.BaseEntity" ; // 实体类基类类名


    @Test
    public void Generator() {

        /**
         * 全局配置
         */
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setActiveRecord(true); // 开启 ActiveRecord 模式
        globalConfig.setAuthor("thc"); // 开发人员
        globalConfig.setOutputDir(outputDir + "/src/main/java"); // 生成文件的输出目录
        globalConfig.setFileOverride(true); // 是否覆盖已有文件

        globalConfig.setServiceName("%sService"); // 设置Service名字
        globalConfig.setDateType(DateType.ONLY_DATE); // 设置只剩成Date类型

        globalConfig.setSwagger2(true); // 设置swagger

        /**
         * 数据库配置
         */
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL); // 数据库类型
        dataSourceConfig.setDriverName(driverName); // 驱动名称
        dataSourceConfig.setUrl(url); // 驱动连接的URL
        dataSourceConfig.setUsername(username); // 数据库连接用户名
        dataSourceConfig.setPassword(password); // 数据库连接密码

        /**
         * 策略配置项
         */
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setTablePrefix(new String[] { TablePrefix }); // 表前缀
        strategyConfig.setNaming(NamingStrategy.underline_to_camel); // 数据库表字段映射到实体的命名策略
        strategyConfig.setInclude(tables); // 需要包含的表名
        strategyConfig.setEntityLombokModel(true); // 增加lombok注解
        strategyConfig.setEntityBooleanColumnRemoveIsPrefix(true); // is_deleted 实体类中is要去掉（ali代码规范）
        strategyConfig.setRestControllerStyle(true); // restful api 风格控制器
        strategyConfig.setControllerMappingHyphenStyle(true); // url中驼峰转连字符
        // strategyConfig.setSuperEntityColumns(entityColumns[0], entityColumns[1], entityColumns[2], entityColumns[3]); // 设置父类公共字段
        // strategyConfig.setSuperEntityClass(baseEntityName);

        /**
         * 跟包相关的配置项
         */
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName(modulesName); // 模块名
        packageConfig.setParent(parent); // 父包名
        packageConfig.setEntity("entity"); // Entity包名
        packageConfig.setMapper("mapper");
        packageConfig.setController("controller");

        /**
         * 集成注入配置
         */
        AutoGenerator gen = new AutoGenerator();
        gen.setGlobalConfig(globalConfig);
        gen.setDataSource(dataSourceConfig);
        gen.setStrategy(strategyConfig);
        gen.setPackageInfo(packageConfig);
        gen.execute();
    }
}