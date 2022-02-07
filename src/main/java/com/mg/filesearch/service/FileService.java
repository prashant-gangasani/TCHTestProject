package com.mg.filesearch.service;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mg.filesearch.config.FileSearchConfig;
import com.mg.filesearch.util.S3Util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {

	@Autowired
	FileSearchConfig config;

	@Autowired
	S3Util s3Util;

	public void searchFile() {
		log.info("searchFile method start");
		log.info("{}", config);
		Scanner sc = new Scanner(System.in);
		log.info("Enter the column numbers for searching like 1,2,4 without any whitespace");
		log.info("0 - All Data");
		log.info("1 - Bank Name");
		log.info("2 - Type");
		log.info("3 - City");
		log.info("4 - State");
		log.info("5 - Zip Code");
		String columnStr = sc.nextLine();
		String[] columns = columnStr.split(",");
		StringBuilder sql = new StringBuilder("select * from S3Object");
		if (!columns[0].equals("0")) {
			sql.append(" s where ");
			int counter = 0;
			int length = columns.length;
			for (String columnNum : columns) {
				counter++;
				if (columnNum.equals("1")) {
					log.info("Enter Bank Name");
					String temp = sc.nextLine();
					sql.append("s._1 like'%").append(temp).append("%'");
				} else if (columnNum.equals("2")) {
					log.info("Enter Type");
					String temp = sc.nextLine();
					sql.append("s._2 like'%").append(temp).append("%'");
				} else if (columnNum.equals("3")) {
					log.info("Enter City");
					String temp = sc.nextLine();
					sql.append("s._3 like'%").append(temp).append("%'");
				} else if (columnNum.equals("4")) {
					log.info("Enter State");
					String temp = sc.nextLine();
					sql.append("s._4 like'%").append(temp).append("%'");
				} else if (columnNum.equals("5")) {
					log.info("Enter Zip Code");
					String temp = sc.nextLine();
					sql.append("s._5 like'%").append(temp).append("%'");
				}
				if (counter < length) {
					sql.append(" and ");
				}
			}
		}
		sc.close();
		log.info("{}", sql);
		s3Util.printData(config, sql.toString());
	}
}
