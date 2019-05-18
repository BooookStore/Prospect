package com.prospect.core.web

import com.prospect.core.applicationservice.AddProductBacklogItemCommand
import com.prospect.core.applicationservice.ProductBacklogItemApplicationService
import com.prospect.core.domain.feature.Point
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("project/{projectId}/productbacklogitem")
class ProductBacklogItemController(private val productBacklogItemApplicationService: ProductBacklogItemApplicationService) {

    @PostMapping
    fun add(@PathVariable projectId: String, @Valid @RequestBody aCommand: AddProductBacklogItemCommandJSON) {
        productBacklogItemApplicationService.addProductBacklogItem(AddProductBacklogItemCommand(
                projectId = aCommand.projectId,
                title = aCommand.title,
                description = aCommand.description,
                point = aCommand.point()
        ))
    }

}

data class AddProductBacklogItemCommandJSON(
        @field:NotBlank
        val projectId: String,
        @field:NotBlank
        val title: String,
        @field:NotNull
        val description: String,
        @field:Min(value = -1)
        val point: Int
) {

    fun point(): Point? = if (point == -1) null else Point.of(point)

}