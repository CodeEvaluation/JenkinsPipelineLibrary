package stage.analysis;

import org.junit.jupiter.api.Test;
import pipeline.MockJenkins;
import pipeline.PostResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AnalyserTest {
    @Test
    void convertsThePostResponseToJavaFileFeedback() {
        MockJenkins jenkins = new MockJenkins();
        when(jenkins.mock.readJavaFile(anyString()))
                .thenReturn(new JavaFile("SomeClass.java", "public class SomeClass {}"));
        PostResponse stubPostResponse = new PostResponse(200, "{" +
                "\"fullyQualifiedClassName\": \"SomeClass\", " +
                "\"feedbacks\": []" +
                "}");
        when(jenkins.mock.post(anyString(), anyString()))
                .thenReturn(stubPostResponse);
        Analyser analyser = new Analyser(jenkins);

        assertEquals(JavaFileFeedback.fromPostResponse(stubPostResponse),
                analyser.analyse("non-existent-file"));
    }

    @Test
    void postsTheJavaFileAsJson() {
        MockJenkins jenkins = new MockJenkins();
        JavaFile stubJavaFile = new JavaFile(
                "SomeClass.java", "public class SomeClass {}");
        when(jenkins.mock.readJavaFile(anyString()))
                .thenReturn(stubJavaFile);
        when(jenkins.mock.post(anyString(), anyString()))
                .thenReturn(new PostResponse(200, "{" +
                        "\"fullyQualifiedClassName\": \"SomeClass\", " +
                        "\"feedbacks\": []" +
                        "}"));
        Analyser analyser = new Analyser(jenkins);

        analyser.analyse("non-existent-file");

        verify(jenkins.mock, times(1)).post(
                "http://localhost:9000/code-analysis",
                stubJavaFile.toJson());
    }
}