package com.ajouin.member_service.exception

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String,
) {
    INVALID_INPUT_VALUE(400, "C001", "잘못된 입력이에요."),
    METHOD_NOT_ALLOWED(405, "C002", "허용되지 않은 메소드에요."),
    ENTITY_NOT_FOUND(400, "C003", "대상을 찾을 수 없어요."),
    INTERNAL_SERVER_ERROR(500, "C004", "서버 에러에요. 잠시 후, 다시 시도해주세요."),
    INVALID_TYPE_VALUE(400, "C005", "잘못된 타입이에요."),
    HANDLE_ACCESS_DENIED(403, "C006", "권한이 없어요."),
    DUPLICATED_VALUE(400, "C007", "중복된 값이에요."),
}