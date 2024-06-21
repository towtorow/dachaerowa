package com.dachaerowa.dachaerowa.util;

import com.dachaerowa.dachaerowa.domain.model.User;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class CommonUtil {
    public static LocalDateTime convertStringToLocalDateTime(String dateTimeString) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return LocalDateTime.parse(dateTimeString, formatter);
    }
    public static File convertMultiPartToFile(MultipartFile file) throws IOException {
        if(Objects.nonNull(file.getOriginalFilename())){
            File convFile = new File(file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(compressImage(file, 800, 600, 0.75f));
            fos.close();
            return convFile;
        }
        return null;
    }

    public static byte[] compressImage(MultipartFile imageFile, int width, int height, float quality) throws IOException {
        try (InputStream inputStream = imageFile.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // 압축 수행
            Thumbnails.of(inputStream)
                    .size(width, height)
                    .outputQuality(quality)
                    .toOutputStream(outputStream);
            return outputStream.toByteArray();
        }
    }

    public static String getAuthenticationUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
