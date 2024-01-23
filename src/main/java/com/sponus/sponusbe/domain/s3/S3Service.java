package com.sponus.sponusbe.domain.s3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
public class S3Service {

	private final AmazonS3 amazonS3;

	private final S3Config s3Config;

	public String uploadFile(MultipartFile file) {
		String filePath =
			UUID.randomUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		try {
			amazonS3.putObject(
				new PutObjectRequest(s3Config.getBucket(), s3Config.getFolder() + filePath, file.getInputStream(),
					metadata));
		} catch (IOException e) {
			log.error("error at AmazonS3Manager uploadFile : {}", (Object)e.getStackTrace());
		}

		return amazonS3.getUrl(s3Config.getBucket(), filePath).toString();
	}

	public String deleteImage(String image) {
		amazonS3.deleteObject(s3Config.getBucket(), s3Config.getFolder() + image);
		return "삭제 성공";
	}

	public List<String> uploadFiles(List<MultipartFile> files) {
		List<String> fileUrls = new ArrayList<>();

		files.stream().map(file -> {
			fileUrls.add(uploadFile(file));
			return fileUrls;
		}).collect(Collectors.toList());
		return fileUrls;
	}

}
