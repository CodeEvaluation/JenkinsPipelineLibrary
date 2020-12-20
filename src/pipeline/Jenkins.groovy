package pipeline

import stage.analysis.JavaFile

class Jenkins {
    private final Object jenkins

    Jenkins(Object jenkins) {
        this.jenkins = jenkins
    }

    void node(Closure closure) {
        jenkins.node(closure)
    }

    void stage(String name, Closure closure) {
        println("--- Stage: ${name} ---")
        jenkins.stage(name, closure)
    }

    void println(String text) {
        jenkins.echo(text)
    }

    void sh(String script) {
        jenkins.sh(script)
    }

    @SuppressWarnings('GroovyAssignabilityCheck')
    JobParameters jobParameters() {
        return new JobParameters(jenkins.params.GitHubLink)
    }

    void dir(String directoryRelativePath, Closure closure) {
        jenkins.dir(directoryRelativePath, closure)
    }

    String workspace() {
        return jenkins.env.WORKSPACE
    }

    boolean pathExists(String path) {
        return jenkins.fileExists(path)
    }

    @SuppressWarnings('GroovyAssignabilityCheck')
    List<String> findJavaFiles(String path) {
        List<String> files = new ArrayList<>()
        dir(path) {
            // The real type returned is FileWrapper.
            // JavaDoc here: https://javadoc.jenkins.io/plugin/pipeline-utility-steps/org/jenkinsci/plugins/pipeline/utility/steps/fs/FileWrapper.html
            List<Object> foundFiles = jenkins.findFiles(glob: "**/*.java")
            for (Object fileWrapper : foundFiles) {
                files.add("${path}/${fileWrapper.getPath()}")
            }
        }
        return files
    }

    String readFile(String path) {
        return jenkins.readFile(path)
    }

    PostResponse post(String url, String body) {
        return new PostResponse(jenkins.httpRequest(url: url, httpMode: 'POST', requestBody: body))
    }

    JavaFile readJavaFile(String pathToJavaFile) {
        assert pathExists(pathToJavaFile)
        return new JavaFile(pathToJavaFile, readFile(pathToJavaFile))
    }
}
