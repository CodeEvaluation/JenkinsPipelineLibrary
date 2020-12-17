package pipeline;

import org.junit.jupiter.api.Test;
import stage.CodeEvaluation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PipelineTest {
    @Test
    void runsCodeEvaluation() {
        MockJenkins jenkins = new MockJenkins();
        CodeEvaluation codeEvaluation = mock(CodeEvaluation.class);
        Pipeline pipeline = new Pipeline(jenkins, codeEvaluation);

        pipeline.run();

        verify(codeEvaluation, times(1)).run();
    }

    @Test
    void printsFailureIfExceptionCaught() {
        MockJenkins jenkins = new MockJenkins();
        CodeEvaluation codeEvaluation = mock(CodeEvaluation.class);
        doThrow(new RuntimeException("Oh no")).when(codeEvaluation).run();
        Pipeline pipeline = new Pipeline(jenkins, codeEvaluation);

        assertThrows(RuntimeException.class, pipeline::run);
        verify(jenkins.mock, times(1)).println(contains("Failure"));
    }

    @Test
    void printsSuccessAtTheEnd() {
        MockJenkins jenkins = new MockJenkins();
        CodeEvaluation codeEvaluation = mock(CodeEvaluation.class);
        Pipeline pipeline = new Pipeline(jenkins, codeEvaluation);

        pipeline.run();

        verify(jenkins.mock, times(1)).println(contains("Success"));
    }
}