package com.prospect.core.domain.common

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.stereotype.Component

@Component
class DomainEventPublisher : ApplicationEventPublisherAware {

    companion object {

        lateinit var publisher: ApplicationEventPublisher

        fun publish(domainEvent: DomainEvent) = publisher.publishEvent(domainEvent)

    }

    override fun setApplicationEventPublisher(applicationEventPublisher: ApplicationEventPublisher) {
        publisher = applicationEventPublisher
    }


}