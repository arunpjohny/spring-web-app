package com.arunpjohny.web.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ApplicationUtils {

	@Value("${location.temp}")
	private Resource tempLocation;

	public File getTempDir(String type) throws IOException {
		File temp = tempLocation.getFile();
		File dir = new File(temp, type);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	public File getTempFile(String type) throws IOException {
		File dir = getTempDir(type);
		String name;
		synchronized (getClass()) {
			name = UUID.randomUUID().toString();
		}
		return new File(dir, name);
	}
}
