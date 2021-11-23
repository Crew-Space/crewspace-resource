package com.crewspace.api;

import com.crewspace.api.domain.member.Member;
import com.crewspace.api.domain.member.MemberRepository;
import com.crewspace.api.dto.req.space.CreateSpaceRequestDTO;
import com.crewspace.api.dto.req.space.SpaceEnterRequestDTO;
import com.crewspace.api.service.SpaceService;
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
        public void dbInit() {
            // 유저 생성
            Member member1 = new Member("11", "aa9919@naver.com", "testprofile", "yaewon", true);
            memberRepository.save(member1);

            Member member2 = new Member("22", "test@naver.com", "이미지", "spaceOwner", false);
            memberRepository.save(member2);

            // 동아리 생성
            List<String> memberCategory = new ArrayList<>();
            memberCategory.add("테스트 멤버 카테고리 - 일반");
            CreateSpaceRequestDTO createSpaceRequest = CreateSpaceRequestDTO.builder()
                .memberEmail("test@naver.com")
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
                .memberEmail("aa9919@naver.com")
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
 

        }
    }

}