package com.prospect.core.web

import com.prospect.core.applicationservice.project.ProjectApplicationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("project")
class ProjectController(private val projectApplicationService: ProjectApplicationService) {

    @PostMapping
    fun create(@RequestBody aCommand: CreateProjectJSONCommand) {
        projectApplicationService.create(aCommand.name)
    }

    @PutMapping("{projectId}/name")
    fun rename(@PathVariable projectId: String, @RequestBody aCommand: RenameProjectJSONCommand) {
        projectApplicationService.rename(projectId, aCommand.name)
    }

    @DeleteMapping("{projectId}")
    fun delete(@PathVariable projectId: String) {
        projectApplicationService.delete(projectId)
    }

}

data class CreateProjectJSONCommand(val name: String)

data class RenameProjectJSONCommand(val name: String)
