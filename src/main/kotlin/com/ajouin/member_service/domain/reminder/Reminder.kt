package com.ajouin.member_service.domain.reminder

import com.ajouin.member_service.domain.member.Member
import com.ajouin.member_service.domain.reminder.Status.SCHEDULED
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
class Reminder(

    @ManyToOne(fetch = FetchType.LAZY)
    val member: Member,

    val noticeId: Long,
    val remindAt: LocalDateTime,

    @CreationTimestamp
    val createdAt: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    var status: Status = SCHEDULED,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)