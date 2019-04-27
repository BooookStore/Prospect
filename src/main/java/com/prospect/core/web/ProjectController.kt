package com.prospect.core.web

import com.prospect.core.applicationservice.project.ProjectApplicationService
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("project")
class ProjectController(private val projectApplicationService: ProjectApplicationService) {

    @PutMapping
    fun create(@RequestBody aCommand: CreateProjectJSONCommand) {
        projectApplicationService.create(aCommand.name)
    }

}

data class CreateProjectJSONCommand(val name: String)
