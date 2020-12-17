package pipeline

class PostResponse {
    private final Object rawJenkinsPostResponse

    PostResponse(Object rawJenkinsPostResponse) {
        this.rawJenkinsPostResponse = rawJenkinsPostResponse
    }

    int statusCode() {
        return rawJenkinsPostResponse.status
    }

    String jsonBody() {
        return rawJenkinsPostResponse.content
    }

    String stringValue() {
        return "Status: ${statusCode()}\n" +
                "Body:   ${jsonBody()}"
    }
}
