package com.ajouin.member_service.domain.reminder

import com.ajouin.member_service.exception.BusinessException
import com.ajouin.member_service.exception.ErrorCode
import com.ajouin.member_service.logger
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService (
    private val reminderRepository: ReminderRepository,
    private val mailSender: JavaMailSender
) {

    fun sendReminder(reminder: Reminder) {
        val message = buildEmailMessage(
            subject = "[아주인] 공지사항 리마인드 안내",
            content = "Reminder for notice ${reminder.noticeId}",
            email = reminder.member.email
        )
        sendEmail(message)

        reminder.status = Status.COMPLETED
        reminderRepository.save(reminder)

        logger.info{ "Sending reminder for notice ${reminder.noticeId} to user ${reminder.member.email}" }
    }

    private fun buildEmailMessage(subject: String, content: String, email: String): SimpleMailMessage {
        val message = SimpleMailMessage()
        message.from = "아주인 <admin@ajou.in>"
        message.setTo(email)
        message.subject = subject
        message.text = content
        return message
    }

    private fun sendEmail(message: SimpleMailMessage) {
        try {
            mailSender.send(message)
        } catch (e: Exception) {
            throw BusinessException(ErrorCode.INTERNAL_SERVER_ERROR)
        }
    }
}