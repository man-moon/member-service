package com.ajouin.member_service.domain.reminder

import com.ajouin.member_service.domain.member.Member
import com.ajouin.member_service.domain.member.MemberRepository
import com.ajouin.member_service.domain.reminder.Status.COMPLETED
import com.ajouin.member_service.domain.reminder.Status.SCHEDULED
import com.ajouin.member_service.domain.reminder.dto.ReminderRequest
import com.ajouin.member_service.domain.reminder.dto.ReminderResponse
import com.ajouin.member_service.exception.BusinessException
import com.ajouin.member_service.exception.EntityNotFoundException
import com.ajouin.member_service.exception.ErrorCode
import com.ajouin.member_service.exception.ErrorCode.DUPLICATED_VALUE
import com.ajouin.member_service.logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime

@Service
class ReminderService(
    private val reminderRepository: ReminderRepository,
    private val taskScheduler: TaskScheduler,
    private val emailService: EmailService,
) {

    fun createReminder(member: Member, request: ReminderRequest): ReminderResponse {

        // 기존에 스케줄된 리마인더가 등록되어있는지 확인
        val isExist = reminderRepository.existsByMemberAndNoticeIdAndStatus(member, request.noticeId, SCHEDULED)
        if(isExist) {
            throw BusinessException(DUPLICATED_VALUE)
        }

        val reminder = Reminder(
            member = member,
            noticeId = request.noticeId,
            remindAt = request.remindAt,
            status = SCHEDULED
        )
        val savedReminder = reminderRepository.save(reminder)

        // 스케줄러에 리마인드 작업 등록
        scheduleReminder(savedReminder)

        return ReminderResponse(
            noticeId = savedReminder.noticeId,
            status = SCHEDULED
        )
    }

    @Transactional
    fun deleteReminder(member: Member, noticeId: Long) {
        val reminder: Reminder = reminderRepository.findByMemberAndNoticeIdAndStatus(member, noticeId, SCHEDULED)
                ?: throw EntityNotFoundException()

        reminderRepository.delete(reminder)
    }

    fun getReminder(member: Member): List<ReminderResponse> {
        val reminder = reminderRepository.findAllByMemberAndStatus(member, SCHEDULED)
        return reminder.map {
            ReminderResponse(
                noticeId = it.noticeId,
                status = it.status,
            )
        }

    }

    private fun scheduleReminder(reminder: Reminder) {
        val task = Runnable {
            emailService.sendReminder(reminder)
        }
        val delay = Duration.between(LocalDateTime.now(), reminder.remindAt)
        taskScheduler.schedule(task, Instant.now().plus(delay))
    }
}