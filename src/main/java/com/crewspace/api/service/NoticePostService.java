package com.crewspace.api.service;

import static com.crewspace.api.constants.ExceptionCode.POST_CATEGORY_NOT_FOUND;
import static com.crewspace.api.constants.ExceptionCode.SPACE_ADMIN_ONLY;
import static com.crewspace.api.constants.ExceptionCode.SPACE_MEMBER_NOT_FOUND;

import com.crewspace.api.domain.post.NoticePost;
import com.crewspace.api.domain.post.NoticePostRepository;
import com.crewspace.api.domain.post.NoticeTarget;
import com.crewspace.api.domain.post.NoticeTargetRepository;
import com.crewspace.api.domain.post.PostCategory;
import com.crewspace.api.domain.post.PostCategoryRepository;
import com.crewspace.api.domain.post.PostImage;
import com.crewspace.api.domain.post.PostImageRepository;
import com.crewspace.api.domain.spaceMember.MemberCategory;
import com.crewspace.api.domain.spaceMember.MemberCategoryRepository;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import com.crewspace.api.domain.spaceMember.SpaceMemberRepository;
import com.crewspace.api.dto.req.post.WriteNoticeRequestDTO;
import com.crewspace.api.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticePostService {
    private final NoticePostRepository noticePostRepository;

    private final PostCategoryRepository postCategoryRepository;
    private final MemberCategoryRepository memberCategoryRepository;

    private final SpaceMemberRepository spaceMemberRepository;
    private final PostImageRepository postImageRepository;
    private final NoticeTargetRepository noticeTargetRepository;

    @Transactional
    public void write(WriteNoticeRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        PostCategory postCategory = postCategoryRepository.findById(request.getPostCategoryId())
            .orElseThrow(() -> new CustomException(POST_CATEGORY_NOT_FOUND));

        if(!postCategory.getIsNotice()){
            throw new CustomException(POST_CATEGORY_NOT_FOUND);
        }

        if(!spaceMember.getIsAdmin()){
            throw new CustomException(SPACE_ADMIN_ONLY);
        }

        // 공지 게시글 저장
        NoticePost noticePost = request.toNoticePost(spaceMember, postCategory);
        noticePostRepository.save(noticePost);

        // 게시글 이미지 저장
        List<PostImage> postImages = request.toPostImages(noticePost);
        postImageRepository.saveAll(postImages);


        // 멤버 카테고리 가져오기
        List<MemberCategory> targets = memberCategoryRepository.findByIds(request.getTargets());
        List<NoticeTarget> noticeTargets = request.toNoticeTarget(noticePost, targets);
        noticeTargetRepository.saveAll(noticeTargets);

        return;
    }
}
