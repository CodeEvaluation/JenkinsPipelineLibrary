package stage.analysis


import pipeline.PostResponse

class JavaFileFeedback {
    private final String fullyQualifiedClassName

    JavaFileFeedback(String fullyQualifiedClassName) {
        this.fullyQualifiedClassName = fullyQualifiedClassName
    }

    static JavaFileFeedback fromPostResponse(PostResponse postResponse) {
        Map<String, Object> json = postResponse.parseJson()
        String fullyQualifiedClassName = json["fullyQualifiedClassName"]
        return new JavaFileFeedback(fullyQualifiedClassName)
    }

    String message() {
        return "${fullyQualifiedClassName}: Ok"
    }
}
