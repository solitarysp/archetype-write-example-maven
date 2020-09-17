package com.lethanh98.archdemo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Sử dụng để lấy các config chung cho dự án.
 * Start của config chung này bắt đầu bằng app
 */

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class AppConfigurationProperties {
    private int repositoryQueryLimitWarningMs;
    private int asyncExecutorCorePoolSize = 2;
    private int asyncExecutorMaxPoolSize = 4;
    private String defaultLanguage = "vi";
    private String asyncExecutorThreadNamePrefix = "Async-";
}
