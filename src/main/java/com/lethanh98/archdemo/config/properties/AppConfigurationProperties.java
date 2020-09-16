package com.lethanh98.archdemo.config.properties;

import com.lethanh98.archdemo.config.constant.ConstantPropertiesConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${" + ConstantPropertiesConfig.APP_LOG_REQUEST_HTTP + ":#{false}}")
    private boolean logRequestHttp;
    private int repositoryQueryLimitWarningMs;
    private int asyncExecutorCorePoolSize = 2;
    private int asyncExecutorMaxPoolSize = 4;
    private String defaultLanguage = "vi";
    private String asyncExecutorThreadNamePrefix = "Async-";
}
