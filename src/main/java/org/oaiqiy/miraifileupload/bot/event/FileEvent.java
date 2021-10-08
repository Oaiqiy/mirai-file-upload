package org.oaiqiy.miraifileupload.bot.event;

import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.FriendMessagePostSendEvent;
import net.mamoe.mirai.event.events.FriendMessagePreSendEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.internal.message.FileMessageImpl;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.FileMessage;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.RemoteFile;
import org.hibernate.internal.util.xml.FilteringXMLEventReader;
import org.oaiqiy.miraifileupload.storage.StorageService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class FileEvent extends SimpleListenerHost {
    private StorageService storageService;
    FileEvent(StorageService storageService){
        this.storageService=storageService;
    }

    @EventHandler
    public void onMessage(@NotNull MessageEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理
        event.getSubject().sendMessage("receive");

        String json = MessageChain.serializeToJsonString(event.getMessage());
        String mirai = event.getMessage().serializeToMiraiCode();

        event.getSubject().sendMessage(json);
        event.getSubject().sendMessage(mirai);

    }

}
