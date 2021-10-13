package org.oaiqiy.miraifileupload.storage;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class StorageData {
    /**
     * 存文件信息的链表
     */
    private List<RemoteFileData> data;

}
