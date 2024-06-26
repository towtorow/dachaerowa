package com.dachaerowa.dachaerowa.internal.api.request;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GatheringUpdateRequest {

    private Long gatheringId;
    private String title;
    private String description;

    private List<MultipartFile> images;

    private List<String> deleteImageIds;
    public List<Long> getDeleteImageIdsAsLong() {
        if (deleteImageIds == null) {
            return null;
        }
        return deleteImageIds.stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
