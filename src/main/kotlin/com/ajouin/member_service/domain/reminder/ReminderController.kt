package com.ajouin.member_service.domain.reminder

import com.ajouin.member_service.domain.member.MemberService
import com.ajouin.member_service.domain.reminder.dto.ReminderRequest
import com.ajouin.member_service.domain.reminder.dto.ReminderResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reminder")
class ReminderController(
    private val reminderService: ReminderService,
    private val memberService: MemberService
) {

    @PostMapping
    fun createReminder(
        @RequestBody request: ReminderRequest,
        @RequestHeader("User-Email", required = true) email: String,
    ): ResponseEntity<ReminderResponse> {

        val member = memberService.getMemberOrElseCreate(email)
        val reminder = reminderService.createReminder(member, request)

        return ResponseEntity.ok(reminder)
    }

    @DeleteMapping
    fun deleteReminder(
        @RequestParam noticeId: Long,
        @RequestHeader("User-Email", required = true) email: String,
    ): ResponseEntity<ReminderResponse> {
        val member = memberService.getMember(email)
        reminderService.deleteReminder(member, noticeId)

        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun getReminder(
        @RequestHeader("User-Email", required = true) email: String
    ): List<ReminderResponse> {

        val member = memberService.getMemberOrElseCreate(email)
        return reminderService.getReminder(member)

    }
}