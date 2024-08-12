package com.ajouin.member_service.domain.reminder

import com.ajouin.member_service.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReminderRepository: JpaRepository<Reminder, Long> {
    fun findAllByStatus(status: Status): List<Reminder>
    fun existsByMemberAndNoticeIdAndStatus(member: Member, noticeId: Long, status: Status): Boolean
    fun findAllByMemberAndStatus(member: Member, status: Status): List<Reminder>
    fun findByMemberAndNoticeIdAndStatus(member: Member, noticeId: Long, status: Status): Reminder?
}