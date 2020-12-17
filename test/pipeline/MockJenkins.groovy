package pipeline

import org.mockito.Mockito

class MockJenkins extends Jenkins {
    public final Jenkins mock

    MockJenkins() {
        super(new Object())
        mock = Mockito.mock(Jenkins.class)
    }

    @Override
    void node(Closure closure) {
        mock.node(closure)
        closure.call()
    }

    @Override
    void stage(String name, Closure closure) {
        mock.stage(name, closure)
        closure.call()
    }

    @Override
    void sh(String script) {
        mock.sh(script)
    }

    @Override
    void println(String text) {
        mock.println(text)
    }

    @Override
    JobParameters jobParameters() {
        mock.jobParameters()
        return new JobParameters("https://github.com/robmoore-i/AccountingCalisthenics")
    }

    @Override
    void dir(String directoryRelativePath, Closure closure) {
        mock.dir(directoryRelativePath, closure)
        closure.call()
    }

    @Override
    String workspace() {
        return mock.workspace()
    }

    @Override
    boolean pathExists(String path) {
        return mock.pathExists(path)
    }

    @Override
    List<String> findJavaFiles(String path) {
        return mock.findJavaFiles(path)
    }
}
