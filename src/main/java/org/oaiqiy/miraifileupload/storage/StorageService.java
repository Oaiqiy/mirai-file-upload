package org.oaiqiy.miraifileupload.storage;

import org.springframework.web.multipart.MultipartFile;


public interface StorageService {


	void store(MultipartFile file) throws Exception;

	void loadAll();




	void delete(String filename);

}
