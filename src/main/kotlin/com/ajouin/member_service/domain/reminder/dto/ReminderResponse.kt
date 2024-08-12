package com.ajouin.member_service.domain.reminder.dto

import com.ajouin.member_service.domain.reminder.Status

data class ReminderResponse(
    val noticeId: Long,
    val status: Status
)