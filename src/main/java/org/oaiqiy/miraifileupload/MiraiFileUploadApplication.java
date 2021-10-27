package org.oaiqiy.miraifileupload;

import net.mamoe.mirai.Bot;
import org.oaiqiy.miraifileupload.bot.BotEventHandler;
import org.oaiqiy.miraifileupload.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;



@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class })
@ConfigurationPropertiesScan
public class MiraiFileUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiraiFileUploadApplication.class, args);
    }

    /**
     * 初始化文件数据
     * @param storageService
     * @return
     */
    @Bean
    public CommandLineRunner init(StorageService storageService){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                storageService.loadAll();
            }
        };
    }

    /**
     * 初始化bot事件监听器
     * @param bot
     * @param botEventHandler
     * @return
     */
    @Bean
    public CommandLineRunner initBotEvent(Bot bot, BotEventHandler botEventHandler){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                bot.getEventChannel().registerListenerHost(botEventHandler);
            }
        };

    }




}
