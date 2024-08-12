package com.ajouin.member_service.exception

open class HandleAccessException(
        errorCode: ErrorCode = ErrorCode.HANDLE_ACCESS_DENIED
) : BusinessException(errorCode)