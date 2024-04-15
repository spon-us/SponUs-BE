package com.sponus.coreinfras3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sponus.coreinfras3.convert.webp.WebpConvertService;
import com.sponus.coreinfras3.util.FileUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

	private final AmazonS3 amazonS3;

	private final S3Config s3Config;

	private final WebpConvertService webpConvertService;

	public String uploadImage(MultipartFile multipartFile) {
		File file = FileUtils.convertToFile(multipartFile);
		return uploadImage(file);
	}

	public String uploadImage(File file) {
		File webpFile = webpConvertService.convertToWebP(file);
		return uploadFile(webpFile);
	}

	public String uploadFile(MultipartFile multipartFile) {
		File file = FileUtils.convertToFile(multipartFile);
		return uploadFile(file);
	}

	public String uploadFile(File file) {
		String fileName = getFileNamePrefix() + file.getName();

		amazonS3.putObject(new PutObjectRequest(s3Config.getBucket(), s3Config.getFolder() + fileName, file));
		try {
			Files.delete(file.toPath());
		} catch (IOException e) {
			throw new IllegalArgumentException("Failed to delete file", e);
		}

		return amazonS3.getUrl(s3Config.getBucket(), s3Config.getFolder() + fileName).toString();
	}

	public String deleteFile(String image) {
		try {
			amazonS3.deleteObject(s3Config.getBucket(), s3Config.getFolder() + image);
		} catch (Exception e) {
			log.error("error at AmazonS3Manager deleteFile : {}", (Object)e.getStackTrace());
		}
		return "삭제 성공";
	}

	public List<String> uploadFiles(List<MultipartFile> files) {
		return files.stream()
			.map(this::uploadFile)
			.toList();
	}

	private String getFileNamePrefix() {
		return UUID.randomUUID().toString().substring(0, 5) + "-";
	}
}
