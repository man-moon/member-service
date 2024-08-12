package com.ajouin.member_service.domain.reminder

import com.ajouin.member_service.domain.reminder.Status.*
import com.ajouin.member_service.logger
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime

@Component
class ReminderInitializer(
    private val reminderRepository: ReminderRepository,
    private val taskScheduler: TaskScheduler,
    private val emailService: EmailService,
) : ApplicationListener<ApplicationReadyEvent> {


    @Transactional
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        val reminders = reminderRepository.findAllByStatus(SCHEDULED)
        reminders.forEach { reminder ->
            scheduleReminder(reminder)
        }
    }

    private fun scheduleReminder(reminder: Reminder) {
        val task = Runnable {
            sendReminder(reminder)
        }
        val delay = Duration.between(LocalDateTime.now(), reminder.remindAt)
        if (delay.isNegative || delay.isZero) {
            sendReminder(reminder) // 시간이 이미 지났다면 바로 실행
        } else {
            taskScheduler.schedule(task, Instant.now().plus(delay))
        }
    }

    fun sendReminder(reminder: Reminder) {
        emailService.sendReminder(reminder)
    }
}
