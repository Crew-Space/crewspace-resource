package com.crewspace.api.domain.space;

import com.crewspace.api.domain.BaseTimeEntity;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@NoArgsConstructor
@Entity
public class Space extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "space_id")
    private Long id;

    private String invitationCode;
    private String image;
    private String bannerImage;
    private String name;
    private String description;

    private Boolean hasBirthdate;
    private Boolean hasEmail;
    private Boolean hasContact;
    private Boolean hasSns;
    private Boolean hasEtc;

    // 생성 메서드
    @Builder
    public Space(String image, String bannerImage, String name, String description, Boolean hasBirthdate,
        Boolean hasEmail, Boolean hasContact, Boolean hasSns, Boolean hasEtc) {

        // 초대 코드 랜덤 생성
        String invitationCode = UUID.randomUUID().toString().replaceAll("-", "");
        invitationCode = invitationCode.substring(0, 6).toUpperCase();
        this.bannerImage = bannerImage;

        this.invitationCode = invitationCode;

        this.image = image;
        this.name = name;
        this.description = description;

        this.hasBirthdate = hasBirthdate;
        this.hasEmail = hasEmail;
        this.hasContact = hasContact;
        this.hasSns = hasSns;
        this.hasEtc = hasEtc;
    }

    // 배너 업데이트 메서드
    public void updateBanner(String bannerImage){
        this.bannerImage = bannerImage;
    }
}
