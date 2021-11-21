package com.crewspace.api.controller.v1;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.req.space.EnteredSpaceRequestDTO;
import com.crewspace.api.dto.res.space.EnteredSpaceResponse;
import com.crewspace.api.dto.res.space.EnteredSpaceResponseDTO;
import com.crewspace.api.service.SpaceMemberService;
import com.crewspace.api.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
