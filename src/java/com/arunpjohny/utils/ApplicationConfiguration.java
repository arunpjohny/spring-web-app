package com.arunpjohny.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.Resource;

public class ApplicationConfiguration {
	private static ApplicationConfiguration me;

	private Resource temp;

	public ApplicationConfiguration() {

		synchronized (getClass()) {
			if (ApplicationConfiguration.me != null) {
				throw new IllegalStateException("Another instance of "
						+ getClass() + " already exits.");
			}
		}

		ApplicationConfiguration.me = this;
	}

	public static ApplicationConfiguration getInstance() {
		return me;
	}

	public Resource getTemp() {
		return temp;
	}

	public void setTemp(Resource temp) {
		this.temp = temp;
	}

	public File getTempFile() throws IOException {
		return temp.getFile();
	}
}
