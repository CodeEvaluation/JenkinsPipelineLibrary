package stage.analysis;

import org.junit.jupiter.api.Test;
import pipeline.PostResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaFileFeedbackTest {
    @Test
    void canBeConstructedFromPostResponse() {
        PostResponse postResponse = new PostResponse(
                200, "{" +
                "\"fullyQualifiedClassName\": \"com.bilgin.accounting.Transaction\", " +
                "\"feedbacks\": []" +
                "}");
        assertEquals(
                "com.bilgin.accounting.Transaction: Ok",
                JavaFileFeedback.fromPostResponse(postResponse).message());
    }
}
