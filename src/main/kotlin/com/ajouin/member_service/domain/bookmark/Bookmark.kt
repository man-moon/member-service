package com.ajouin.member_service.domain.bookmark

import com.ajouin.member_service.domain.member.Member
import jakarta.persistence.*

@Entity
class Bookmark(
    @ManyToOne(fetch = FetchType.LAZY)
    val member: Member,
    val noticeId: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    )