package org.oaiqiy.miraifileupload.bot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "bot")
public class BotProperties {
    private Long qqNum;
    private String password;
    private Long groupNum;

}
