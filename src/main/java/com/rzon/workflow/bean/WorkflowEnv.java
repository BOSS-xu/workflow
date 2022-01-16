package com.rzon.workflow.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "workflow")
public class WorkflowEnv {

    private DataSource dataSource = new DataSource();

    @Getter
    @Setter
    public static class DataSource {

        private String type;

        private String url;

        private String username;

        private String password;

        private Integer minimumIdle;

        private Integer maximumPoolSize;

    }
}
