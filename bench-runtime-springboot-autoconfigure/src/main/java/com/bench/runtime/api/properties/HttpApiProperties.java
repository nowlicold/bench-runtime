package com.bench.runtime.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author by cold
 * @date 2020/06/26
 */
@Data
@ConfigurationProperties(prefix = "http.config")
public class HttpApiProperties {

    private Boolean logEnable;

}
