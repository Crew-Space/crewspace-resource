package com.crewspace.api.service;

import static com.crewspace.api.constants.ExceptionCode.SPACE_MEMBER_NOT_FOUND;

import com.crewspace.api.domain.spaceMember.MemberCategory;
import com.crewspace.api.domain.spaceMember.MemberCategoryRepository;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import com.crewspace.api.domain.spaceMember.SpaceMemberRepository;
import com.crewspace.api.dto.req.category.CategoryListRequestDTO;
import com.crewspace.api.dto.res.memberCategory.MemberCategoryListResponseDTO;
import com.crewspace.api.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberCategoryService {

    private final SpaceMemberRepository spaceMemberRepository;
    private final MemberCategoryRepository memberCategoryRepository;

    public MemberCategoryListResponseDTO categoryList(CategoryListRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        long numOfMember = spaceMemberRepository.countBySpace(spaceMember.getSpace());
        List<MemberCategory> memberCategories = memberCategoryRepository.findBySpace(spaceMember.getSpace());

        return MemberCategoryListResponseDTO.of(spaceMember, numOfMember, memberCategories);
    }

}
