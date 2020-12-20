package pipeline

import groovy.json.JsonSlurper

class PostResponse {
    private final int statusCode
    private final String content

    private final static JsonSlurper jsonSlurper = new JsonSlurper()

    PostResponse(int statusCode, String content) {
        this.content = content
        this.statusCode = statusCode
    }

    @SuppressWarnings(['GroovyAssignabilityCheck', 'GrUnresolvedAccess'])
    static PostResponse fromRawJenkinsHttpResponse(Object rawJenkinsPostResponse) {
        return new PostResponse(
                rawJenkinsPostResponse.status,
                rawJenkinsPostResponse.content)
    }

    @SuppressWarnings('GroovyAssignabilityCheck')
    Map<String, Object> parseJson() {
        return jsonSlurper.parseText(content)
    }

    @Override
    String toString() {
        return "Status: ${statusCode}\n" +
                "Body:   ${content}"
    }

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false
        PostResponse that = (PostResponse) o
        if (statusCode != that.statusCode) return false
        if (content != that.content) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = statusCode
        result = 31 * result + content.hashCode()
        return result
    }
}
