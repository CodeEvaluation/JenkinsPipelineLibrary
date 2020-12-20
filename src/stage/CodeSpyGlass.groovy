package stage

import pipeline.Jenkins
import stage.clone.CloneStage
import stage.analysis.JavaClassFinder
import stage.analysis.Analyser
import stage.analysis.AnalysisStage
import stage.readme.ReadmeStage

class CodeSpyGlass {
    private final Jenkins jenkins

    CodeSpyGlass(Jenkins jenkins) {
        this.jenkins = jenkins
    }

    void run() {
        String codeDirectoryRelativePath = new CloneStage(jenkins)
                .run(jenkins.jobParameters())

        new ReadmeStage(jenkins)
                .run(codeDirectoryRelativePath)

        new AnalysisStage(jenkins, new JavaClassFinder(jenkins), new Analyser(jenkins))
                .run("${jenkins.workspace()}/${codeDirectoryRelativePath}")
    }
}
