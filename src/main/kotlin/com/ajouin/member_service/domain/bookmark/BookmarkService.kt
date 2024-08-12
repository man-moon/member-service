package com.ajouin.member_service.domain.bookmark

import com.ajouin.member_service.domain.member.Member
import com.ajouin.member_service.domain.reminder.dto.BookmarkResponse
import com.ajouin.member_service.exception.BusinessException
import com.ajouin.member_service.exception.ErrorCode.DUPLICATED_VALUE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookmarkService(
    private val bookmarkRepository: BookmarkRepository,
) {

    @Transactional
    fun createBookmark(member: Member, noticeId: Long) {

        val isExist = bookmarkRepository.existsByMemberAndNoticeId(member, noticeId)
        if(isExist) {
            throw BusinessException(DUPLICATED_VALUE)
        }

        val bookmark = Bookmark(member, noticeId)
        bookmarkRepository.save(bookmark)
    }

    @Transactional
    fun deleteBookmark(member: Member, noticeId: Long) {

        val bookmark = bookmarkRepository.findByMemberAndNoticeId(member, noticeId)
        bookmarkRepository.delete(bookmark)
    }

    fun getBookmark(member: Member): List<BookmarkResponse> {
        val bookmark = bookmarkRepository.findAllByMember(member)
        return bookmark.map { BookmarkResponse(it.noticeId) }
    }
}