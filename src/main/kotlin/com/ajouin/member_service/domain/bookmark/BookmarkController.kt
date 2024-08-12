package com.ajouin.member_service.domain.bookmark

import com.ajouin.member_service.domain.member.MemberService
import com.ajouin.member_service.domain.reminder.dto.BookmarkResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/bookmark")
class BookmarkController(
    private val bookmarkService: BookmarkService,
    private val memberService: MemberService,
) {

    // 북마크 등록
    @PostMapping
    fun createBookmark(
        @RequestHeader("User-Email", required = true) email: String,
        @RequestParam noticeId: Long,
    ): ResponseEntity<Any> {

        val member = memberService.getMemberOrElseCreate(email)
        bookmarkService.createBookmark(member, noticeId)

        return ResponseEntity.ok().build()
    }

    // 북마크 해제
    @DeleteMapping
    fun deleteBookmark(
        @RequestHeader("User-Email", required = true) email: String,
        @RequestParam noticeId: Long,
    ): ResponseEntity<Any> {

        val member = memberService.getMember(email)
        bookmarkService.deleteBookmark(member, noticeId)

        return ResponseEntity.ok().build()
    }


    // 북마크 목록
    @GetMapping
    fun getBookmark(
        @RequestHeader("User-Email", required = true) email: String
    ): List<BookmarkResponse> {
        val member = memberService.getMemberOrElseCreate(email)
        return bookmarkService.getBookmark(member)
    }

}