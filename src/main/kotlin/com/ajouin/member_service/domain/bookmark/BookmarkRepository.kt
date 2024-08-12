package com.ajouin.member_service.domain.bookmark

import com.ajouin.member_service.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookmarkRepository: JpaRepository<Bookmark, Long> {

    fun findAllByMember(member: Member): List<Bookmark>

    fun existsByMemberAndNoticeId(member: Member, noticeId: Long): Boolean

    fun findByMemberAndNoticeId(member: Member, noticeId: Long): Bookmark
}