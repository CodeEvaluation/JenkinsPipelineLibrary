package stage.analysis;

import org.junit.jupiter.api.Test;
import pipeline.PostResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaFileFeedbackTest {
    @Test
    void separatesTheFeedbacksByFullStops() {
        PostResponse postResponse = new PostResponse(
                200, "{" +
                "\"fullyQualifiedClassName\": \"com.bilgin.accounting.Transaction\", " +
                "\"feedbacks\": [\"I love it\", \"Let's be friends\"]" +
                "}");
        assertEquals(
                "com.bilgin.accounting.Transaction: I love it. Let's be friends",
                JavaFileFeedback.fromPostResponse(postResponse).message());
    }

    @Test
    void includesAPieceOfFeedbackWhenGiven() {
        PostResponse postResponse = new PostResponse(
                200, "{" +
                "\"fullyQualifiedClassName\": \"com.bilgin.accounting.Transaction\", " +
                "\"feedbacks\": [\"Not quite my tempo\"]" +
                "}");
        assertEquals(
                "com.bilgin.accounting.Transaction: Not quite my tempo",
                JavaFileFeedback.fromPostResponse(postResponse).message());
    }

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
