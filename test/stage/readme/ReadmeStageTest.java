package stage.readme;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pipeline.MockJenkins;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class ReadmeStageTest {
    @Test
    void startsStage() {
        MockJenkins jenkins = new MockJenkins();
        ReadmeStage stage = new ReadmeStage(jenkins);

        stage.run("code");

        Mockito.verify(jenkins.mock).stage(eq("Readme"), any());
    }

    @Test
    void executesInGivenDirectory() {
        MockJenkins jenkins = new MockJenkins();
        ReadmeStage stage = new ReadmeStage(jenkins);

        stage.run("code");

        Mockito.verify(jenkins.mock).dir(eq("code"), any());
    }
}
