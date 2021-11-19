package com.crewspace.api.controller.v1;

import com.crewspace.api.constants.SuccessCode;
import com.crewspace.api.dto.req.CreateSpaceRequest;
import com.crewspace.api.dto.res.CreateSpaceResponse;
import com.crewspace.api.dto.res.CreateSpaceResponseDTO;
import com.crewspace.api.service.SpaceService;
import com.crewspace.api.utils.SecurityUtil;
import java.io.IOException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class SpaceController {

    private final SpaceService spaceService;

    @PostMapping("/space")
    public ResponseEntity<CreateSpaceResponse> create(@ModelAttribute @Valid CreateSpaceRequest request) throws IOException {

        String imageURL;
        String memberEmail = SecurityUtil.getCurrentMemberId();

        // REFACTOR
        if(request.getImage() == null){
            imageURL = "default image!";
        }else{
            imageURL = "upload with S3";
        }

        CreateSpaceResponseDTO responseDTO = spaceService.create(request.toCreateSpaceDTO(imageURL, memberEmail));
        return CreateSpaceResponse.toResponse(SuccessCode.CREATE_SPACE_SUCCESS, responseDTO);
    }
}
