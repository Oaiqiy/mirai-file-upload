package org.oaiqiy.miraifileupload.storage;

import kotlin.coroutines.CoroutineContext;
import lombok.AllArgsConstructor;
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
    private final StorageService storageService;


    /**
     * 处理接收到的群文件消息
     * @param event
     */
    @EventHandler
    public void onMessage(@NotNull GroupMessageEvent event) { // 可以抛出任何异常, 将在 handleException 处理
        Group group = event.getGroup();
        if(group.getId()!=botProperties.getGroupNum())
            return;

        FileMessage fileMessage = event.getMessage().get(FileMessage.Key);
        if(fileMessage==null)
            return;

        event.getSubject().sendMessage("received");
        RemoteFile remoteFile = fileMessage.toRemoteFile(group);
        storageData.getData().add(0,new RemoteFileData(remoteFile.getId(),remoteFile.getDownloadInfo().getUrl(),remoteFile.getName()));

    }

    /**
     * 处理发送的群文件消息
     * @param event
     */
    @EventHandler
    public void onMessage(@NotNull GroupMessagePostSendEvent event) {

        Group group = event.getTarget();
        if(group.getId()!=botProperties.getGroupNum())
            return;

        FileMessage fileMessage = event.getMessage().get(FileMessage.Key);
        if(fileMessage == null)
            return;
        event.getTarget().sendMessage("send");
        RemoteFile remoteFile = fileMessage.toRemoteFile(group);
        storageData.getData().add(0,new RemoteFileData(remoteFile.getId(),remoteFile.getDownloadInfo().getUrl(),remoteFile.getName()));

    }

    /**
     * 处理群聊中其他用户删除文件的消息
     * 删除群文件会发出一个撤回消息事件
     * @param event
     */
    @EventHandler
    public void onMessage(@NotNull MessageRecallEvent.GroupRecall event){
        Group group = event.getGroup();
        if(group.getId()!=botProperties.getGroupNum())
            return;
        if(event.getOperator()==null)
            return;
        storageService.reloadAll();
    }


}
