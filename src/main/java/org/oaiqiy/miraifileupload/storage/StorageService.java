package org.oaiqiy.miraifileupload.storage;

import org.springframework.web.multipart.MultipartFile;


public interface StorageService {

	/**
	 * 存文件
	 * @param file
	 * @throws Exception 空文件抛出异常
	 */
	void store(MultipartFile file) throws Exception;

	/**
	 * 清除缓存的数据，加载全部文件信息
	 */
	void loadAll();

	/**
	 * 在删除一个文件后重新加载文件信息
	 */
	void reloadAll();

	/**
	 * 删除一个文件
	 * @param fileId
	 */
	void delete(String fileId);

}
