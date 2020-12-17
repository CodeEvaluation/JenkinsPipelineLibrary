package stage.clone

import pipeline.Jenkins
import pipeline.JobParameters

import java.util.function.Function

class CloneStage {
    private final Jenkins jenkins

    CloneStage(Jenkins jenkins) {
        this.jenkins = jenkins
    }

    String run(JobParameters jobParameters) {
        jenkins.stage("Clone") {
            // Expected output is "git version 2.11.0"
            jenkins.sh("git --version")

            jenkins.sh("rm -rf code")
            jenkins.sh("git clone ${jobParameters.githubUrl} code")
            jenkins.sh("ls -al code")
        }
        return "code"
    }
}
