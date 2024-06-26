package com.dachaerowa.dachaerowa.internal.api.controller;

import com.dachaerowa.dachaerowa.domain.model.Gathering;
import com.dachaerowa.dachaerowa.domain.model.User;
import com.dachaerowa.dachaerowa.domain.service.GatheringService;
import com.dachaerowa.dachaerowa.domain.service.UserService;
import com.dachaerowa.dachaerowa.domain.service.impl.CustomUserDetailsService;
import com.dachaerowa.dachaerowa.internal.api.request.GatheringDeleteRequest;
import com.dachaerowa.dachaerowa.internal.api.request.GatheringRequest;
import com.dachaerowa.dachaerowa.internal.api.request.GatheringUpdateRequest;
import com.dachaerowa.dachaerowa.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/gatherings")
public class GatheringController {

    @Autowired
    private GatheringService gatheringService;

    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public ResponseEntity<Gathering> createGathering(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("startDateTime") String startDateTime,
            @RequestParam("endDateTime") String endDateTime,
            @RequestParam("location") String location,
            @RequestParam(value = "images", required = false) List<MultipartFile> images
    ) {
        User user = userService.getUserByUsername(CommonUtil.getAuthenticationUsername());
        GatheringRequest gatheringRequest = new GatheringRequest();
        gatheringRequest.setTitle(title);
        gatheringRequest.setDescription(description);
        gatheringRequest.setStartDateTime(CommonUtil.convertStringToLocalDateTime(startDateTime));
        gatheringRequest.setEndDateTime(CommonUtil.convertStringToLocalDateTime(endDateTime));
        gatheringRequest.setLocation(location);
        gatheringRequest.setImages(images);
        gatheringRequest.setOrganizer(user);

        Gathering savedGathering = gatheringService.saveGatheringAndDetail(gatheringRequest);
        return new ResponseEntity<>(savedGathering, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public Page<Gathering> getGatherings(@RequestParam("page") int page, @RequestParam("size") int size) {
        return gatheringService.getGatherings(page, size);
    }

    @GetMapping("/todo/get")
    public Page<Gathering> getGatheringsAfterNow(@RequestParam("page") int page, @RequestParam("size") int size) {
        return gatheringService.getGatheringsAfterNow(page, size);
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deleteGathering(
            @RequestBody GatheringDeleteRequest request
    ) {

        User user = userService.getUserByUsername(CommonUtil.getAuthenticationUsername());

        Gathering gathering = gatheringService.getGathering(Long.parseLong(request.getGatheringId()));

        if (Objects.equals(user.getId(), gathering.getOrganizer().getId())) {
            gatheringService.deleteGathering(Long.parseLong(request.getGatheringId()));
            return ResponseEntity.ok().build();

        }



        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "모임장만 제거할 수 있습니다.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header("Content-Type", "application/json")
                .body(body);

    }

    @PostMapping("/update")
    public ResponseEntity<Object> deleteGathering(
            @RequestParam("gatheringId") Long gatheringId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value = "deleteImageIds", required = false) List<String> deleteImageIds,
            @RequestParam(value = "images", required = false) List<MultipartFile> images

    ) {
        GatheringUpdateRequest request = new GatheringUpdateRequest();
        request.setGatheringId(gatheringId);
        request.setImages(images);
        request.setDeleteImageIds(deleteImageIds);
        request.setTitle(title);
        request.setDescription(description);

        User user = userService.getUserByUsername(CommonUtil.getAuthenticationUsername());

        Gathering gathering = gatheringService.getGathering(request.getGatheringId());

        if (Objects.equals(user.getId(), gathering.getOrganizer().getId())) {



            gatheringService.updateGathering(request);

            return ResponseEntity.ok().build();

    }




        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "모임장만 수정할 수 있습니다.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header("Content-Type", "application/json")
                .body(body);

    }
}