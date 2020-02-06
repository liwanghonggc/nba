package com.lwh.elasticsearch.nba.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.lwh.elasticsearch.nba.dao"})
public class MyBatisConfig {
}
