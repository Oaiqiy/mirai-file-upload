package org.oaiqiy.miraifileupload.bot.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.NotNull;
import kotlinx.coroutines.flow.Flow;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.FileSupported;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.FileMessage;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.RemoteFile;
import org.hibernate.internal.util.xml.FilteringXMLEventReader;
import org.oaiqiy.miraifileupload.bot.BotProperties;
import org.oaiqiy.miraifileupload.storage.StorageService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;


@Slf4j
@Service
@AllArgsConstructor
public class FileEventHandler extends SimpleListenerHost {
    private final BotProperties botProperties;


    @EventHandler
    public void onMessage(@NotNull GroupMessageEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理
        //event.getSubject().sendMessage("receive");

        if(event.getMessage() instanceof FileMessage){
            event.getSubject().sendMessage("55555555555555555");
        }

        String json = MessageChain.serializeToJsonString(event.getMessage());
        String mirai = event.getMessage().serializeToMiraiCode();

        Group group = event.getSubject();
        Stream<RemoteFile> fileStream = (Stream<RemoteFile>) group.getFilesRoot().listFiles();



        ObjectMapper om = new ObjectMapper();
        JsonNode jsonNode = om.readTree(json);

        JsonNode msg = jsonNode.get(1);


        Friend me = event.getBot().getFriend(botProperties.getYourQq());




        FileMessage fm = FileMessage.create(msg.get("id").asText(),msg.get("internalId").asInt(),msg.get("name").asText(),msg.get("size").asLong());

        RemoteFile remoteFile = fm.toRemoteFile((FileSupported) event.getSubject());



        String url = remoteFile.getDownloadInfo().getUrl();



        event.getSubject().sendMessage(url);





//        event.getSubject().sendMessage(json);
//        event.getSubject().sendMessage(mirai);

    }

}
