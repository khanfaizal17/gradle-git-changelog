package com.selesse.gradle.git.changelog.functional

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class GitLogJavaFunctionalTest extends Specification {
    @Rule final TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')
    }

    def "when using java plugin, and output directory is specified, it can be customized"() {
        given:
        buildFile << """
            plugins {
                id 'java'
                id 'com.selesse.git.changelog'
            }

            changelog {
                outputDirectory = file(project.rootDir)
            }
        """

        when:
        def result = ProjectRunner.buildGenerateChangelog(testProjectDir.root)

        then:
        new File(testProjectDir.root, "CHANGELOG.md").isFile()
        result.task(":generateChangelog").outcome == SUCCESS
    }
}
