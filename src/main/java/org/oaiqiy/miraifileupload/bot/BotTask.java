package org.oaiqiy.miraifileupload.bot;

import lombok.AllArgsConstructor;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import org.oaiqiy.miraifileupload.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BotTask {

    private final Bot bot;


    @Bean
    public CommandLineRunner test(){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

//                Friend friend = bot.getFriend(1320371940l);
//                friend.sendMessage("5555");

            }
        };
    }
}
