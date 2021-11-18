package com.crewspace.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
}
