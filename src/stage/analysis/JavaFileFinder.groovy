package stage.analysis


import pipeline.Jenkins

class JavaFileFinder {
    private final Jenkins jenkins

    JavaFileFinder(Jenkins jenkins) {
        this.jenkins = jenkins
    }

    List<String> findSourceFilePaths(String codeDirectoryAbsolutePath) {
        String sourceRoot = sourceRoot(codeDirectoryAbsolutePath)
        jenkins.println("Identified source root: ${sourceRoot}")
        return jenkins.findJavaFiles(sourceRoot)
    }

    private String sourceRoot(String codeDirectoryAbsolutePath) {
        if (!jenkins.pathExists(codeDirectoryAbsolutePath)) {
            throw new SourceRootNotFound(new RuntimeException("Code directory " +
                    "is misconfigured. This is a bug in the pipeline code."))
        }

        String srcMain = "${codeDirectoryAbsolutePath}/src/main"
        if (jenkins.pathExists(srcMain)) {
            return srcMain
        }

        String src = "${codeDirectoryAbsolutePath}/src"
        if (jenkins.pathExists(src)) {
            return src
        }

        throw new SourceRootNotFound(new RuntimeException("Couldn't find " +
                "source root within the code directory. " +
                "Tried 'src/main', then 'src'."))
    }
}
