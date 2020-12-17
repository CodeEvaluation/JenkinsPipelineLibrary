package stage.objectcalisthenics

class JavaFile {
    private final String path
    private final String content

    JavaFile(String pathToJavaFile, String javaFileContent) {
        this.path = pathToJavaFile
        this.content = javaFileContent
    }

    String path() {
        return path
    }

    String content() {
        return content
    }
}
