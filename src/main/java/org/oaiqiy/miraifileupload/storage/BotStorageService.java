package org.oaiqiy.miraifileupload.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.RemoteFile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class BotStorageService implements StorageService{
    private final Bot bot;
    private final StorageProperties storageProperties;
    private Path rootLocation;



    @Override
    public void init() {
        rootLocation=Paths.get(storageProperties.getLocation());
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public void store(MultipartFile file) throws Exception {

        log.info("execute store");
        rootLocation=Paths.get(storageProperties.getLocation());
        Group group = bot.getGroup(1018811259);

        group.sendMessage("begin");

        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }

        ExternalResource.uploadAsFile(ExternalResource.create(file.getInputStream()),group,file.getOriginalFilename());


    }

    @Override
    public Stream<String> loadAll() {
        Group group = bot.getGroup(1018811259);
        RemoteFile root = group.getFilesRoot();
        return root.listFilesCollection().stream().map(remoteFile ->
            Objects.requireNonNull(remoteFile.getDownloadInfo()).getUrl());
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {
        rootLocation=Paths.get(storageProperties.getLocation());
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void delete(String filename) {

    }
}
