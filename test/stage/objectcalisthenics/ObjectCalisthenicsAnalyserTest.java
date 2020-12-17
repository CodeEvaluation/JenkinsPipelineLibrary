package stage.objectcalisthenics;

import org.junit.jupiter.api.Test;
import pipeline.MockJenkins;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ObjectCalisthenicsAnalyserTest {
    @Test
    void failsWithAssertionErrorIfPathDoesntExist() {
        MockJenkins jenkins = new MockJenkins();
        when(jenkins.mock.pathExists("non-existent-file")).thenReturn(false);
        ObjectCalisthenicsAnalyser objectCalisthenicsAnalyser = new ObjectCalisthenicsAnalyser(jenkins);

        assertThrows(AssertionError.class,
                () -> objectCalisthenicsAnalyser.analyse("non-existent-file"));
    }
}