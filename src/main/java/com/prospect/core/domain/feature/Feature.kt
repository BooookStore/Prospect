package com.prospect.core.domain.feature

class Feature(
        val id: String,
        var title: String,
        var description: String,
        var point: Point?,
        var tasks: OrderedTasks = OrderedTasks()
)