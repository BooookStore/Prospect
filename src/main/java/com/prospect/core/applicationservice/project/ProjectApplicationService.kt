package com.prospect.core.applicationservice.project

import com.prospect.core.domain.common.nexIdentify
import com.prospect.core.domain.project.Project
import com.prospect.core.domain.project.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectApplicationService(private val projectRepository: ProjectRepository) {

    fun create(name: String) {
        val aNewProject = Project(nexIdentify(), name)
        projectRepository.save(aNewProject)
    }

    fun delete(id: String) {
        val aRemoveTargetProject = findNotNullProject(id)
        projectRepository.remove(aRemoveTargetProject)
    }

    fun rename(id: String, anNewName: String) {
        val aProject = findNotNullProject(id)
        aProject.name = anNewName
        projectRepository.save(aProject)
    }

    private fun findNotNullProject(id: String): Project {
        return projectRepository.findById(id) ?: throw IllegalArgumentException("存在しないProjectです")
    }

}