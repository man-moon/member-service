package com.ajouin.member_service.domain.member

import com.ajouin.member_service.exception.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {

    fun getMember(email: String): Member {
        return memberRepository.findByEmail(email) ?: throw EntityNotFoundException()
    }

    @Transactional
    fun getMemberOrElseCreate(email: String): Member {
        val member = memberRepository.findByEmail(email) ?:
            memberRepository.save(
                Member(
                    email = email
                )
            )

        return member

    }
}