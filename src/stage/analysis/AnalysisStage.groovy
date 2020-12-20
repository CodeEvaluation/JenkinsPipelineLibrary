package stage.analysis

import pipeline.Jenkins

class AnalysisStage {
    private final Jenkins jenkins
    private final JavaFileFinder finder
    private final Analyser analyser

    AnalysisStage(Jenkins jenkins, JavaFileFinder javaClassFinder, Analyser analyser) {
        this.jenkins = jenkins
        this.finder = javaClassFinder
        this.analyser = analyser
    }

    void run(String codeDirectoryAbsolutePath) {
        jenkins.stage("Analysis") {
            jenkins.println("codeDirectoryAbsolutePath: ${codeDirectoryAbsolutePath}")
            List<String> sourceClasses = finder.findSourceFilePaths(codeDirectoryAbsolutePath)
            jenkins.println("sourceClasses: ${sourceClasses.toString()}")
            jenkins.dir(codeDirectoryAbsolutePath) {
                createReport(sourceClasses)
                        .printWith(jenkins)
            }
        }
    }

    private JavaCodebaseReport createReport(List<String> sourceClasses) {
        List<JavaFileFeedback> fileFeedbacks = new ArrayList<JavaFileFeedback>()
        for (String sourceClass : sourceClasses) {
            fileFeedbacks.add(analyser.analyse(sourceClass))
        }
        return new JavaCodebaseReport(fileFeedbacks)
    }
}
