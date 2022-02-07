package com.mg.filesearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties
public class FileSearchConfig {

	private String version;
	private S3Config s3Config;
	private AwsConfig awsConfig;
}
