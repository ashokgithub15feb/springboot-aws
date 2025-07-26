package com.ashok.springbootaws.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.logs.AWSLogs;
import com.amazonaws.services.logs.model.DescribeLogStreamsRequest;
import com.amazonaws.services.logs.model.InputLogEvent;
import com.amazonaws.services.logs.model.LogStream;
import com.amazonaws.services.logs.model.PutLogEventsRequest;
import com.amazonaws.services.logs.model.PutLogEventsResult;

@Service
public class AmazonCloudWatchService {

	private Logger logger = LogManager.getLogger(AmazonCloudWatchService.class);

	@Autowired
	private AWSLogs cloudWatchAWSLogs;

	private String logGroupName = "my-user-crud-app";
	private String logStreamName = "my-user-app-log-stream";

	public void logMessageToCloudWatch(String message) {
		logger.info("Inside log Message to aws cloud watch method");

		List<InputLogEvent> inputLogEvents = new ArrayList<>();
		InputLogEvent inputLogEvent = new InputLogEvent();
		Calendar calendar = Calendar.getInstance();

		inputLogEvent.setTimestamp(calendar.getTimeInMillis());
		inputLogEvent.setMessage(message);
		inputLogEvents.add(inputLogEvent);

		String token = null;
		DescribeLogStreamsRequest describeLogStreamsRequest = new DescribeLogStreamsRequest(logGroupName);

		describeLogStreamsRequest.withLimit(5);

		List<LogStream> logStreams = new ArrayList<>();
		logStreams = cloudWatchAWSLogs.describeLogStreams(describeLogStreamsRequest).getLogStreams();

		if (logStreams != null) {
			for (LogStream logStream : logStreams) {
				if (logStream.getLogStreamName().equals(logStreamName)) {
					token = logStream.getUploadSequenceToken();
				}
			}
		}

		PutLogEventsRequest putLogEventsRequest = new PutLogEventsRequest();
		PutLogEventsResult putLogEventsResult = new PutLogEventsResult();

		if (token != null) {
			appendLogsToAWSCloudWatchWithToken(putLogEventsRequest, putLogEventsResult, token, inputLogEvents);
		} else {
			firstHitToAWSCloudWatch(putLogEventsRequest, putLogEventsResult, inputLogEvents);
		}
		
		logger.info("Logged Message to aws cloud watch successfully");
	}

	private void firstHitToAWSCloudWatch(PutLogEventsRequest putLogEventsRequest, PutLogEventsResult putLogEventsResult,
			List<InputLogEvent> inputLogEvents) {
		putLogEventsRequest.setLogGroupName(logGroupName);
		putLogEventsRequest.setLogStreamName(logStreamName);
		putLogEventsRequest.setLogEvents(inputLogEvents);

		putLogEventsResult = cloudWatchAWSLogs.putLogEvents(putLogEventsRequest);

	}

	private void appendLogsToAWSCloudWatchWithToken(PutLogEventsRequest putLogEventsRequest,
			PutLogEventsResult putLogEventsResult, String token, List<InputLogEvent> inputLogEvents) {
		putLogEventsRequest.setLogGroupName(logGroupName);
		putLogEventsRequest.setLogStreamName(logStreamName);
		putLogEventsRequest.setLogEvents(inputLogEvents);
		putLogEventsRequest.setSequenceToken(token);

		putLogEventsResult = cloudWatchAWSLogs.putLogEvents(putLogEventsRequest);
	}

}
