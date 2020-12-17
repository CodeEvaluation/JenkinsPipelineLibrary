package stage.objectcalisthenics

class JavaFile {
    private final String path
    private final String content

    JavaFile(String pathToJavaFile, String javaFileContent) {
        this.path = pathToJavaFile
        this.content = javaFileContent
    }

    String className() {
        String[] split = path.split("/")
        String baseName = split[split.length - 1]
        return baseName.substring(0, baseName.length() - 5)
    }

    String toJson() {
        return "{\"className\": \"${className()}\", \"fileContent\": \"${content}\"}"
    }
}
