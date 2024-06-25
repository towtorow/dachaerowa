package com.dachaerowa.dachaerowa.internal.api.controller;

import com.dachaerowa.dachaerowa.domain.model.Gathering;
import com.dachaerowa.dachaerowa.domain.service.GatheringDetailService;
import com.dachaerowa.dachaerowa.domain.service.GatheringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/gatheringDetails")
public class GatheringDetailController {

    @Autowired
    private GatheringDetailService gatheringDetailService;

    @GetMapping("/get")
    public Map<String, Object> getGatheringDetails(@RequestParam("gatheringId") int gatheringId) {
        return gatheringDetailService.getGatheringDetails(gatheringId);
    }
    @GetMapping("/participants/get")
    public List<String> getGatheringParticipants(@RequestParam("gatheringId") int gatheringId){
        return gatheringDetailService.getGatheringParticipants(gatheringId);
    }

    @GetMapping("/participant/save")
    public ResponseEntity<Long> saveParticipant(@RequestParam("gatheringId") int gatheringId){
        Long gatheringParticipantId = gatheringDetailService.saveParticipant(gatheringId);
        if(Objects.isNull(gatheringParticipantId)){
            return new ResponseEntity<>(gatheringParticipantId, HttpStatus.CONFLICT);
        }else{
            return new ResponseEntity<>(gatheringParticipantId, HttpStatus.CREATED);
        }
    }
}
