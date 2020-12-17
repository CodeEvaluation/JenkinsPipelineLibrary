package pipeline

import stage.CodeSpyGlass

class Pipeline {
    private final Jenkins jenkins
    private final CodeSpyGlass codeSpyGlass

    Pipeline(Jenkins jenkins, CodeSpyGlass codeSpyGlass) {
        this.codeSpyGlass = codeSpyGlass
        this.jenkins = jenkins
    }

    void run() {
        try {
            jenkins.println("--- CodeSpyGlass pipeline now starting ---")
            codeSpyGlass.run()
            jenkins.println("--- Finish: Success ---")
        } catch (Exception e) {
            jenkins.println("--- Caught an exception ---")
            jenkins.println("${e.toString()}")
            jenkins.println("--- Finish: Failure ---")
            throw e
        }
    }
}
