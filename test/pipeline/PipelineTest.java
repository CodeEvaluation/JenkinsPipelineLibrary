package pipeline;

import org.junit.jupiter.api.Test;
import stage.CodeSpyGlass;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PipelineTest {
    @Test
    void runsCodeSpyGlass() {
        MockJenkins jenkins = new MockJenkins();
        CodeSpyGlass codeSpyGlass = mock(CodeSpyGlass.class);
        Pipeline pipeline = new Pipeline(jenkins, codeSpyGlass);

        pipeline.run();

        verify(codeSpyGlass, times(1)).run();
    }

    @Test
    void printsFailureIfExceptionCaught() {
        MockJenkins jenkins = new MockJenkins();
        CodeSpyGlass codeSpyGlass = mock(CodeSpyGlass.class);
        doThrow(new RuntimeException("Oh no")).when(codeSpyGlass).run();
        Pipeline pipeline = new Pipeline(jenkins, codeSpyGlass);

        assertThrows(RuntimeException.class, pipeline::run);
        verify(jenkins.mock, times(1)).println(contains("Failure"));
    }

    @Test
    void printsSuccessAtTheEnd() {
        MockJenkins jenkins = new MockJenkins();
        CodeSpyGlass codeSpyGlass = mock(CodeSpyGlass.class);
        Pipeline pipeline = new Pipeline(jenkins, codeSpyGlass);

        pipeline.run();

        verify(jenkins.mock, times(1)).println(contains("Success"));
    }
}