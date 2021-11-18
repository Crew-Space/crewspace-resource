package com.crewspace.api.domain;

import java.util.Locale;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Space extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "space_id")
    private Long id;

    private String invitationCode;
    private String image;
    private String name;
    private String description;

    private Boolean hasBirthdate;
    private Boolean hasEmail;
    private Boolean hasContact;
    private Boolean hasSns;
    private Boolean hasEtc;

    // 생성 메서드
    @Builder
    public Space(String image, String name, String description, Boolean hasBirthdate,
        Boolean hasEmail, Boolean hasContact, Boolean hasSns, Boolean hasEtc) {

        // 초대 코드 랜덤 생성
        String invitationCode = UUID.randomUUID().toString().replaceAll("-", "");
        invitationCode = invitationCode.substring(0, 6).toUpperCase();

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
}
