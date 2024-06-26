package com.dachaerowa.dachaerowa.domain.service.impl;

import com.dachaerowa.dachaerowa.domain.model.Category;
import com.dachaerowa.dachaerowa.domain.model.Gathering;
import com.dachaerowa.dachaerowa.domain.model.GatheringDetail;
import com.dachaerowa.dachaerowa.domain.repository.GatheringDetailRepository;
import com.dachaerowa.dachaerowa.domain.repository.GatheringRepository;
import com.dachaerowa.dachaerowa.domain.service.GatheringService;
import com.dachaerowa.dachaerowa.internal.api.request.GatheringRequest;
import com.dachaerowa.dachaerowa.internal.api.request.GatheringUpdateRequest;
import com.dachaerowa.dachaerowa.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
@Service
public class GatheringServiceImpl implements GatheringService {
    @Autowired
    private GatheringRepository gatheringRepository;
    @Autowired
    private GatheringDetailRepository gatheringDetailRepository;
    private final S3Service s3Service;

    public GatheringServiceImpl(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @Override
    public Gathering saveGathering(Gathering gathering) {
        return gatheringRepository.save(gathering);
    }

    @Override
    public List<GatheringDetail> saveGatheringDetails(List<GatheringDetail> gatheringDetails) {
        return gatheringDetailRepository.saveAll(gatheringDetails);
    }
    @Override
    public Gathering saveGatheringAndDetail(GatheringRequest request) {

        List<String> imageUrls = new ArrayList<>(); 
        if(Objects.nonNull(request.getImages()) && !request.getImages().isEmpty()){
            for (MultipartFile file : request.getImages()) {
                try {

                    File convertedFile = CommonUtil.convertMultiPartToFile(file);
                    String fileUrl = s3Service.uploadFile("dachaerowa-bucket", file.getOriginalFilename(), convertedFile);
                    imageUrls.add(fileUrl);

                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        Gathering gathering = new Gathering();
        gathering.setTitle(request.getTitle());
        gathering.setImageUrl(!imageUrls.isEmpty() ? imageUrls.get(0) : null);
        gathering.setDescription(request.getDescription());
        gathering.setCategory(Category.IRUROWA.name());
        gathering.setStartDateTime(request.getStartDateTime());
        gathering.setEndDateTime(request.getEndDateTime());
        gathering.setLocation(request.getLocation());
        gathering.setOrganizer(request.getOrganizer());

        gathering = gatheringRepository.save(gathering);

        List<GatheringDetail> gatheringDetails = new ArrayList<>();

        Long gateringId = gathering.getId();

        imageUrls.forEach(item -> gatheringDetails.add(GatheringDetail.builder().gatheringId(gateringId).imageUrl(item).build()));

        gatheringDetailRepository.saveAll(gatheringDetails);

        return gathering;
    }
    @Override
    public Page<Gathering> getGatherings(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return gatheringRepository.findAllWithJoinOrganizerOnly(pageable);
    }

    @Override
    public Page<Gathering> getGatheringsAfterNow(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return gatheringRepository.findAllWithJoinOrganizerOnlyAfterNow(pageable, LocalDateTime.now());
    }

    @Override
    public Gathering getGathering(Long gatheringId){
        return gatheringRepository.findById(gatheringId).orElseThrow();
    }
    @Transactional
    public void deleteGathering(Long gatheringId){
        gatheringRepository.updateDeletedDateById(gatheringId, LocalDateTime.now());
    }
    @Transactional
    public void updateGathering(GatheringUpdateRequest request){
        gatheringRepository.updateTitleAndDescriptionById(request.getGatheringId(), request.getTitle(), request.getDescription());
        if (Objects.nonNull(request.getDeleteImageIds()) && !request.getDeleteImageIds().isEmpty()) {

            gatheringDetailRepository.updateDeletedDateById(request.getDeleteImageIdsAsLong(), LocalDateTime.now());
        }

        List<String> imageUrls = new ArrayList<>();
        if(Objects.nonNull(request.getImages()) && !request.getImages().isEmpty()){
            for (MultipartFile file : request.getImages()) {
                try {

                    File convertedFile = CommonUtil.convertMultiPartToFile(file);
                    String fileUrl = s3Service.uploadFile("dachaerowa-bucket", file.getOriginalFilename(), convertedFile);
                    imageUrls.add(fileUrl);

                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }

        List<GatheringDetail> gatheringDetails = new ArrayList<>();

        Long gateringId = request.getGatheringId();

        imageUrls.forEach(item -> gatheringDetails.add(GatheringDetail.builder().gatheringId(gateringId).imageUrl(item).build()));

        gatheringDetailRepository.saveAll(gatheringDetails);


    }

}
