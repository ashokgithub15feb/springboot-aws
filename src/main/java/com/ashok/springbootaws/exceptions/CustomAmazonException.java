package com.ashok.springbootaws.exceptions;

public class CustomAmazonException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6554246757776577577L;

	public CustomAmazonException(String message) {
		super(message);
	}
	
	public static String NotFoundEception(String bucketName) {
		return "Bucket with "+bucketName+" not found!!";
	}
	
	public static String UserNotFoundEception(String id) {
		return "User with "+id+" not found!!";
	}
	
	public static String todoAlreadyExists() {
		return "Bucket with given name already exists!!";
	}
}
