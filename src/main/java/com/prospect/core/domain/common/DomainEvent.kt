package com.prospect.core.domain.common

import java.time.LocalDateTime
import java.time.ZoneId

open class DomainEvent(val occurredOn: LocalDateTime = LocalDateTime.now(ZoneId.of("Asia/Tokyo")))