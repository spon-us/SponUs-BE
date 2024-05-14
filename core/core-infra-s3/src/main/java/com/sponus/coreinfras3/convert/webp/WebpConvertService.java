package com.sponus.coreinfras3.convert.webp;

import java.io.File;

import org.springframework.stereotype.Service;

import com.sponus.coreinfras3.convert.ImageFormat;
import com.sponus.coreinfras3.util.FileUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WebpConvertService {

	private final WebpConvertStrategyLocator strategyLocator;

	// @Async(AsyncConfig.WEBP_CONVERSION) // 비동기 처리. 이 부분은 AsyncConfig에 정의된 상수를 사용
	public File convertToWebP(File file) {
		String fileName = FileUtils.removeExtension(file.getName());
		String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);

		ImageFormat format = ImageFormat.of(extension);
		if (format == null) {
			throw new IllegalArgumentException("Unsupported image format: " + extension);
		}

		WebpConvertStrategy convertStrategy = strategyLocator.getStrategy(format);
		return convertStrategy.convert(fileName, file, new WebpCompressionParam());
	}
}
