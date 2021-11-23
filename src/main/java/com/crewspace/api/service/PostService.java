package com.crewspace.api.service;

import static com.crewspace.api.constants.ExceptionCode.POST_NOT_FOUND;
import static com.crewspace.api.constants.ExceptionCode.SPACE_MEMBER_NOT_FOUND;

import com.crewspace.api.domain.memberPost.FixedPostRepository;
import com.crewspace.api.domain.memberPost.SavedPostRepository;
import com.crewspace.api.domain.post.CommunityPost;
import com.crewspace.api.domain.post.NoticePost;
import com.crewspace.api.domain.post.NoticeTarget;
import com.crewspace.api.domain.post.NoticeTargetRepository;
import com.crewspace.api.domain.post.PostSupportRepository;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import com.crewspace.api.domain.spaceMember.SpaceMemberRepository;
import com.crewspace.api.dto.req.post.PostRequestDTO;
import com.crewspace.api.dto.res.post.CommunityPostDetailResponseDTO;
import com.crewspace.api.dto.res.post.NoticePostDetailResponseDTO;
import com.crewspace.api.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostSupportRepository postSupportRepository;
    private final SpaceMemberRepository spaceMemberRepository;
    private final SavedPostRepository savedPostRepository;
    private final FixedPostRepository fixedPostRepository;
    private final NoticeTargetRepository noticeTargetRepository;

    public CommunityPostDetailResponseDTO communityDetail(PostRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        // 작성자 정보 포함
        CommunityPost communityPost = postSupportRepository.findCommunityPost(request.getPostId())
            .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        Boolean isAuthor = communityPost.getAuthor().getMember().getEmail().equals(request.getMemberEmail());
        Boolean isSaved = savedPostRepository.existsByPostAndMember(communityPost, spaceMember);

        return CommunityPostDetailResponseDTO.builder()
            .post(communityPost)
            .isAuthor(isAuthor)
            .isSaved(isSaved)
            .build();
    }

    public NoticePostDetailResponseDTO noticeDetail(PostRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        NoticePost noticePost = postSupportRepository.findNoticePost(request.getPostId())
            .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        List<NoticeTarget> noticeTargets = noticeTargetRepository.findByNoticePost(noticePost);
        Boolean isAuthor = noticePost.getAuthor().getMember().getEmail().equals(request.getMemberEmail());
        Boolean isSaved = savedPostRepository.existsByPostAndMember(noticePost, spaceMember);
        Boolean isFixed = fixedPostRepository.existsByPostAndMember(noticePost, spaceMember);

        return NoticePostDetailResponseDTO.builder()
            .post(noticePost)
            .targets(noticeTargets)
            .isAuthor(isAuthor)
            .isFixed(isFixed)
            .isSaved(isSaved)
            .build();
    }

}
