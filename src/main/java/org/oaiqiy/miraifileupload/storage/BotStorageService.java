package org.oaiqiy.miraifileupload.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.RemoteFile;
import org.oaiqiy.miraifileupload.bot.BotProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class BotStorageService implements StorageService{
    private final Bot bot;
    private final Group group;

    @Autowired
    BotStorageService(Bot bot, BotProperties botProperties){
        this.bot= bot;
        this.group=bot.getGroup(botProperties.getGroupNum());
    }





    @Override
    public void store(MultipartFile file) throws Exception {


        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }

        ExternalResource.sendAsFile(ExternalResource.create(file.getInputStream()),group,file.getOriginalFilename());


    }



    @Override
    public List<URLandName> loadAll() {

        RemoteFile root = group.getFilesRoot();
        return root.listFilesCollection().stream().map(remoteFile -> new URLandName(remoteFile.getDownloadInfo().getUrl(),remoteFile.getName())).collect(Collectors.toList());

    }





    @Override
    public void delete(String filename) {
        RemoteFile root = group.getFilesRoot();
        for(RemoteFile x:root.listFilesCollection()){
            if(x.getName().equals(filename)){
                x.delete();
            }
        }
    }
}
