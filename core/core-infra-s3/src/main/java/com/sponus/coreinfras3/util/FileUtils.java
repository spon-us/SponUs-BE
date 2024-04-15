package com.sponus.coreinfras3.util;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class FileUtils {

	private static final String TEMP_DIR_PATH = "tmp/";

	public static String getExtension(String fileName) {
		if (fileName == null) {
			throw new IllegalArgumentException("fileName cannot be null");
		}
		int index = fileName.lastIndexOf(".");
		if (index == -1) {
			throw new IllegalArgumentException("fileName does not have an extension");
		}
		return fileName.substring(index + 1);
	}

	public static String removeExtension(String fileName) {
		if (fileName == null) {
			throw new IllegalArgumentException("fileName cannot be null");
		}
		int index = fileName.lastIndexOf(".");
		if (index == -1) {
			throw new IllegalArgumentException("fileName does not have an extension");
		}
		return fileName.substring(0, index);
	}

	public static File convertToFile(MultipartFile multipartFile) {
		String tempDir = System.getProperty("java.io.tmpdir");
		File file = new File(tempDir + multipartFile.getOriginalFilename());
		log.info("File path: {}", file.getAbsolutePath());
		try {
			multipartFile.transferTo(file);
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to convert multipart file to file", e);
		}
		return file;
	}
}
