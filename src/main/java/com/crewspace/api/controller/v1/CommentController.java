package com.crewspace.api.controller.v1;

import static com.crewspace.api.constants.SuccessCode.CREATE_COMMENT_SUCCESS;

import com.crewspace.api.dto.req.comment.WriteCommentRequest;
import com.crewspace.api.dto.req.comment.WriteCommentRequestDTO;
import com.crewspace.api.dto.res.comment.WriteCommentResponse;
import com.crewspace.api.service.CommentService;
import com.crewspace.api.utils.SecurityUtil;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/v1/comment")
    public ResponseEntity<WriteCommentResponse> create(@Valid @RequestHeader("Space-Id") Long spaceId,
        @Valid @RequestBody WriteCommentRequest request){
        String memberEmail = SecurityUtil.getCurrentMemberId();

        WriteCommentRequestDTO requestDTO = request.toWriteCommentRequestDTO(spaceId, memberEmail);
        commentService.create(requestDTO);

        return WriteCommentResponse.newResponse(CREATE_COMMENT_SUCCESS);
    }

}
