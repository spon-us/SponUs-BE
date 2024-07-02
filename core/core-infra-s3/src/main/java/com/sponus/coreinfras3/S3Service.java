package com.sponus.coreinfras3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
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

	public String generatePreSignUrl(String fileName) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, 10); //validfy of 10 minutes
		return amazonS3.generatePresignedUrl(s3Config.getBucket(), fileName, calendar.getTime(), HttpMethod.PUT)
			.toString();
	}

	public String uploadFile(File file) {
		String fileName = s3Config.getFolder() + getFileNamePrefix() + file.getName();
		String preSignedUrl = generatePreSignUrl(fileName);

		// log.info("preSignedUrl : {}", preSignedUrl);
		try {
			uploadFileToS3UsingPreSignedUrl(preSignedUrl, file);
		} catch (IOException e) {
			throw new IllegalArgumentException("Failed to upload file", e);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Failed to parsing uri");
		}

		// 로컬 파일 삭제
		try {
			Files.delete(file.toPath());
		} catch (IOException e) {
			throw new IllegalArgumentException("Failed to delete file", e);
		}

		return amazonS3.getUrl(s3Config.getBucket(), fileName).toString();
	}

	private void uploadFileToS3UsingPreSignedUrl(String preSignedUrl, File file) throws URISyntaxException,
		IOException {

		URI uri = new URI(preSignedUrl);
		WebClient webClient = WebClient.create();

		byte[] fileBytes = readFileToBytes(file);

		webClient.put()
			.uri(uri)
			.contentType(MediaType.APPLICATION_OCTET_STREAM)
			.bodyValue(fileBytes)
			.retrieve().toBodilessEntity().block();
	}

	private byte[] readFileToBytes(File file) throws IOException {
		try (FileInputStream fileInputStream = new FileInputStream(file);
			 FileChannel fileChannel = fileInputStream.getChannel()) {
			ByteBuffer byteBuffer = ByteBuffer.allocate((int)fileChannel.size());
			fileChannel.read(byteBuffer);
			return byteBuffer.array();
		}
	}
}
