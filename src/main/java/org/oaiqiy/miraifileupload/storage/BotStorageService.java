package org.oaiqiy.miraifileupload.storage;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.FileSupported;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.FileMessage;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.RemoteFile;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
        Path destinationFile = this.rootLocation.resolve(
                        Paths.get(file.getOriginalFilename()))
                .normalize().toAbsolutePath();
        if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
            // This is a security check
            throw new StorageException(
                    "Cannot store file outside current directory.");
        }
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
        }

        log.info("store successfully");

        File file1 = new File(destinationFile.toString());

        if(file1.exists()){
            group.sendMessage("exists");
        }


        ExternalResource.create(file.getInputStream());

        FileMessage fm = ExternalResource.uploadAsFile(ExternalResource.create(file1),group,file.getOriginalFilename());

//        this.wait(10000);
//        group.sendMessage(fm.serializeToMiraiCode());
//        RemoteFile rm = fm.toRemoteFile(group);
//        fm.getInternalId();
//        rm.getName();
//        group.sendMessage(rm.getName());
//        if(rm.isDirectory()){
//            group.sendMessage("woshi dir");
//        }
//        if(rm.isFile()){
//            group.sendMessage("woshi file");
//        }
//        rm.renameTo("aa");

        //group.getFilesRoot().upload(file1);
        //group.getFilesRoot().upload(ExternalResource.create(file1));
        //group.getFilesRoot().uploadAndSend(ExternalResource.create(file1));

    }

    @Override
    public Stream<Path> loadAll() {
        return null;
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
