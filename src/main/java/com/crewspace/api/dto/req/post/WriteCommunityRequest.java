package com.crewspace.api.dto.req.post;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class WriteCommunityRequest {
    private List<MultipartFile> image = new ArrayList<>();
    private String description;

    public WriteCommunityRequestDTO toWriteCommunityRequestDTO(Long spaceId, String memberEmail,
        Long postCategoryId, List<String> image){
        return WriteCommunityRequestDTO.of(spaceId, memberEmail, postCategoryId, image, description);
    }
}
