package com.prospect.core.web

import com.prospect.core.applicationservice.project.ProjectApplicationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("project")
class ProjectController(private val projectApplicationService: ProjectApplicationService) {

    @PutMapping
    fun create(@RequestBody aCommand: CreateProjectJSONCommand) {
        projectApplicationService.create(aCommand.name)
    }

    @DeleteMapping("{projectId}")
    fun delete(@PathVariable projectId: String) {
        projectApplicationService.delete(projectId)
    }

}

data class CreateProjectJSONCommand(val name: String)
