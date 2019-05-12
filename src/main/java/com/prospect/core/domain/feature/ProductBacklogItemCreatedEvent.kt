package com.prospect.core.domain.feature

import com.prospect.core.domain.common.DomainEvent

class ProductBacklogItemCreatedEvent(
        val projectId: String,
        val featureId: String
) : DomainEvent()
