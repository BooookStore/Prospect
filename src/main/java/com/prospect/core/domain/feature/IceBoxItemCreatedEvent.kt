package com.prospect.core.domain.feature

import com.prospect.core.domain.common.DomainEvent

class IceBoxItemCreatedEvent(
        val projectId: String,
        val featureId: String
) : DomainEvent()