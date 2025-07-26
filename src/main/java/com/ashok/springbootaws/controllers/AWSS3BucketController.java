package com.ashok.springbootaws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ashok.springbootaws.exceptions.CustomAmazonException;
import com.ashok.springbootaws.services.AmazonS3BucketService;

@RestController
@RequestMapping("/aws/v1/s3/bucket")
public class AWSS3BucketController {

	@Autowired
	private AmazonS3BucketService amazonS3BucketService;

	@GetMapping("/add/{bucketName}")
	public ResponseEntity<String> createBucket(@PathVariable String bucketName) throws CustomAmazonException {
		
		String createAWSS3Bucket = amazonS3BucketService.createAWSS3Bucket(bucketName);
		
		return new ResponseEntity<String>(createAWSS3Bucket, HttpStatus.OK);
	}

	@PostMapping(path = "/upload/file/{bucketName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> uploadFile(@RequestParam MultipartFile fileName, @PathVariable String bucketName)
			throws IllegalAccessException, CustomAmazonException {
		String uploadFile = amazonS3BucketService.uploadFile(fileName, bucketName);
		return new ResponseEntity<String>(uploadFile, HttpStatus.OK);
	}

	@DeleteMapping("/delete/file/{bucketName}/{fileName}")
	public ResponseEntity<String> deleteFile(@PathVariable String bucketName, @PathVariable String fileName) {
		return new ResponseEntity<String>(amazonS3BucketService.deleteFile(bucketName, fileName), HttpStatus.OK);
	}

	@DeleteMapping("/delete/file/{bucketName}")
	public ResponseEntity<String> deleteBucket(@PathVariable String bucketName) {
		return new ResponseEntity<String>(amazonS3BucketService.deleteBucket(bucketName), HttpStatus.OK);
	}
}
