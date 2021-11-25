package com.crewspace.api.controller.v1;

import static com.crewspace.api.constants.SuccessCode.*;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.req.spaceMember.EnteredSpaceRequestDTO;
import com.crewspace.api.dto.req.spaceMember.MemberListRequestDTO;
import com.crewspace.api.dto.req.spaceMember.MemberSearchRequestDTO;
import com.crewspace.api.dto.res.spaceMember.EnteredSpaceResponse;
import com.crewspace.api.dto.res.spaceMember.EnteredSpaceResponseDTO;
import com.crewspace.api.dto.res.spaceMember.SpaceMemberListResponse;
import com.crewspace.api.dto.res.spaceMember.SpaceMemberListResponseDTO;
import com.crewspace.api.dto.res.spaceMember.SpaceMemberSearchResponse;
import com.crewspace.api.dto.res.spaceMember.SpaceMemberSearchResponseDTO;
import com.crewspace.api.service.SpaceMemberService;
import com.crewspace.api.utils.SecurityUtil;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SpaceMemberController {

    private final SpaceMemberService spaceMemberService;

    @GetMapping("/v1/spaces")
    public ResponseEntity<EnteredSpaceResponse> enteredSpaceList(){
        String memberEmail = SecurityUtil.getCurrentMemberId();

        EnteredSpaceRequestDTO enteredSpaceRequestDTO = new EnteredSpaceRequestDTO(memberEmail);
        EnteredSpaceResponseDTO responseDTO = spaceMemberService.enteredSpaceList(enteredSpaceRequestDTO);

        return EnteredSpaceResponse.toResponse(LOAD_SPACES_SUCCESS, responseDTO);
    }

    @GetMapping("/v1/members")
    public ResponseEntity<SpaceMemberListResponse> memberList(@Valid @RequestHeader("Space-Id") Long spaceId,
        @RequestParam(value = "memberCategoryId", defaultValue = "-1", required = false) Long memberCategoryId){

        String memberEmail = SecurityUtil.getCurrentMemberId();

        MemberListRequestDTO requestDTO = MemberListRequestDTO.of(spaceId, memberEmail, memberCategoryId);

        SpaceMemberListResponseDTO responseDTO = spaceMemberService.memberList(requestDTO);

        return SpaceMemberListResponse.newResponse(LOAD_MEMBER_LIST_SUCCESS, responseDTO);
    }

    @GetMapping("/v1/members/search")
    public ResponseEntity<SpaceMemberSearchResponse> memberSearch(@Valid @RequestHeader("Space-Id") Long spaceId,
        @RequestParam(value = "keyword") String keyword){

        String memberEmail = SecurityUtil.getCurrentMemberId();

        MemberSearchRequestDTO requestDTO = MemberSearchRequestDTO.of(spaceId, memberEmail, keyword);

        SpaceMemberSearchResponseDTO responseDTO = spaceMemberService.memberSearch(requestDTO);

        return SpaceMemberSearchResponse.newResponse(SEARCH_MEMBER_LIST_SUCCESS, responseDTO);
    }
}
