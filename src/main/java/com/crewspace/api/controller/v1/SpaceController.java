package com.crewspace.api.controller.v1;

import static com.crewspace.api.constants.SuccessCode.*;

import com.crewspace.api.dto.req.space.CreateSpaceRequest;
import com.crewspace.api.dto.req.space.InvitationCodeRequestDTO;
import com.crewspace.api.dto.req.space.RegisterInfoRequestDTO;
import com.crewspace.api.dto.req.space.SpaceEnterRequest;
import com.crewspace.api.dto.req.space.SpaceMainRequestDTO;
import com.crewspace.api.dto.res.space.CreateSpaceResponse;
import com.crewspace.api.dto.res.space.CreateSpaceResponseDTO;
import com.crewspace.api.dto.res.space.InvitationCodeResponse;
import com.crewspace.api.dto.res.space.InvitationCodeResponseDTO;
import com.crewspace.api.dto.res.space.RegisterInfoResponse;
import com.crewspace.api.dto.res.space.RegisterInfoResponseDTO;
import com.crewspace.api.dto.res.space.SpaceEnterResponse;
import com.crewspace.api.dto.res.space.SpaceEnterResponseDTO;
import com.crewspace.api.dto.res.space.SpaceMainResponse;
import com.crewspace.api.dto.res.space.SpaceMainResponseDTO;
import com.crewspace.api.service.SpaceService;
import com.crewspace.api.utils.SecurityUtil;
import java.io.IOException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/space")
public class SpaceController {

    private final SpaceService spaceService;

    @Value("${default_image.space}")
    private String defaultSpaceImage;

    @Value("${default_image.profile}")
    private String defaultProfileImage;

    @Value("${default_image.space_banner}")
    private String defaultSpaceBanner;

    @PostMapping("")
    public ResponseEntity<CreateSpaceResponse> create(@ModelAttribute @Valid CreateSpaceRequest request) throws IOException {

        String imageURL;
        String memberEmail = SecurityUtil.getCurrentMemberId();

        // REFACTOR
        if(request.getImage() == null){
            imageURL = defaultSpaceImage;
        }else{
            imageURL = "upload with S3";
        }

        CreateSpaceResponseDTO responseDTO = spaceService.create(request.toCreateSpaceDTO(imageURL, defaultSpaceBanner, memberEmail));
        return CreateSpaceResponse.toResponse(CREATE_SPACE_SUCCESS, responseDTO);
    }

    @GetMapping("/{space-code}")
    public ResponseEntity<InvitationCodeResponse> confirmInvitationCode(@PathVariable("space-code") String spaceCode){

        InvitationCodeRequestDTO invitationCodeRequestDTO = new InvitationCodeRequestDTO(spaceCode);
        InvitationCodeResponseDTO responseDTO = spaceService.confirmInvitationCode(invitationCodeRequestDTO);

        return InvitationCodeResponse.toResponse(VALID_SPACE_CODE, responseDTO);
    }

    @GetMapping("/register-info")
    public ResponseEntity<RegisterInfoResponse> registerInfo(@Valid @RequestHeader("Space-Id") Long spaceId){
        RegisterInfoRequestDTO registerInfoRequestDTO = new RegisterInfoRequestDTO(spaceId);
        RegisterInfoResponseDTO responseDTO = spaceService.registerInfo(registerInfoRequestDTO);

        return RegisterInfoResponse.toResponse(LOAD_REGISTER_INFO_SUCCESS, responseDTO);
    }

    @PostMapping("/enter")
    public ResponseEntity<SpaceEnterResponse> enterSpace(@Valid @RequestHeader("Space-Id") Long spaceId, @Valid @ModelAttribute
        SpaceEnterRequest request){

        String imageURL;
        String memberEmail = SecurityUtil.getCurrentMemberId();

        // REFACTOR
        if(request.getImage() == null){
            imageURL = defaultProfileImage;
        }else{
            imageURL = "upload with S3";
        }
        SpaceEnterResponseDTO responseDTO = spaceService.enterSpace(
            request.toSpaceEnterDTO(spaceId, memberEmail, imageURL));

        return SpaceEnterResponse.toResponse(ENTER_SPACE_SUCEESS, responseDTO);
    }


    @GetMapping("")
    public ResponseEntity<SpaceMainResponse> spaceMain(@Valid @RequestHeader("Space-Id") Long spaceId){

        String memberEmail = SecurityUtil.getCurrentMemberId();
        SpaceMainRequestDTO requestDTO = SpaceMainRequestDTO.of(memberEmail, spaceId);

        SpaceMainResponseDTO responseDTO = spaceService.spaceMain(requestDTO);

        return SpaceMainResponse.newResponse(ENTER_SPACE_SUCEESS, responseDTO);
    }
}
