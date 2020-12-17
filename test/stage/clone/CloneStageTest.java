package stage.clone;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import pipeline.JobParameters;
import pipeline.MockJenkins;
import stage.clone.CloneStage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

public class CloneStageTest {

    private final JobParameters jobParameters = new JobParameters("https://github.com/robmoore-i/AccountingCalisthenics");

    @Test
    void returnsTheCodeDirectoryRelativePath() {
        MockJenkins jenkins = new MockJenkins();
        CloneStage stage = new CloneStage(jenkins);

        assertEquals("code", stage.run(jobParameters));
    }

    @Test
    void ensuresTheCodeDirectoryIsDeletedBeforeCloning() {
        MockJenkins jenkins = new MockJenkins();
        CloneStage stage = new CloneStage(jenkins);

        stage.run(jobParameters);

        InOrder inOrder = Mockito.inOrder(jenkins.mock);
        inOrder.verify(jenkins.mock).sh("rm -rf code");
        inOrder.verify(jenkins.mock).sh(startsWith("git clone"));
    }

    @Test
    void showsTheContentsOfTheWorkspace() {
        MockJenkins jenkins = new MockJenkins();
        CloneStage stage = new CloneStage(jenkins);

        stage.run(jobParameters);

        verify(jenkins.mock).sh("ls -al code");
    }

    @Test
    void clonesTheGivenRepository() {
        MockJenkins jenkins = new MockJenkins();
        CloneStage stage = new CloneStage(jenkins);

        stage.run(jobParameters);

        verify(jenkins.mock).sh("git clone " + jobParameters.githubUrl + " code");
    }

    @Test
    void verifiesGitIsPresent() {
        MockJenkins jenkins = new MockJenkins();
        CloneStage stage = new CloneStage(jenkins);

        stage.run(jobParameters);

        verify(jenkins.mock).sh("git --version");
    }

    @Test
    void startsTheStage() {
        MockJenkins jenkins = new MockJenkins();
        CloneStage stage = new CloneStage(jenkins);

        stage.run(jobParameters);

        verify(jenkins.mock).stage(eq("Clone"), any());
    }
}
