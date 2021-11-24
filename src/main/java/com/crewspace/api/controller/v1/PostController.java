package com.crewspace.api.controller.v1;

import static com.crewspace.api.constants.SuccessCode.*;

import com.crewspace.api.dto.req.post.PostListRequestDTO;
import com.crewspace.api.dto.req.post.PostRequestDTO;
import com.crewspace.api.dto.req.post.WriteCommunityRequest;
import com.crewspace.api.dto.req.post.WriteCommunityRequestDTO;
import com.crewspace.api.dto.req.post.WriteNoticeRequest;
import com.crewspace.api.dto.req.post.WriteNoticeRequestDTO;
import com.crewspace.api.dto.res.post.CommunityPostDetailResponse;
import com.crewspace.api.dto.res.post.CommunityPostDetailResponseDTO;
import com.crewspace.api.dto.res.post.CommunityPostListResponse;
import com.crewspace.api.dto.res.post.CommunityPostListResponseDTO;
import com.crewspace.api.dto.res.post.NoticePostDetailResponse;
import com.crewspace.api.dto.res.post.NoticePostDetailResponseDTO;
import com.crewspace.api.dto.res.post.WritePostResponse;
import com.crewspace.api.service.CommunityPostService;
import com.crewspace.api.service.NoticePostService;
import com.crewspace.api.service.PostService;
import com.crewspace.api.utils.SecurityUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final CommunityPostService communityPostService;
    private final NoticePostService noticePostService;

    private final PostService postService;

    @PostMapping("/v1/posts/community/{post-category-id}/post")
    public ResponseEntity<WritePostResponse> writeCommunityPost(@PathVariable("post-category-id") Long postCategoryId,
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
        
        return WritePostResponse.newResponse(WRITE_COMMUNITY_POST_SUCCESS);
    }

    @PostMapping("/v1/posts/notice/{post-category-id}/post")
    public ResponseEntity<WritePostResponse> writeNoticePost(@PathVariable("post-category-id") Long postCategoryId,
        @Valid @RequestHeader("Space-Id") Long spaceId, @Valid @ModelAttribute WriteNoticeRequest request){

        String memberEmail = SecurityUtil.getCurrentMemberId();
        List<String> imageURLs = new ArrayList<>();
        if(request.getImage().size() > 0){
            imageURLs = request.getImage().stream()
                .map(image -> "upload with S3")
                .collect(Collectors.toList());
        }

        WriteNoticeRequestDTO requestDTO = request.toWriteNoticeDTO(spaceId, memberEmail,
            postCategoryId, imageURLs);
        noticePostService.write(requestDTO);

        return WritePostResponse.newResponse(WRITE_NOTICE_POST_SUCCESS);
    }

    @GetMapping("/v1/posts/community/{post-id}")
    public ResponseEntity<CommunityPostDetailResponse> communityPostDetail(@PathVariable("post-id") Long postId,
        @Valid @RequestHeader("Space-Id") Long spaceId){

        String memberEmail = SecurityUtil.getCurrentMemberId();
        PostRequestDTO requestDTO = PostRequestDTO.of(spaceId, memberEmail, postId);

        CommunityPostDetailResponseDTO responseDTO = postService.communityDetail(
            requestDTO);

        return CommunityPostDetailResponse.newResponse(READ_COMMUNITY_POST_SUCCESS, responseDTO);
    }

    @GetMapping("/v1/posts/notice/{post-id}")
    public ResponseEntity<NoticePostDetailResponse> noticePostDetail(@PathVariable("post-id") Long postId,
        @Valid @RequestHeader("Space-Id") Long spaceId){

        String memberEmail = SecurityUtil.getCurrentMemberId();
        PostRequestDTO requestDTO = PostRequestDTO.of(spaceId, memberEmail, postId);

        NoticePostDetailResponseDTO responseDTO = postService.noticeDetail(requestDTO);

        return NoticePostDetailResponse.newResponse(READ_NOTICE_POST_SUCCESS, responseDTO);
    }

    @GetMapping("/v1/posts/community")
    public ResponseEntity<CommunityPostListResponse> communityList(@Valid @RequestHeader("Space-Id") Long spaceId,
        @RequestParam(value = "offset", defaultValue = "0", required = false) int offset,
        @RequestParam(value = "postCategoryId", defaultValue = "-1", required = false) Long postCategoryId,
        @RequestParam(value = "type") String type){

        String memberEmail = SecurityUtil.getCurrentMemberId();
        PostListRequestDTO requestDTO = PostListRequestDTO.of(postCategoryId, offset, type, spaceId, memberEmail);

        CommunityPostListResponseDTO responseDTO = postService.communityList(requestDTO);
        return CommunityPostListResponse.newResponse(LOAD_COMMUNITY_LIST_SUCCESS, responseDTO);
    }
}
