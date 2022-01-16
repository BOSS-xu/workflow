package com.rzon.workflow.config;

import com.rzon.workflow.bean.WorkflowEnv;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    
    private static final String ORLACE_DCN = "oracle.jdbc.driver.OracleDriver";
    
    private static final String SQLSERVER_DCN = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    private static final String ORACLE_TEST_QUERY = "select 1 from dual";
    
    private static final String SQLSERVER_TEST_QUERY = "select 1";
    
    @Resource
    private WorkflowEnv yml;

    @Bean
    public DataSource dataSource() {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(yml.getDataSource().getUrl());
        dataSource.setUsername(yml.getDataSource().getUsername());
        dataSource.setPassword(yml.getDataSource().getPassword());
        dataSource.setDriverClassName(getDriverClassName());
        dataSource.setAutoCommit(false);
        dataSource.setPoolName("hikari");
        dataSource.setConnectionTestQuery(getTestQuery());
        dataSource.setMaximumPoolSize(yml.getDataSource().getMaximumPoolSize());
        dataSource.setMinimumIdle(yml.getDataSource().getMinimumIdle());
        return dataSource;
    }
    
    private String getDriverClassName() {
        return yml.getDataSource().getType().equals("oracle")
                ? ORLACE_DCN
                : SQLSERVER_DCN;
    }
    
    private String getTestQuery() {
        return yml.getDataSource().getType().equals("oracle")
                ? ORACLE_TEST_QUERY
                : SQLSERVER_TEST_QUERY;
    }
}
