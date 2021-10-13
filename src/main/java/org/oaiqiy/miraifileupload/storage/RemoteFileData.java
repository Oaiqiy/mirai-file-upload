package org.oaiqiy.miraifileupload.storage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoteFileData {
    /**
     * 文件的id
     */
    private String id;

    /**
     * 文件下载链接
     */
    private String url;

    /**
     * 文件名
     */
    private String name;

}
