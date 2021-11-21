package com.crewspace.api.controller.v1;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.req.spaceMember.EnteredSpaceRequestDTO;
import com.crewspace.api.dto.res.spaceMember.EnteredSpaceResponse;
import com.crewspace.api.dto.res.spaceMember.EnteredSpaceResponseDTO;
import com.crewspace.api.service.SpaceMemberService;
import com.crewspace.api.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

        return EnteredSpaceResponse.toResponse(SuccessCode.LOAD_SPACES_SUCCESS, responseDTO);
    }

}
