package stage.analysis


import pipeline.PostResponse

class JavaFileFeedback {
    private final String fullyQualifiedClassName
    private final List<Object> feedbacks

    JavaFileFeedback(String fullyQualifiedClassName, List<Object> feedbacks) {
        this.fullyQualifiedClassName = fullyQualifiedClassName
        this.feedbacks = feedbacks
    }

    static JavaFileFeedback fromPostResponse(PostResponse postResponse) {
        Map<String, Object> json = postResponse.parseJson()
        String fullyQualifiedClassName = json["fullyQualifiedClassName"]
        //noinspection GroovyAssignabilityCheck
        List<String> feedbacks = json["feedbacks"]
        return new JavaFileFeedback(fullyQualifiedClassName, feedbacks)
    }

    String message() {
        List<String> feedbackMessages = feedbacks as List<String>
        if (feedbackMessages.isEmpty()) {
            return "${fullyQualifiedClassName}: Ok"
        }
        return "${fullyQualifiedClassName}: ${feedbackMessages.join(". ")}"
    }
}
