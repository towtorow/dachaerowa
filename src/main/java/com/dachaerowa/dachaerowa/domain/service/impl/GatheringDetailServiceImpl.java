package com.dachaerowa.dachaerowa.domain.service.impl;

import com.dachaerowa.dachaerowa.domain.model.Gathering;
import com.dachaerowa.dachaerowa.domain.model.GatheringDetail;
import com.dachaerowa.dachaerowa.domain.model.GatheringsParticipants;
import com.dachaerowa.dachaerowa.domain.model.User;
import com.dachaerowa.dachaerowa.domain.repository.GatheringDetailRepository;
import com.dachaerowa.dachaerowa.domain.repository.GatheringRepository;
import com.dachaerowa.dachaerowa.domain.repository.GatheringsParticipantsRepository;
import com.dachaerowa.dachaerowa.domain.repository.UserRepository;
import com.dachaerowa.dachaerowa.domain.service.GatheringDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GatheringDetailServiceImpl implements GatheringDetailService {
    @Autowired
    private GatheringRepository gatheringRepository;
    @Autowired
    private GatheringDetailRepository gatheringDetailRepository;
    @Autowired
    private GatheringsParticipantsRepository gatheringsParticipantsRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<String, Object> getGatheringDetails(int gatheringId){
        Gathering gathering = gatheringRepository.findById((long) gatheringId).orElseThrow();
        List<GatheringDetail> gatheringDetails = gatheringDetailRepository.findAllByGatheringId(gathering.getId());
        return Map.of("gathering", gathering, "gatheringDetails", gatheringDetails);
    }
    @Override
    public List<String> getGatheringParticipants(int gatheringId){
        List<GatheringsParticipants> gatheringsParticipants = gatheringsParticipantsRepository.findByGatheringId((long) gatheringId);
        return gatheringsParticipants.stream().map(GatheringsParticipants::getParticipantUsername).toList();
    }

    @Override
    public Long saveParticipant(int gatheringId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetail.getUsername()).orElseThrow();
        return gatheringsParticipantsRepository.save(GatheringsParticipants.builder().gatheringId((long) gatheringId).participantId(user.getId()).participantUsername(user.getUsername()).build()).getId();
    }

}
