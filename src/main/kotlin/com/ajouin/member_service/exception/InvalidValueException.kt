package com.ajouin.member_service.exception

open class InvalidValueException(
    errorCode: ErrorCode = ErrorCode.INVALID_INPUT_VALUE
): BusinessException(errorCode)