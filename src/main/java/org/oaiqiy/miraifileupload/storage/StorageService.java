package org.oaiqiy.miraifileupload.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	void init();

	void store(MultipartFile file) throws Exception;

	Stream<String> loadAll();

	Stream<String> loadAllNames();

	Stream<URLandName> loadAllURL();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAll();

	void delete(String filename);

}
