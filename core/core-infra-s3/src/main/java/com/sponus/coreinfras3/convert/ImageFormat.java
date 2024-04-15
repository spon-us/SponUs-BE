package com.sponus.coreinfras3.convert;

public enum ImageFormat {
	JPEG, JPG, PNG, GIF, WEBP;

	public static ImageFormat of(String format) {
		try {
			return ImageFormat.valueOf(format.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Unsupported image format: " + format);
		}
	}
}
