package org.oaiqiy.miraifileupload.bot;

import kotlin.coroutines.CoroutineContext;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.GroupMessagePostSendEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.data.FileMessage;
import net.mamoe.mirai.message.data.MessageContent;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.RemoteFile;
import org.jetbrains.annotations.NotNull;
import org.oaiqiy.miraifileupload.bot.BotProperties;
import org.oaiqiy.miraifileupload.storage.RemoteFileData;
import org.oaiqiy.miraifileupload.storage.StorageData;
import org.oaiqiy.miraifileupload.storage.StorageService;
import org.springframework.stereotype.Component;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class BotEventHandler extends SimpleListenerHost {

    private final StorageData storageData;
    private final BotProperties botProperties;
    private final StorageService storageService;


    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        exception.printStackTrace();
    }

    /**
     * 处理接收到的群消息
     * @param event
     */
    @EventHandler
    public void onMessage(@NotNull GroupMessageEvent event) throws Exception{ // 可以抛出任何异常, 将在 handleException 处理
        Group group = event.getGroup();
        if(group.getId()!=botProperties.getGroupNum())
            return;

        FileMessage fileMessage = event.getMessage().get(FileMessage.Key);
        if(fileMessage!=null){
            event.getSubject().sendMessage("received");
            RemoteFile remoteFile = fileMessage.toRemoteFile(group);
            storageData.getData().add(0,new RemoteFileData(remoteFile.getId(),remoteFile.getDownloadInfo().getUrl(),remoteFile.getName()));
            return;
        }

        MessageContent messageContent = event.getMessage().get(PlainText.Key);
        if(messageContent!=null){


            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String text = messageContent.contentToString();
            Pattern pattern = Pattern.compile("((http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&:/~\\+#]*[\\w\\-\\@?^=%&/~\\+#])?)");
            Matcher matcher =pattern.matcher(text);
            if(matcher.find()){
                String web = matcher.group(0);
                event.getSubject().sendMessage(web);
                storageData.getText().add(web);
                return;
            }
            event.getSubject().sendMessage(messageContent.contentToString());
            storageData.getText().add(text);

        }




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
