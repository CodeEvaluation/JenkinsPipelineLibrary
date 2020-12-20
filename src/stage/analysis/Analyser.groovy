package stage.analysis

import pipeline.Jenkins
import pipeline.PostResponse

class Analyser {
    private final Jenkins jenkins

    Analyser(Jenkins jenkins) {
        this.jenkins = jenkins
    }

    JavaFileFeedback analyse(String pathToJavaFile) {
        jenkins.println("Analyzing ${pathToJavaFile}\n")
        JavaFile javaFile = jenkins.readJavaFile(pathToJavaFile)
        PostResponse response = jenkins.post(
                "http://localhost:9000/code-analysis",
                javaFile.toJson())
        return JavaFileFeedback.fromPostResponse(response)
    }
}
