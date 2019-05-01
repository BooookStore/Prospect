package com.prospect.core.web

import com.prospect.core.applicationservice.project.ProjectApplicationService
import com.prospect.core.query.ProjectCriteria
import com.prospect.core.query.ProjectQuery
import com.prospect.core.query.model.ProjectDetailModel
import com.prospect.core.query.model.ProjectOverviewModel
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("project")
class ProjectController(
        private val projectApplicationService: ProjectApplicationService,
        private val projectQuery: ProjectQuery
) {

    @GetMapping
    fun queryOverview(@RequestBody projectCriteria: ProjectCriteria): List<ProjectOverviewModel> {
        return projectQuery.find(projectCriteria)
    }

    @GetMapping("/{projectId}")
    fun queryDetail(@PathVariable projectId: String): ProjectDetailModel {
        return projectQuery.findById(projectId)
    }

    @PostMapping
    fun create(@RequestBody aCommand: CreateProjectJSONCommand) {
        projectApplicationService.create(aCommand.name)
    }

    @PutMapping("/{projectId}/name")
    fun rename(@PathVariable projectId: String, @RequestBody aCommand: RenameProjectJSONCommand) {
        projectApplicationService.rename(projectId, aCommand.name)
    }

    @DeleteMapping("/{projectId}")
    fun delete(@PathVariable projectId: String) {
        projectApplicationService.delete(projectId)
    }

}

data class CreateProjectJSONCommand(val name: String)

data class RenameProjectJSONCommand(val name: String)
