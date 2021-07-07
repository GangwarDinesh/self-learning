package com.base.app.validate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.base.app.model.FileUploadModel;

@Component
public class FileValidator implements Validator {
	
	public static final String PNG_MIME_TYPE = "image/png";
	public static final long TEN_MB_IN_BYTES = 10485760;
	
	@Value("${upload.file.required}")
	private String fileRequiredMsg;
	
	@Value("${upload.invalid.file.type}")
	private String fileInvalidMsg;
	
	@Value("${upload.exceeded.file.size}")
	private String fileSizeMsg;

	@Override
	public boolean supports(Class<?> clazz) {
		return FileUploadModel.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FileUploadModel fileUploadModel = (FileUploadModel)target;
		MultipartFile file = fileUploadModel.getFile();
		if(file.isEmpty()) {
			errors.rejectValue("file", fileRequiredMsg);
		}else if(!PNG_MIME_TYPE.equalsIgnoreCase(file.getContentType())) {
			errors.rejectValue("file", fileInvalidMsg);
		}else if(file.getSize()>TEN_MB_IN_BYTES) {
			errors.rejectValue("file", fileSizeMsg);
		}
	}

}
