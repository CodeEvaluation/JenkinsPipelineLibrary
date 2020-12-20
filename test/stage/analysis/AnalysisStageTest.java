package stage.analysis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pipeline.MockJenkins;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AnalysisStageTest {

    private final JavaFileFinder stubJavaFileFinder = mock(JavaFileFinder.class);
    private final Analyser mockAnalyser = mock(Analyser.class);
    private final MockJenkins jenkins = new MockJenkins();

    @BeforeEach
    void setUp() {
        when(stubJavaFileFinder.findSourceFilePaths("/code"))
                .thenReturn(Collections.emptyList());
        when(mockAnalyser.analyse(anyString()))
                .thenReturn(new JavaFileFeedback("org.rob.Main"));
    }

    @Test
    void runsAnalysisOnEachSourceClass() {
        when(stubJavaFileFinder.findSourceFilePaths("/code"))
                .thenReturn(List.of("src/main/Main.java", "src/main/Thing.java"));
        AnalysisStage stage = new AnalysisStage(jenkins, stubJavaFileFinder, mockAnalyser);

        stage.run("/code");

        verify(mockAnalyser).analyse("src/main/Main.java");
        verify(mockAnalyser).analyse("src/main/Thing.java");
    }

    @Test
    void executesInDirectory() {
        AnalysisStage stage = new AnalysisStage(jenkins, stubJavaFileFinder, mockAnalyser);

        stage.run("/code");

        verify(jenkins.mock).dir(eq("/code"), any());
    }

    @Test
    void startsStage() {
        AnalysisStage stage = new AnalysisStage(jenkins, stubJavaFileFinder, mockAnalyser);

        stage.run("/code");

        verify(jenkins.mock).stage(eq("Analysis"), any());
    }
}