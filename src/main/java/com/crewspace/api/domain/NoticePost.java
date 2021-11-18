package com.crewspace.api.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class NoticePost extends Post{

    private String title;
    private Boolean isShowed;
    private LocalDateTime reservedTime;

    @OneToMany(mappedBy = "noticePost", cascade = CascadeType.ALL)
    private List<NoticeTarget> targets = new ArrayList<>();
}
