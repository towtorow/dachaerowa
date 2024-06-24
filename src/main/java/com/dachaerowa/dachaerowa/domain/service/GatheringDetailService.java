package com.dachaerowa.dachaerowa.domain.service;

import java.util.List;
import java.util.Map;

public interface GatheringDetailService {

    public Map<String, Object> getGatheringDetails(int gatheringId);
    public List<String> getGatheringParticipants(int gatheringId);
    public Long saveParticipant(int gatheringId);
}
