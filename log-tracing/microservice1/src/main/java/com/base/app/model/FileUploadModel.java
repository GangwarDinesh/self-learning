package com.base.app.model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String fileName;
	
	private String fileType;
	
	private transient MultipartFile file;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "FileUploadModel [fileName=" + fileName + ", fileType=" + fileType + "]";
	}
	
	

}
