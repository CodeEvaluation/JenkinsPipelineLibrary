package stage.analysis

import pipeline.Jenkins

class JavaCodebaseReport {
    private final List<JavaFileFeedback> feedbacks

    JavaCodebaseReport(List<JavaFileFeedback> feedbacks) {
        this.feedbacks = feedbacks
    }

    void printWith(Jenkins jenkins) {
        StringBuilder reportTextBuilder = new StringBuilder("=== Report ===")
        for (JavaFileFeedback feedback : feedbacks) {
            reportTextBuilder.append(feedback.message())
        }
        String reportText = reportTextBuilder.toString()
        jenkins.println("${reportText}\n\n=== End of report ===")
    }
}
