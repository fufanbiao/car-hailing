package com.car.hailing.generate;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;

/**
 * @Author: fanbiao.fu
 * @Description: mybatis代码生成器
 * @Date: 2024/6/12
 */
public class MyBatisPlusGenerator {

  public static void main(String[] args) {
    Generate();
  }

  public static void Generate() {
    FastAutoGenerator.create("jdbc:mysql://w-bzrds-692a4582a25b4933visz.service.testdb:3306/db_scanner_application_sit", "scanner_application_sit", "izW5(WVM(P3svnB06")
        //全局配置
        .globalConfig(builder -> {
          builder.author("fanbiao.fu") // 设置作者
              .enableSwagger() // 开启 swagger 模式
             // .fileOverride() // 覆盖已生成文件
              .dateType(DateType.TIME_PACK)//时间策略
              .commentDate("yyyy-MM-dd")//注释日期
              //*****************************************
              .outputDir(System.getProperty("user.dir") + "/src/main/java"); // 指定输出目录

        })
        //包配置
        .packageConfig(builder -> {
          //*****************************************
          builder.parent("com.car.hailing") // 设置父包名
             // .moduleName("system") // 设置父包模块名
              .entity("entity")//entity包名
              .service("service")//service包名
              .serviceImpl("service.impl")//service.impl包名
              .mapper("mapper")//mapper包名
              .xml("mapper.xml")
              .controller("controller")//controller包名
              //.other("other")//其他的包名
              //.pathInfo(Collections.singletonMap(OutputFile.xml, "D://")); // 设置mapperXml生成路径
              .build();
        })
        //模板配置
        .templateConfig(builder -> {
          builder.disable(TemplateType.ENTITY)
              .entity("/templates/entity.java")
              .service("/templates/service.java")
              .serviceImpl("/templates/serviceImpl.java")
              .mapper("/templates/mapper.java")
              //.mapperXml("/templates/mapper.xml")
              .controller("/templates/controller.java")
              .build();
        })
        //策略配置
//                .strategyConfig(builder -> {
//                    builder.addInclude("t_simple") // 设置需要生成的表名
//                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
//                })
        //entity配置
        .strategyConfig(builder -> {
          builder.entityBuilder()
              .disableSerialVersionUID()//禁用生成 serialVersionUID
              .enableLombok()//开启lomnbok模型
              .enableRemoveIsPrefix()//开启 Boolean 类型字段移除 is 前缀
              .enableTableFieldAnnotation()
              .enableActiveRecord()//开启生成实体时生成字段注解
              .naming(NamingStrategy.underline_to_camel)//数据库表映射到实体的命名策略,默认下划线转驼峰命名:NamingStrategy.underline_to_camel
              .columnNaming(NamingStrategy.underline_to_camel)
              .addSuperEntityColumns("id", "created_by", "created_time", "updated_by",
                  "updated_time")
              .addIgnoreColumns("age")
              .addTableFills(new Column("create_time", FieldFill.INSERT))
              .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))//添加表字段填充
              .idType(IdType.AUTO)//全局逐渐类型
              .formatFileName("%sEntity")
              .build();
        })
        // Controller 策略配置
        .strategyConfig(builder -> {
          builder.controllerBuilder()
              .enableRestStyle()//开启生成@RestController 控制器
              .enableHyphenStyle()//开启驼峰命名
              .build();
        })
        // Service 策略配置
        .strategyConfig(builder -> {
          builder.serviceBuilder()
              .formatServiceFileName("%sService")//Service类名设置
              .formatServiceImplFileName("%sServiceImpl");//ServiceImpl类名设置

        })
        //mapper策略配置
        .strategyConfig(builder -> {
          builder.mapperBuilder()
              .formatMapperFileName("%sDao")//
              .formatXmlFileName("%sXml")
              .build();
        })
        .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
        .execute();
  }

}

