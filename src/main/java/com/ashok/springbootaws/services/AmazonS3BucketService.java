package com.ashok.springbootaws.services;

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
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.ashok.springbootaws.exceptions.CustomAmazonException;

@Service
public class AmazonS3BucketService {

	private Logger logger = LogManager.getLogger(AmazonS3BucketService.class);

	@Autowired
	private FileStore fileStore;

	public void downloadFile(String fileName, AmazonS3 amazonS3, String bucketName) {
		try {
			logger.info("File to be fetched from AWS S3: ()", fileName);

			S3Object s3Object = amazonS3.getObject(bucketName, fileName);

			S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();

			String content = IOUtils.toString(s3ObjectInputStream);

			logger.info("AWS S3 Content: {}", content);
		} catch (IOException e) {
			logger.error("Error in reading file content: {}", e.getMessage());
		} catch (AmazonS3Exception e) {
			logger.error("AmazonS3Exception error occured: {}", e.getMessage());
		}
	}

	public String createAWSS3Bucket(String bucketName) throws CustomAmazonException {
		logger.info("AWS S3 create bucket");
		Bucket bucket = fileStore.createBucket(bucketName);
		if(bucket != null && bucket.getName() != null) {
			return bucket.getName();
		} else {
			throw new CustomAmazonException(CustomAmazonException.NotFoundEception(bucketName));
		}
	}

	public String uploadFile(MultipartFile file, String bucketName) throws IllegalAccessException, CustomAmazonException {
		PutObjectResult uploadFileToAWSS3Bucket = null;
		if (file.isEmpty()) {
			throw new IllegalAccessException("can not upload empty file");
		}

		try {
			uploadFileToAWSS3Bucket = fileStore.uploadFileToAWSS3Bucket(file, bucketName);

		} catch (Exception e) {
			logger.error("Failed to upload file: {}", e.getMessage());
		}
		
		if(uploadFileToAWSS3Bucket != null) {
			return "File Uploaded Successfully";
		} else {
			throw new CustomAmazonException(CustomAmazonException.todoAlreadyExists());
		}
	}

	public String deleteBucket(String bucketName) {
		fileStore.deleteBucket(bucketName);
		
		return "AWS S3 Bucket deleted successfully";
	}

	public String deleteFile(String bucketName, String fileName) {
		fileStore.deleteBucketFile(bucketName, fileName);
		return "File deleted successfully";
	}
}
