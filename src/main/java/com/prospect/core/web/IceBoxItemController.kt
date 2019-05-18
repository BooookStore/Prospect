package com.prospect.core.web

import com.prospect.core.applicationservice.AddIceBoxItemCommand
import com.prospect.core.applicationservice.IceBoxItemApplicationService
import com.prospect.core.domain.type.Point
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping("project/{projectId}/iceboxitem")
class IceBoxItemController(private val iceBoxItemApplicationService: IceBoxItemApplicationService) {

    @PostMapping
    fun add(@PathVariable projectId: String, @Valid @RequestBody aCommand: AddIceBoxItemCommandJSON) {
        iceBoxItemApplicationService.addIceBoxItem(AddIceBoxItemCommand(
                projectId = projectId,
                title = aCommand.title,
                description = aCommand.description,
                point = Point.of(aCommand.point)
        ))
    }

}

data class AddIceBoxItemCommandJSON(
        @field:NotBlank
        val title: String,
        @field:NotBlank
        val description: String,
        @field:Min(value = 0)
        val point: Int
)
