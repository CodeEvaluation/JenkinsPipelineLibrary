package stage.readme

import pipeline.Jenkins

import java.util.function.Function

class ReadmeStage {
    private final Jenkins jenkins

    ReadmeStage(Jenkins jenkins) {
        this.jenkins = jenkins
    }

    void run(String codeDirectoryRelativePath) {
        jenkins.stage("Readme") {
            jenkins.dir(codeDirectoryRelativePath) {
                jenkins.println("Checking README")
            }
        }
    }
}
