package com.prospect.core.web

import com.prospect.core.applicationservice.FeatureAddCommand
import com.prospect.core.applicationservice.FeatureApplicationService
import com.prospect.core.domain.type.Point
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping("project/{projectId}/iceboxitem")
class IceBoxItemController(private val featureApplicationService: FeatureApplicationService) {

    @PostMapping
    fun add(@PathVariable projectId: String, @Valid @RequestBody aCommand: AddIceBoxItemCommand) {
        featureApplicationService.addIceBoxItem(FeatureAddCommand(
                projectId = projectId,
                title = aCommand.title,
                description = aCommand.description,
                point = Point.of(aCommand.point)
        ))
    }

}

data class AddIceBoxItemCommand(
        @field:NotBlank
        val title: String,
        @field:NotBlank
        val description: String,
        @field:Min(value = 0)
        val point: Int
)
