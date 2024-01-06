package com.sponus.sponusbe.auth.jwt.dto;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.util.StreamUtils;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class CachedHttpServletRequest extends HttpServletRequestWrapper {

	private final String cachedRequestBody;

	/**
	 * Constructs a request object wrapping the given request.
	 *
	 * @param request The request to wrap
	 * @throws IllegalArgumentException if the request is null
	 */
	public CachedHttpServletRequest(HttpServletRequest request) throws IOException {
		super(request);
		ServletInputStream inputStream = request.getInputStream();
		byte[] rawData = StreamUtils.copyToByteArray(inputStream);
		cachedRequestBody = new String(rawData);
	}

	@Override
	public ServletInputStream getInputStream() {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
			this.cachedRequestBody.getBytes(StandardCharsets.UTF_8));
		return new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return inputStream.available() == 0;
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setReadListener(ReadListener listener) {
				throw new UnsupportedOperationException();
			}

			@Override
			public int read() {
				return inputStream.read();
			}
		};
	}

	@Override
	public BufferedReader getReader() {
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}
}
