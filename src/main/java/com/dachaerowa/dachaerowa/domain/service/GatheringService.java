package com.dachaerowa.dachaerowa.domain.service;

import com.dachaerowa.dachaerowa.domain.model.Gathering;
import com.dachaerowa.dachaerowa.domain.model.GatheringDetail;
import com.dachaerowa.dachaerowa.internal.api.request.GatheringRequest;
import com.dachaerowa.dachaerowa.internal.api.request.GatheringUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface GatheringService {
    Gathering saveGathering(Gathering gathering);
    List<GatheringDetail> saveGatheringDetails(List<GatheringDetail> gatheringDetails);

    Gathering saveGatheringAndDetail(GatheringRequest gatheringRequest);

    Page<Gathering> getGatherings(int page, int size);

    public Page<Gathering> getGatheringsAfterNow(int page, int size);

    public Gathering getGathering(Long gatheringId);

    public void deleteGathering(Long gatheringId);

    public void updateGathering(GatheringUpdateRequest request);

}
