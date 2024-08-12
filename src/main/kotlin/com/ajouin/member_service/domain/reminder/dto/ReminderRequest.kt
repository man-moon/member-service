package com.ajouin.member_service.domain.reminder.dto

import java.time.LocalDateTime

data class ReminderRequest(
    val noticeId: Long,
    val remindAt: LocalDateTime
)