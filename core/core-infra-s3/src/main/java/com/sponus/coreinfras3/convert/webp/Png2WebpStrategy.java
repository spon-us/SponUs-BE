package com.sponus.coreinfras3.convert.webp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Service;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.webp.WebpWriter;
import com.sponus.coreinfras3.convert.ImageFormat;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Png2WebpStrategy implements WebpConvertStrategy {

	@Override
	public boolean identify(ImageFormat imageFormat) {
		return ImageFormat.PNG.equals(imageFormat);
	}

	@Override
	public File convert(String fileName, File file, WebpCompressionParam param) {
		try {
			File webpFile = ImmutableImage.loader()
				.fromFile(file)
				.output(WebpWriter.DEFAULT, new File(fileName + ".webp"));
			Files.delete(file.toPath());
			return webpFile;
		} catch (IOException e) {
			log.error("PNG -> WebP conversion failed, cancelling conversion. {}", e.getMessage());
			return file;
		}
	}
}
