package com.selesse.gradle.git.changelog

import com.selesse.gradle.git.changelog.tasks.GenerateChangelogTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.JavaPlugin

class GitLogPlugin implements Plugin<Project> {
    Logger logger = Logging.getLogger(GitLogPlugin)

    @Override
    void apply(Project project) {
        GenerateChangelogTask task = project.tasks.create('generateChangelog', GenerateChangelogTask)
        GitChangelogExtension extension = project.extensions.create('changelog', GitChangelogExtension)

        project.afterEvaluate({
            def outputDirectoryWasSpecified = project.changelog.outputDirectory != null
            extension.with {
                title = project.changelog.title ?: project.name
                outputDirectory = project.changelog.outputDirectory ?: project.buildDir
            }

            logger.info("Initialized with settings: ${extension}")
        })

    }
}
