package pipeline

import stage.CodeEvaluation

class Pipeline {
    private final Jenkins jenkins
    private final CodeEvaluation codeEvaluation

    Pipeline(Jenkins jenkins, CodeEvaluation codeEvaluation) {
        this.codeEvaluation = codeEvaluation
        this.jenkins = jenkins
    }

    void run() {
        try {
            jenkins.println("--- CodeEvaluation pipeline now starting ---")
            codeEvaluation.run()
            jenkins.println("--- Finish: Success ---")
        } catch (Exception e) {
            jenkins.println("--- Caught an exception ---")
            jenkins.println("${e.toString()}")
            jenkins.println("--- Finish: Failure ---")
            throw e
        }
    }
}
