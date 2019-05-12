package com.prospect.core.domain.feature

import com.prospect.core.domain.common.DomainEvent
import com.prospect.core.domain.type.Priority

class ProductBacklogItemCreatedEvent(
        val projectId: String,
        val featureId: String,
        val priority: Priority
) : DomainEvent()
