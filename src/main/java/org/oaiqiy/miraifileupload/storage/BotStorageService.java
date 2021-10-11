package org.oaiqiy.miraifileupload.storage;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.selects.SelectClause2;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.RemoteFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.oaiqiy.miraifileupload.bot.BotProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BotStorageService implements StorageService{
    private final Bot bot;
    private final Group group;
    private final StorageData storageData;


    @Autowired
    BotStorageService(Bot bot, BotProperties botProperties,StorageData storageData){
        this.bot= bot;
        this.group=bot.getGroup(botProperties.getGroupNum());
        this.storageData = storageData;
    }





    @Override
    public void store(MultipartFile file) throws Exception {


        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }

        //group.getFilesRoot().resolve(file.getName()).uploadAndSend(ExternalResource.create(file.getInputStream()));
        ExternalResource.sendAsFile(ExternalResource.create(file.getInputStream()),group,file.getOriginalFilename(),new RemoteFile.ProgressionCallback(){
            @Override
            public void onSuccess(@NotNull RemoteFile file, @NotNull ExternalResource resource) {
                log.info("dfghjklkjhgdfasfsadf");
            }

            @Override
            public void onFailure(@NotNull RemoteFile file, @NotNull ExternalResource resource, @NotNull Throwable exception) {
                log.info("fffffffff");
            }
        });


    }



    @Override
    public void loadAll() {
        RemoteFile root = group.getFilesRoot();
        List<RemoteFile> files = root.listFilesCollection();
        storageData.setData( files.stream().map(remoteFile -> new RemoteFileData(remoteFile.getId(),remoteFile.getDownloadInfo().getUrl(),remoteFile.getName())).collect(Collectors.toList()));

    }

    @Override
    public void reloadAll() {
        RemoteFile root = group.getFilesRoot();
        List<String> fileIds = root.listFilesCollection().stream().map(remoteFile -> remoteFile.getId()).collect(Collectors.toList());
        storageData.getData().removeIf(remoteFileData -> !fileIds.removeIf(s -> s.equals(remoteFileData.getId())));


    }


    @Override
    public void delete(String fileId) {
        RemoteFile remoteFile = group.getFilesRoot().resolveById(fileId);
        if(remoteFile!=null){
            remoteFile.delete();
        }

        storageData.getData().removeIf(x -> x.getId().equals(fileId));

    }
}
