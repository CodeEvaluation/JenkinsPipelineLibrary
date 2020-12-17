package stage.objectcalisthenics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class JavaFileTest {
    @Test
    void escapesQuotesInFileContent() {
        JavaFile javaFile = new JavaFile("files/somewhere/MyClass.java",
                "package com.bilgin.accounting;\n" +
                        "\n" +
                        "public class Amount {\n" +
                        "    private final int value;\n" +
                        "\n" +
                        "    public Amount(int value) {\n" +
                        "        this.value = value;\n" +
                        "    }\n" +
                        "\n" +
                        "    @Override\n" +
                        "    public boolean equals(Object o) {\n" +
                        "        if (this == o) return true;\n" +
                        "        if (!(o instanceof Amount)) return false;\n" +
                        "\n" +
                        "        Amount amount = (Amount) o;\n" +
                        "\n" +
                        "        return value == amount.value;\n" +
                        "    }\n" +
                        "\n" +
                        "    @Override\n" +
                        "    public int hashCode() {\n" +
                        "        return value;\n" +
                        "    }\n" +
                        "\n" +
                        "    public void print(StatementPrinter printer) {\n" +
                        "        printer.printRaw(\"Amount: \" + value);\n" +
                        "    }\n" +
                        "\n" +
                        "    public Amount sum(Amount amount) {\n" +
                        "        return new Amount(value + amount.value);\n" +
                        "    }\n" +
                        "\n" +
                        "    public Amount subtract(Amount amount) {\n" +
                        "        return new Amount(value - amount.value);\n" +
                        "    }\n" +
                        "}");
        assertFalse(javaFile.jsonEscapedFileContent().contains("(\""),
                "The quote in the 'print' method wasn't escaped properly");
    }

    @Test
    void parsesClassNameFromPath() {
        JavaFile javaFile = new JavaFile("files/somewhere/MyClass.java",
                "public class MyClass {}");
        assertEquals("MyClass", javaFile.className());
    }
}
