package stage.analysis;

import org.junit.jupiter.api.Test;
import pipeline.MockJenkins;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class AnalyserTest {
    @Test
    void failsWithAssertionErrorIfPathDoesntExist() {
        MockJenkins jenkins = new MockJenkins();
        when(jenkins.mock.pathExists("non-existent-file")).thenReturn(false);
        Analyser analyser = new Analyser(jenkins);

        assertThrows(AssertionError.class,
                () -> analyser.analyse("non-existent-file"));
    }
}