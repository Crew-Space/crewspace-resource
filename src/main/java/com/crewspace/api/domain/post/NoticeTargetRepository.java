package com.crewspace.api.domain.post;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeTargetRepository extends JpaRepository<NoticeTarget, Long> {
    @Query("select n from NoticeTarget n join fetch n.target where n.noticePost = :noticePost")
    List<NoticeTarget> findByNoticePost(@Param("noticePost") NoticePost noticePost);
}
