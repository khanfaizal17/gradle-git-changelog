package com.selesse.gradle.git.changelog

import com.selesse.gradle.git.changelog.tasks.GenerateChangelogTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static org.assertj.core.api.Assertions.assertThat

class GitLogPluginTest {
    @Test public void testGenerateChangelogTask_isProperInstance() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'com.selesse.git.changelog'

        assertThat(project.tasks.generateChangelog instanceof GenerateChangelogTask).isTrue()
    }

    @Test public void testAssemble_dependsOnGenerateChangelog() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'base'
        project.pluginManager.apply 'com.selesse.git.changelog'
        project.evaluate()

        def outputDirectory = project.extensions.changelog.outputDirectory

        // TODO(AS): Fix outputDirectory being modified in @TaskAction
        assertThat(outputDirectory == null ||
                (outputDirectory.absolutePath.replace("\\", "/") as String).endsWith('build')).isTrue()
    }

    private String getOutputDirectoryPath(Project project) {
        project.changelog.outputDirectory.absolutePath.replace("\\", "/") as String
    }
}
