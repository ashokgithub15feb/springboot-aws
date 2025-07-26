package com.ashok.springbootaws.services;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectResult;

@Service
public class FileStore {

	private Logger logger = LogManager.getLogger(FileStore.class);
	
	@Autowired
	private AmazonS3 amazonS3;
	
	public Bucket createBucket(String bucketName) {
		logger.info("Inside method createBucket");
		Bucket createBucket = null;
		try {
			if(bucketAlreadyExists(bucketName)) {
				logger.info("AWS S3 Bucket is already exists");
			}
			
			createBucket = amazonS3.createBucket(bucketName);
		} catch (AmazonS3Exception e) {
			logger.error("Unable to create bucket: {}", e.getMessage());
		}
		
		return createBucket;
	}

	private boolean bucketAlreadyExists(String bucketName) {
		logger.info("Inside method bucketAlreadyExists");
		return amazonS3.doesBucketExistV2(bucketName);
	}
	
	public PutObjectResult uploadFileToAWSS3Bucket(MultipartFile multiPart,String bucketName) {
		PutObjectResult putObject = null;
		try {
		logger.info("Inside method upload");
		
		String fileName = multiPart.getOriginalFilename();
		File file = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
		
			multiPart.transferTo(file);
			
			putObject = amazonS3.putObject(bucketName, file.getName(), file);
		} catch (IllegalStateException | IOException e) {
			logger.error("IllegalStateException occured: {}", e.getMessage());
		} catch(AmazonS3Exception e) {
			logger.error("Unable to upload file: {}", e.getMessage());
		}
		return putObject;
	}
	
	public void deleteBucket(String bucketName) {
		amazonS3.deleteBucket(bucketName);
	}
	
	public void deleteBucketFile(String bucketName, String fileName) {
		amazonS3.deleteObject(bucketName, fileName);
	}
}
