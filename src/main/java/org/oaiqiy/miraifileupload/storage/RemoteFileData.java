package org.oaiqiy.miraifileupload.storage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoteFileData {
    private String url;
    private String name;
}
