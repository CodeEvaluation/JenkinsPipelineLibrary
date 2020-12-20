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

        // Make a POST request to the server to parse and analyse the file
        PostResponse response = jenkins.post(
                "http://localhost:9000/code-analysis",
                javaFile.toJson())
        jenkins.println(response.toString())
        // Create report entries from the response

        return new JavaFileFeedback()
    }
}
