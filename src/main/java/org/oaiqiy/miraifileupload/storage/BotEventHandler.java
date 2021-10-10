package org.oaiqiy.miraifileupload.storage;

import kotlin.coroutines.CoroutineContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.FileMessage;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.RemoteFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class BotEventHandler extends SimpleListenerHost {
    private final StorageData storageData;

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception){
        // 处理事件处理时抛出的异常
    }
    @EventHandler
    public void onMessage(@NotNull GroupMessageEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理
        FileMessage fileMessage = event.getMessage().get(FileMessage.Key);
        if(fileMessage==null)
            return;
        event.getSubject().sendMessage("received");
        RemoteFile remoteFile = fileMessage.toRemoteFile(event.getGroup());
        storageData.getData().add(0,new RemoteFileData(remoteFile.getDownloadInfo().getUrl(),remoteFile.getName()));


            // 无返回值, 表示一直监听事件.
    }

}
