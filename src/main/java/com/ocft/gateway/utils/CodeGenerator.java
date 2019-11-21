package com.ocft.gateway.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Scanner;

/**
 * @author lijiaxing
 * @Title: CodeGenerator
 * @ProjectName
 * @date 2019/7/2下午3:59
 * @Description: mybatis-plus 生成代码工具类
 */
public class CodeGenerator {
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        String projectPath = System.getProperty("user.dir");
        GlobalConfig gc = new GlobalConfig()
                .setOutputDir(projectPath + "/src/main/java")
                .setAuthor("lijiaxing")
                .setOpen(false)
                .setBaseResultMap(true)
                .setBaseColumnList(true);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig()
                .setDbType(DbType.MYSQL)
                .setTypeConvert(new MySqlTypeConvert())
                .setUrl("jdbc:mysql://106.14.226.254:3306/gateway")
                .setDriverName("com.mysql.jdbc.Driver")
                .setUsername("root")
                .setPassword("root123");

        // 包配置
        PackageConfig pc = new PackageConfig()
                .setModuleName(scanner("模块名"))
                .setParent("com.ocft.gateway");

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setSuperEntityColumns("id")
                .setInclude(scanner("表名，多个英文逗号分割").split(","))
                .setControllerMappingHyphenStyle(true)
                .setTablePrefix(pc.getModuleName() + "_");

        //执行配置信息生成文件
        mpg.setGlobalConfig(gc)
                .setDataSource(dsc)
                .setPackageInfo(pc)
                .setStrategy(strategy)
                .execute();
    }
}
