package stage.objectcalisthenics

import pipeline.Jenkins

class ObjectCalisthenicsStage {
    private final Jenkins jenkins
    private final JavaClassFinder finder
    private final ObjectCalisthenicsAnalyser analyser

    ObjectCalisthenicsStage(Jenkins jenkins, JavaClassFinder javaClassFinder, ObjectCalisthenicsAnalyser analyser) {
        this.jenkins = jenkins
        this.finder = javaClassFinder
        this.analyser = analyser
    }

    void run(String codeDirectoryAbsolutePath) {
        jenkins.stage("Object Calisthenics") {
            jenkins.println("codeDirectoryAbsolutePath: ${codeDirectoryAbsolutePath}")
            List<String> sourceClasses = finder.findSourceClasses(codeDirectoryAbsolutePath)
            jenkins.println("sourceClasses: ${sourceClasses.toString()}")
            jenkins.dir(codeDirectoryAbsolutePath) {
                createReport(sourceClasses)
                        .printWith(jenkins)
            }
        }
    }

    private Report createReport(List<String> sourceClasses) {
        List<ReportEntry> reportEntries = new ArrayList<ReportEntry>()
        for (String sourceClass : sourceClasses) {
            reportEntries.addAll(analyser.analyse(sourceClass))
        }
        return new Report(reportEntries)
    }
}
