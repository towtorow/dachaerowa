package com.dachaerowa.dachaerowa.internal.api.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class GatheringRequest {
    private List<MultipartFile> images;

    private String title;

    private String description;

    private String category;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private String location;
}
