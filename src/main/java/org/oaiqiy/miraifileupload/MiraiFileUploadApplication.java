package org.oaiqiy.miraifileupload;

import net.mamoe.mirai.Bot;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.valves.RemoteIpValve;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.oaiqiy.miraifileupload.storage.BotEventHandler;
import org.oaiqiy.miraifileupload.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import java.nio.charset.Charset;

@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class })
@ConfigurationPropertiesScan
public class MiraiFileUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiraiFileUploadApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(StorageService storageService){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                storageService.loadAll();
            }
        };
    }
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
