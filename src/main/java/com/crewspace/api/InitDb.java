package com.crewspace.api;

import com.crewspace.api.domain.member.Member;
import com.crewspace.api.domain.member.MemberRepository;
import com.crewspace.api.domain.post.NoticePostRepository;
import com.crewspace.api.dto.req.post.WriteCommunityRequestDTO;
import com.crewspace.api.dto.req.post.WriteNoticeRequestDTO;
import com.crewspace.api.dto.req.space.CreateSpaceRequestDTO;
import com.crewspace.api.dto.req.space.SpaceEnterRequestDTO;
import com.crewspace.api.service.CommunityPostService;
import com.crewspace.api.service.NoticePostService;
import com.crewspace.api.service.SpaceService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    private final SpaceService spaceService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final SpaceService spaceService;
        private final MemberRepository memberRepository;
        private final CommunityPostService communityPostService;
        private final NoticePostService noticePostService;

        public void dbInit() {
            // 유저 생성
            Member member1 = new Member("11", "aa9919@naver.com", "testprofile", "yaewon", true);
            memberRepository.save(member1);

            Member member2 = new Member("22", "test@naver.com", "이미지", "spaceOwner", false);
            memberRepository.save(member2);

            // 동아리 생성
            List<String> memberCategory = new ArrayList<>();
            memberCategory.add("개발팀");
            CreateSpaceRequestDTO createSpaceRequest = CreateSpaceRequestDTO.builder()
                .memberEmail("aa9919@naver.com")
                .image("test img")
                .name("크루 스페이스2")
                .memberCategory(memberCategory)
                .description("크루 스페이스 설명설명")
                .hasBirthdate(false)
                .hasEmail(false)
                .hasContact(false)
                .hasSns(false)
                .hasEtc(false)
                .build();
            spaceService.create(createSpaceRequest);

            // 동아리 가입
            SpaceEnterRequestDTO spaceEnterRequest = SpaceEnterRequestDTO.builder()
                .spaceId(Long.valueOf(3))
                .memberEmail("test@naver.com")
                .profileImage("동아리 가입 이미지")
                .name("스페이서 예원")
                .description("난 예옹이야~")
                .memberCategoryId(Long.valueOf(7))
                .isAdmin(false)
                .birthdate("")
                .email("")
                .contact("")
                .sns("")
                .etc("")
                .build();
            spaceService.enterSpace(spaceEnterRequest);

            // 커뮤니티 게시글 생성
            List<String> communityImages = new ArrayList<>();
            communityImages.add("testImg1");
            communityImages.add("testImg2");
            WriteCommunityRequestDTO writeCommunityRequest = WriteCommunityRequestDTO.of(Long.valueOf(3),
                "aa9919@naver.com", Long.valueOf(5), communityImages, "설명설명");
            communityPostService.write(writeCommunityRequest);

            // 공지 게시글 생성
            List<String> noticeImages = new ArrayList<>();
            noticeImages.add("notice img1");
            noticeImages.add("notice img2");
            noticeImages.add("notice img3");
            List<Long> noticeTargets = new ArrayList<>();
            noticeTargets.add(Long.valueOf(6));

            WriteNoticeRequestDTO writeNoticeRequest = WriteNoticeRequestDTO.builder()
                .spaceId(Long.valueOf(3))
                .memberEmail("aa9919@naver.com")
                .postCategoryId(Long.valueOf(4))
                .targets(noticeTargets)
                .images(noticeImages)
                .title("공지 제목입니다")
                .description("공지 설명설명")
                .isReserved(false)
                .reservedTime(LocalDateTime.now())
                .build();
            noticePostService.write(writeNoticeRequest);

        }
    }

}