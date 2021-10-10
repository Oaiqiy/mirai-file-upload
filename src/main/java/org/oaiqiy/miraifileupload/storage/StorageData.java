package org.oaiqiy.miraifileupload.storage;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class StorageData {
    private List<RemoteFileData> data;

}
