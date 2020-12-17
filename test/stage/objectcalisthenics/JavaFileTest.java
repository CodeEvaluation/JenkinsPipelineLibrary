package stage.objectcalisthenics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaFileTest {
    @Test
    void parsesClassNameFromPath() {
        JavaFile javaFile = new JavaFile("files/somewhere/MyClass.java",
                "public class MyClass {}");
        assertEquals("MyClass", javaFile.className());
    }
}
