package stage

import pipeline.Jenkins
import stage.clone.CloneStage
import stage.objectcalisthenics.JavaClassFinder
import stage.objectcalisthenics.ObjectCalisthenicsAnalyser
import stage.objectcalisthenics.ObjectCalisthenicsStage
import stage.readme.ReadmeStage

class CodeEvaluation {
    private final Jenkins jenkins

    CodeEvaluation(Jenkins jenkins) {
        this.jenkins = jenkins
    }

    void run() {
        String codeDirectoryRelativePath = new CloneStage(jenkins)
                .run(jenkins.jobParameters())

        new ReadmeStage(jenkins)
                .run(codeDirectoryRelativePath)

        new ObjectCalisthenicsStage(jenkins, new JavaClassFinder(jenkins), new ObjectCalisthenicsAnalyser(jenkins))
                .run("${jenkins.workspace()}/${codeDirectoryRelativePath}")
    }
}
