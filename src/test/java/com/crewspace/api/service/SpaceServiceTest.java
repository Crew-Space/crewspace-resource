package com.crewspace.api.service;

import com.crewspace.api.dto.req.CreateSpaceRequestDTO;
import com.crewspace.api.dto.res.CreateSpaceResponseDTO;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class SpaceServiceTest {

    @Autowired
    SpaceService spaceService;

    @Test
    @Rollback(false)
    public void 동아리_생성() throws Exception{
        //given
        List<String> memberCategory = new ArrayList<>();
        memberCategory.add("분류1");
        memberCategory.add("분류2");

        CreateSpaceRequestDTO createSpaceDTO = CreateSpaceRequestDTO.builder()
            .memberEmail("aa9919@naver.com")
            .name("크루스페이스")
            .image("테스트 이미지")
            .description("설명설명")
            .memberCategory(memberCategory)
            .hasBirthdate(true)
            .hasContact(true)
            .hasEmail(true)
            .hasEtc(true)
            .hasSns(true)
            .build();


        //when
        CreateSpaceResponseDTO createdSpace = spaceService.create(createSpaceDTO);

        //then
        Assertions.assertThat(createdSpace.getSpaceName()).isEqualTo("크루스페이스");
    }
}