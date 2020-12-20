package pipeline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostResponseTest {
    @Test
    void canBeConstructedFromRawPostResponse() {
        Object rawJenkinsResponse = new Object() {
            @SuppressWarnings("unused")
            public final int status = 200;
            @SuppressWarnings("unused")
            public final String content = "{}";
        };
        assertEquals(
                new PostResponse(200, "{}"),
                PostResponse.fromRawJenkinsHttpResponse(rawJenkinsResponse));
    }
}
