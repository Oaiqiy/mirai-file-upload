package org.oaiqiy.miraifileupload.storage;

import kotlin.coroutines.CoroutineContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.GroupMessagePostSendEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.data.FileMessage;
import net.mamoe.mirai.utils.RemoteFile;
import org.jetbrains.annotations.NotNull;
import org.oaiqiy.miraifileupload.bot.BotProperties;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BotEventHandler extends SimpleListenerHost {
    private final StorageData storageData;
    private final BotProperties botProperties;

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception){

    }
    @EventHandler
    public void onMessage(@NotNull GroupMessageEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理
        Group group = event.getGroup();
        if(group.getId()!=botProperties.getGroupNum())
            return;

        FileMessage fileMessage = event.getMessage().get(FileMessage.Key);
        if(fileMessage==null)
            return;

        event.getSubject().sendMessage("received");
        RemoteFile remoteFile = fileMessage.toRemoteFile(group);
        storageData.getData().add(0,new RemoteFileData(remoteFile.getDownloadInfo().getUrl(),remoteFile.getName()));


    }

    @EventHandler
    public void onMessage(@NotNull GroupMessagePostSendEvent event) throws Exception {

        Group group = event.getTarget();
        if(group.getId()!=botProperties.getGroupNum())
            return;

        FileMessage fileMessage = event.getMessage().get(FileMessage.Key);
        if(fileMessage == null)
            return;
        event.getTarget().sendMessage("send");
        RemoteFile remoteFile = fileMessage.toRemoteFile(group);
        storageData.getData().add(0,new RemoteFileData(remoteFile.getDownloadInfo().getUrl(),remoteFile.getName()));

    }


}
