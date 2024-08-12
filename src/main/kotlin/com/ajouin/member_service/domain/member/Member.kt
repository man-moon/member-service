package com.ajouin.member_service.domain.member

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
class Member(

    @Column(nullable = false, unique = true)
    val email: String,

    @CreationTimestamp
    val createdAt: LocalDateTime? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)