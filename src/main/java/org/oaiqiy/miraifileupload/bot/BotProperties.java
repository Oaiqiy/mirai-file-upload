package org.oaiqiy.miraifileupload.bot;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class BotProperties {
    private Long qqNum;
    private String password;

}
