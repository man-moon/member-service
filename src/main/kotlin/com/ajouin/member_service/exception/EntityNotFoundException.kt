package com.ajouin.member_service.exception

import com.ajouin.member_service.exception.BusinessException

open class EntityNotFoundException(
    errorCode: ErrorCode = ErrorCode.ENTITY_NOT_FOUND
) : BusinessException(errorCode)