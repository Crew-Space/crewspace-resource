package com.crewspace.api.controller.v1;

import static com.crewspace.api.constants.SuccessCode.CREATE_COMMENT_SUCCESS;
import static com.crewspace.api.constants.SuccessCode.LOAD_COMMENT_LIST_SUCCESS;

import com.crewspace.api.dto.req.comment.CommentListRequestDTO;
import com.crewspace.api.dto.req.comment.WriteCommentRequest;
import com.crewspace.api.dto.req.comment.WriteCommentRequestDTO;
import com.crewspace.api.dto.res.comment.CommentListResponse;
import com.crewspace.api.dto.res.comment.CommentListResponseDTO;
import com.crewspace.api.dto.res.comment.WriteCommentResponse;
import com.crewspace.api.service.CommentService;
import com.crewspace.api.utils.SecurityUtil;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/v1/{post-id}/comments")
    public ResponseEntity<CommentListResponse> commentList(@Valid @RequestHeader("Space-Id") Long spaceId,
        @PathVariable(value = "post-id") Long postId,
        @RequestParam(value = "offset", defaultValue = "0", required = false) int offset){

        String memberEmail = SecurityUtil.getCurrentMemberId();
        CommentListRequestDTO requestDTO = CommentListRequestDTO.of(offset, memberEmail, spaceId, postId);
        CommentListResponseDTO responseDTO = commentService.commentList(requestDTO);

        return CommentListResponse.newResponse(LOAD_COMMENT_LIST_SUCCESS, responseDTO);
    }

}
