package com.ajouin.member_service.exception

open class BusinessException(val errorCode: ErrorCode = ErrorCode.INTERNAL_SERVER_ERROR): RuntimeException()
