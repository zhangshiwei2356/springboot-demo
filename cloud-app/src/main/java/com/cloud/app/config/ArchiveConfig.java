package com.cloud.app.config;

import com.cloud.app.archive.ArchiveProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ArchiveProperties.class)
public class ArchiveConfig {
}
