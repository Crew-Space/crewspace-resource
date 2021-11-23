package com.crewspace.api.dto.req.post;

import com.crewspace.api.domain.post.CommunityPost;
import com.crewspace.api.domain.post.PostCategory;
import com.crewspace.api.domain.post.PostImage;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WriteCommunityRequestDTO {

    private Long spaceId;
    private String memberEmail;
    private Long postCategoryId;

    private List<String> images;
    private String description;

    private WriteCommunityRequestDTO(Long spaceId, String memberEmail, Long postCategoryId,
        List<String> images, String description) {
        this.spaceId = spaceId;
        this.memberEmail = memberEmail;
        this.postCategoryId = postCategoryId;
        this.images = images;
        this.description = description;
    }

    public static WriteCommunityRequestDTO of(Long spaceId, String memberEmail, Long postCategoryId,
        List<String> images, String description){
        return new WriteCommunityRequestDTO(spaceId, memberEmail, postCategoryId, images, description);
    }

    public CommunityPost toCommunityPost(SpaceMember spaceMember, PostCategory postCategory){
        return CommunityPost.builder()
            .author(spaceMember)
            .postCategory(postCategory)
            .description(description)
            .build();
    }

    public List<PostImage> toPostImages(CommunityPost communityPost){
        // 0 번째 업로드된건 썸네일이다. t/f로 판단한다. => index 판단은 stream을 쓰기가 어렵구나..
        List<PostImage> postImages = new ArrayList<>();
        for(int i = 0 ; i < images.size() ; i++){
            if(i == 0) postImages.add(PostImage.of(communityPost, true, images.get(i)));
            else postImages.add(PostImage.of(communityPost, false, images.get(i)));
        }
        return postImages;
    }
}
