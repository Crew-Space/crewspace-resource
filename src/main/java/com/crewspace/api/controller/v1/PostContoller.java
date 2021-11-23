package com.crewspace.api.controller.v1;

import static com.crewspace.api.constants.SuccessCode.*;

import com.crewspace.api.dto.req.post.WriteCommunityRequest;
import com.crewspace.api.dto.req.post.WriteCommunityRequestDTO;
import com.crewspace.api.dto.res.postCategory.WriteCommunityResponse;
import com.crewspace.api.service.CommunityPostService;
import com.crewspace.api.utils.SecurityUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostContoller {

    private final CommunityPostService communityPostService;

    @PostMapping("/community/{post-category-id}/post")
    public ResponseEntity<WriteCommunityResponse> writeCommunityPost(@PathVariable("post-category-id") Long postCategoryId,
        @Valid @RequestHeader("Space-Id") Long spaceId, @Valid @ModelAttribute WriteCommunityRequest request){

        String memberEmail = SecurityUtil.getCurrentMemberId();
        List<String> imageURLs = new ArrayList<>();
        if(request.getImage().size() > 0){
             imageURLs = request.getImage().stream()
                .map(image -> "upload with S3")
                .collect(Collectors.toList());
        }

        WriteCommunityRequestDTO requestDTO = request.toWriteCommunityRequestDTO(
            spaceId, memberEmail, postCategoryId, imageURLs);

        communityPostService.write(requestDTO);

        return WriteCommunityResponse.newResponse(WRITE_COMMUNITY_POST_SUCCESS);
    }
}
