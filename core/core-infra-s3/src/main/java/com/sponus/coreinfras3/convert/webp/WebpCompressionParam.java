package com.sponus.coreinfras3.convert.webp;

public class WebpCompressionParam {
	Integer q = -1; // RGB 채널 압축 여부 ( 0 ~ 6 )
	Integer m = -1; // 압축 방식 지정 ( 0 ~ 6 ), 높을 수록 고효율 압축
	Boolean lossless = false; // 손실 or 무손실 압축 여부
}
