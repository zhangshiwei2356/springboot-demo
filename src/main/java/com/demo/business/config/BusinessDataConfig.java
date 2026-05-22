package com.demo.business.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({BusinessDataProperties.class, ArchiveProperties.class})
public class BusinessDataConfig {
}
