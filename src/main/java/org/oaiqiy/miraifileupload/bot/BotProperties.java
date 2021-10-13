package org.oaiqiy.miraifileupload.bot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;



@Component
@Data
@ConfigurationProperties(prefix = "bot")
public class BotProperties {

    /**
     * bot qq账户
     */
    private Long qqNum;

    /**
     * bot qq密码
     */
    private String password;

    /**
     * 存文件的群聊的群号
     */
    private Long groupNum;

}
