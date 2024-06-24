package com.dachaerowa.dachaerowa.internal.api.controller;

import com.dachaerowa.dachaerowa.domain.model.Gathering;
import com.dachaerowa.dachaerowa.domain.model.User;
import com.dachaerowa.dachaerowa.domain.service.GatheringService;
import com.dachaerowa.dachaerowa.domain.service.UserService;
import com.dachaerowa.dachaerowa.domain.service.impl.CustomUserDetailsService;
import com.dachaerowa.dachaerowa.internal.api.request.GatheringRequest;
import com.dachaerowa.dachaerowa.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

}