package com.sponus.coreinfras3.convert.webp;

import java.io.File;

import com.sponus.coreinfras3.convert.ImageFormat;

public interface WebpConvertStrategy {
	boolean identify(ImageFormat imageFormat);

	File convert(String fileName, File file, WebpCompressionParam param);
}
