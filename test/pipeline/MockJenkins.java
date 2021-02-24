package pipeline;

import groovy.lang.Closure;
import org.mockito.Mockito;
import stage.analysis.JavaFile;

import java.util.List;

public class MockJenkins extends Jenkins {
    public final Jenkins mock;

    public MockJenkins() {
        super(new Object());
        mock = Mockito.mock(Jenkins.class);
    }

    @Override
    public void node(Closure<Void> closure) {
        mock.node(closure);
        closure.call();
    }

    @Override
    public void stage(String name, Closure<Void> closure) {
        mock.stage(name, closure);
        closure.call();
    }

    @Override
    public void sh(String script) {
        mock.sh(script);
    }

    @Override
    public void println(String text) {
        mock.println(text);
    }

    @Override
    public JobParameters jobParameters() {
        mock.jobParameters();
        return new JobParameters("https://github.com/robmoore-i/AccountingCalisthenics");
    }

    @Override
    public void dir(String directoryRelativePath, Closure<Void> closure) {
        mock.dir(directoryRelativePath, closure);
        closure.call();
    }

    @Override
    public String workspace() {
        return mock.workspace();
    }

    @Override
    public boolean pathExists(String path) {
        return mock.pathExists(path);
    }

    @Override
    public List<String> findJavaFiles(String path) {
        return mock.findJavaFiles(path);
    }

    @Override
    public JavaFile readJavaFile(String pathToJavaFile) {
        return mock.readJavaFile(pathToJavaFile);
    }

    @Override
    public String readFile(String path) {
        return mock.readFile(path);
    }

    @Override
    public PostResponse post(String url, String body) {
        return mock.post(url, body);
    }
}
