package stage.objectcalisthenics

import pipeline.Jenkins
import pipeline.PostResponse

class ObjectCalisthenicsAnalyser {
    private final Jenkins jenkins

    ObjectCalisthenicsAnalyser(Jenkins jenkins) {
        this.jenkins = jenkins
    }

    List<ReportEntry> analyse(String pathToJavaFile) {
        jenkins.println("Analyzing ${pathToJavaFile}\n")
        JavaFile javaFile = jenkins.readJavaFile(pathToJavaFile)

        // Make a POST request to the server to parse and analyse the file
        PostResponse response = jenkins.post(
                "http://localhost:9000/object-calisthenics-report",
                javaFile.toJson())
        jenkins.println(response.stringValue())
        // Create report entries from the response

        return Collections.emptyList()
    }
}
