package com.weavebytes.httpfrompost;

/*
interface that observe file upload
 */
public interface FileUploadObserver {
	
	public void uploadStatusUpdate(int error, long totalBytesRead, long totalBytes);

}//FileUploadObserver
