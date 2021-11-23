package com.crewspace.api.controller.v1;

import static com.crewspace.api.constants.SuccessCode.FIX_POST_SUCCESS;
import static com.crewspace.api.constants.SuccessCode.SAVE_POST_SUCCESS;
import static com.crewspace.api.constants.SuccessCode.UNFIX_POST_SUCCESS;
import static com.crewspace.api.constants.SuccessCode.UNSAVE_POST_SUCCESS;

import com.crewspace.api.dto.req.memberPost.MemberPostRequestDTO;
import com.crewspace.api.dto.res.memberPost.MemberPostResponse;
import com.crewspace.api.service.MemberPostService;
import com.crewspace.api.utils.SecurityUtil;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberPostController {

    private final MemberPostService memberPostService;

    @PostMapping("/v1/posts/{post-id}/save")
    public ResponseEntity<MemberPostResponse> savePost(
        @PathVariable("post-id") Long postId, @Valid @RequestHeader("Space-Id") Long spaceId){

        String memberEmail = SecurityUtil.getCurrentMemberId();
        MemberPostRequestDTO requestDTO = MemberPostRequestDTO.of(spaceId, memberEmail, postId);
        memberPostService.save(requestDTO);

        return MemberPostResponse.newResponse(SAVE_POST_SUCCESS);
    }

    @DeleteMapping("/v1/posts/{post-id}/save")
    public ResponseEntity<MemberPostResponse> unSavePost(
        @PathVariable("post-id") Long postId, @Valid @RequestHeader("Space-Id") Long spaceId){

        String memberEmail = SecurityUtil.getCurrentMemberId();
        MemberPostRequestDTO requestDTO = MemberPostRequestDTO.of(spaceId, memberEmail, postId);
        memberPostService.unSave(requestDTO);

        return MemberPostResponse.newResponse(UNSAVE_POST_SUCCESS);
    }

    @PostMapping("/v1/posts/{post-id}/fix")
    public ResponseEntity<MemberPostResponse> fixPost(
        @PathVariable("post-id") Long postId, @Valid @RequestHeader("Space-Id") Long spaceId){

        String memberEmail = SecurityUtil.getCurrentMemberId();
        MemberPostRequestDTO requestDTO = MemberPostRequestDTO.of(spaceId, memberEmail, postId);
        memberPostService.fix(requestDTO);

        return MemberPostResponse.newResponse(FIX_POST_SUCCESS);
    }

    @DeleteMapping("/v1/posts/{post-id}/fix")
    public ResponseEntity<MemberPostResponse> unFixPost(
        @PathVariable("post-id") Long postId, @Valid @RequestHeader("Space-Id") Long spaceId){

        String memberEmail = SecurityUtil.getCurrentMemberId();
        MemberPostRequestDTO requestDTO = MemberPostRequestDTO.of(spaceId, memberEmail, postId);
        memberPostService.unFix(requestDTO);

        return MemberPostResponse.newResponse(UNFIX_POST_SUCCESS);
    }
}
