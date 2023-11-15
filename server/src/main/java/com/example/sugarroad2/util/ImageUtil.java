package com.example.sugarroad2.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUtil {
    private static final String BASEDIR = "C:/sugar-road/database/images";

    public ImageUtil() {
        File dir = new File(BASEDIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public byte[] readImage(String uri) throws IOException {
        Path p = Paths.get(BASEDIR + uri);
        return Files.readAllBytes(p);
    }

    public String writeImage(MultipartFile imageFile) {
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + imageFile.getOriginalFilename();
        String path = BASEDIR + "/" + fileName;

        try {
            File f = new File(path);
            imageFile.transferTo(f);
            return "/images/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
