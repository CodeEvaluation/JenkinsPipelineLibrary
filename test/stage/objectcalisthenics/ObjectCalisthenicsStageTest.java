package stage.objectcalisthenics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pipeline.MockJenkins;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ObjectCalisthenicsStageTest {

    private final JavaClassFinder stubJavaClassFinder = mock(JavaClassFinder.class);
    private final ObjectCalisthenicsAnalyser mockAnalyser = mock(ObjectCalisthenicsAnalyser.class);
    private final MockJenkins jenkins = new MockJenkins();

    @BeforeEach
    void setUp() {
        when(stubJavaClassFinder.findSourceClasses("/code")).thenReturn(Collections.emptyList());
    }

    @Test
    void runsAnalysisOnEachSourceClass() {
        when(stubJavaClassFinder.findSourceClasses("/code")).thenReturn(List.of("src/main/Main.java", "src/main/Thing.java"));
        ObjectCalisthenicsStage stage = new ObjectCalisthenicsStage(jenkins, stubJavaClassFinder, mockAnalyser);

        stage.run("/code");

        verify(mockAnalyser).analyse("src/main/Main.java");
        verify(mockAnalyser).analyse("src/main/Thing.java");
    }

    @Test
    void executesInDirectory() {
        ObjectCalisthenicsStage stage = new ObjectCalisthenicsStage(jenkins, stubJavaClassFinder, mockAnalyser);

        stage.run("/code");

        verify(jenkins.mock).dir(eq("/code"), any());
    }

    @Test
    void startsStage() {
        ObjectCalisthenicsStage stage = new ObjectCalisthenicsStage(jenkins, stubJavaClassFinder, mockAnalyser);

        stage.run("/code");

        verify(jenkins.mock).stage(eq("Object Calisthenics"), any());
    }
}