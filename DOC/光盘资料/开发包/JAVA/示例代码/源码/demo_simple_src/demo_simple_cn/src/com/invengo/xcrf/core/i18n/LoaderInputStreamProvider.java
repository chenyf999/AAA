package com.invengo.xcrf.core.i18n;

import java.io.InputStream;

public interface LoaderInputStreamProvider {
	public InputStream getInputStreamForFile(String filename);
}
