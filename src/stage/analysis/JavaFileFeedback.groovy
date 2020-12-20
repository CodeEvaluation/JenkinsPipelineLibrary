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

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false
        JavaFileFeedback feedback = (JavaFileFeedback) o
        if (feedbacks != feedback.feedbacks) return false
        if (fullyQualifiedClassName != feedback.fullyQualifiedClassName) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = fullyQualifiedClassName.hashCode()
        result = 31 * result + feedbacks.hashCode()
        return result
    }
}
