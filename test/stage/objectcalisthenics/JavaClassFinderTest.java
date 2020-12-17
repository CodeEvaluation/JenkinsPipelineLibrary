package stage.objectcalisthenics;

import org.junit.jupiter.api.Test;
import pipeline.Jenkins;
import pipeline.MockJenkins;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class JavaClassFinderTest {

    private final Jenkins jenkins = new MockJenkins();

    @Test
    void ifCodeDirectoryIsMisconfiguredItThrowsException() {
        when(jenkins.pathExists(anyString())).thenReturn(false);
        JavaClassFinder javaClassFinder = new JavaClassFinder(jenkins);

        assertThrows(SourceRootNotFound.class,
                () -> javaClassFinder.findSourceClasses("/example-projects/doesnt-exist"));
    }

    @Test
    void ifFindsNothingItThrowsException() {
        when(jenkins.pathExists("/example-projects/empty-directory")).thenReturn(true);
        when(jenkins.pathExists("/example-projects/empty-directory/src/main")).thenReturn(false);
        JavaClassFinder javaClassFinder = new JavaClassFinder(jenkins);

        assertThrows(SourceRootNotFound.class,
                () -> javaClassFinder.findSourceClasses("/example-projects/empty-directory"));
    }

    @Test
    void findsSourceClassesInSrcMainDirectory() {
        when(jenkins.pathExists(anyString())).thenReturn(true);
        when(jenkins.findJavaFiles("/example-projects/some-project/src/main")).thenReturn(List.of("src/main/game/GameScore.java"));
        JavaClassFinder javaClassFinder = new JavaClassFinder(jenkins);

        List<String> sourceClasses = javaClassFinder.findSourceClasses("/example-projects/some-project");

        assertThat(sourceClasses, hasItem("src/main/game/GameScore.java"));
    }

    @Test
    void findsSourceClassesInSrcDirectory() {
        when(jenkins.pathExists(anyString())).thenReturn(true);
        when(jenkins.pathExists("/example-projects/some-project/src/main")).thenReturn(false);
        when(jenkins.pathExists("/example-projects/some-project/src")).thenReturn(true);
        when(jenkins.findJavaFiles("/example-projects/some-project/src")).thenReturn(List.of("src/game/GameScore.java"));
        JavaClassFinder javaClassFinder = new JavaClassFinder(jenkins);

        List<String> sourceClasses = javaClassFinder.findSourceClasses("/example-projects/some-project");

        assertThat(sourceClasses, hasItem("src/game/GameScore.java"));
    }
}