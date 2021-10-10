package org.oaiqiy.miraifileupload.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;


public interface StorageService {


	void store(MultipartFile file) throws Exception;

	List<URLandName> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);


	void delete(String filename);

}
