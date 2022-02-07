package com.mg.filesearch.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class S3Config {
	private String bucketName;
	private String filePath;
	private String fileName;
}
