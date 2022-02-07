package com.mg.filesearch.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CSVInput;
import com.amazonaws.services.s3.model.CSVOutput;
import com.amazonaws.services.s3.model.CompressionType;
import com.amazonaws.services.s3.model.ExpressionType;
import com.amazonaws.services.s3.model.InputSerialization;
import com.amazonaws.services.s3.model.OutputSerialization;
import com.amazonaws.services.s3.model.SelectObjectContentRequest;
import com.amazonaws.services.s3.model.SelectObjectContentResult;
import com.mg.filesearch.config.FileSearchConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class S3Util {

	public AmazonS3 getS3Client(FileSearchConfig config) {
		return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(
				new BasicAWSCredentials(config.getAwsConfig().getAccessId(), config.getAwsConfig().getSecretKey())))
				.withRegion(Regions.US_EAST_1).build();
	}

	public void printData(FileSearchConfig config, String sql) {
		AmazonS3 s3Client = getS3Client(config);
		SelectObjectContentRequest request = generateBaseCsvRequest(config, sql);
		SelectObjectContentResult result = s3Client.selectObjectContent(request);
		InputStream resultInputStream = result.getPayload().getRecordsInputStream();
		log.info("Data Fetch Complete");
		InputStreamReader isReader = new InputStreamReader(resultInputStream);
		Scanner inputStream = new Scanner(isReader);
		while (inputStream.hasNext()) {
			String data = inputStream.next();
			log.info(data);
			// String[] dataArr = data.split(",");
			// log.info(Arrays.toString(dataArr));
		}
		inputStream.close();
	}

	public SelectObjectContentRequest generateBaseCsvRequest(FileSearchConfig config, String sql) {
		log.info("Data Fetch Started");
		SelectObjectContentRequest request = new SelectObjectContentRequest();
		request.setBucketName(config.getS3Config().getBucketName());
		request.setKey(config.getS3Config().getFilePath() + config.getS3Config().getFileName());
		request.setExpression(sql);
		request.setExpressionType(ExpressionType.SQL);

		InputSerialization inputSerialization = new InputSerialization();
		inputSerialization.setCsv(new CSVInput()
				.withFieldDelimiter(",")/* .withRecordDelimiter('\n') */);
		inputSerialization.setCompressionType(CompressionType.NONE);
		request.setInputSerialization(inputSerialization);

		OutputSerialization outputSerialization = new OutputSerialization();
		outputSerialization.setCsv(new CSVOutput());
		request.setOutputSerialization(outputSerialization);
		return request;
	}
}
