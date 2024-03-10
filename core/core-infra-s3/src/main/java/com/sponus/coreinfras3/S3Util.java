package com.sponus.coreinfras3;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Util {

	private final AmazonS3 amazonS3;

	private final S3Config s3Config;

	public String uploadFile(MultipartFile file) {
		String filePath =
			UUID.randomUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		log.info("[*] File Size : {}", file.getSize());
		try {
			amazonS3.putObject(
				new PutObjectRequest(s3Config.getBucket(), s3Config.getFolder() + filePath, file.getInputStream(),
					metadata));
		} catch (IOException e) {
			log.error("error at AmazonS3Manager uploadFile : {}", (Object)e.getStackTrace());
		}

		return amazonS3.getUrl(s3Config.getBucket(), s3Config.getFolder() + filePath).toString();
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

}
