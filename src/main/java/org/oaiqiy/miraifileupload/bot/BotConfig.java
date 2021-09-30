package org.oaiqiy.miraifileupload.bot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {
    @Autowired
    BotProperties botProperties;

    @Bean
    public Bot bot(){
        return BotFactory.INSTANCE.newBot(botProperties.getQqNum(),botProperties.getPassword());
    }
}
