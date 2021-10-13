package org.oaiqiy.miraifileupload.bot;

import lombok.AllArgsConstructor;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class BotConfig {

    private BotProperties botProperties;

    /**
     *将bot声明为一个bean
     * @return Bot
     */

    @Bean
    public Bot bot(){
        Bot bot = BotFactory.INSTANCE.newBot(botProperties.getQqNum(),botProperties.getPassword());
        bot.login();
        return bot;
    }
}
