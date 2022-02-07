package com.mg.filesearch.util;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ErrorUtil {

	public void printErrorLogs(Exception e) {
		StackTraceElement[] stack = e.getStackTrace();
		for (StackTraceElement stackTraceElement : stack) {
			log.error("{}", stackTraceElement);
		}
	}
}
