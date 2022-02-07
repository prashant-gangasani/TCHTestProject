package com.mg.filesearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.mg.filesearch.service.FileService;
import com.mg.filesearch.util.ErrorUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@ComponentScan("com.mg.filesearch.*")
public class FilesearchApplication {

	public static void main(String[] args) {
		log.info("Starting application V1.0.1");
		SpringApplication.run(FilesearchApplication.class, args);
	}

	@Bean(initMethod = "runAfterObjectCreated")
	public BeanInitMethodImpl getInitBean() {
		return new BeanInitMethodImpl();
	}
}

@Slf4j
class BeanInitMethodImpl {

	@Autowired
	FileService service;

	@Autowired
	ErrorUtil errorUtil;

	public void runAfterObjectCreated() {

		try {
			log.info("runAfterObjectCreated method");
			service.searchFile();
		} catch (Exception e) {
			errorUtil.printErrorLogs(e);
		}

	}
}
