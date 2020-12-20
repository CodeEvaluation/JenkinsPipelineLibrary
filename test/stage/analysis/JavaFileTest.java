package stage.analysis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JavaFileTest {
    @Test
    void escapesNewlinesInsideQuotesInFileContent() {
        JavaFile javaFile = new JavaFile("files/somewhere/MyClass.java",
                "package com.bilgin.accounting;\n" +
                        "\n" +
                        "import java.util.List;\n" +
                        "\n" +
                        "public class ConsolePrinter implements StatementPrinter {\n" +
                        "    private final StringBuilder stringBuilder;\n" +
                        "\n" +
                        "    public ConsolePrinter(StringBuilder stringBuilder) {\n" +
                        "        this.stringBuilder = stringBuilder;\n" +
                        "    }\n" +
                        "\n" +
                        "    public void print(AccountStatement statement) {\n" +
                        "        printRaw(\"---------------Statement--------------------\");\n" +
                        "        newLine();\n" +
                        "        statement.print(this);\n" +
                        "    }\n" +
                        "\n" +
                        "    public void printTransactions(List<Transaction> transactions) {\n" +
                        "        printRaw(\"---------------Transactions-----------------\");\n" +
                        "        newLine();\n" +
                        "        for (Transaction transaction : transactions) {\n" +
                        "            printTransaction(transaction);\n" +
                        "        }\n" +
                        "    }\n" +
                        "\n" +
                        "    private void printTransaction(Transaction transaction) {\n" +
                        "        transaction.print(this);\n" +
                        "        newLine();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void printTotalBalance(Amount amount) {\n" +
                        "        printRaw(\"---------------Total Balance----------------\");\n" +
                        "        newLine();\n" +
                        "        amount.print(this);\n" +
                        "        newLine();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void printRaw(String text) {\n" +
                        "        stringBuilder.append(text);\n" +
                        "\n" +
                        "    }\n" +
                        "\n" +
                        "    private void newLine() {\n" +
                        "        stringBuilder.append(\"\\n\");\n" +
                        "    }\n" +
                        "}");
        String jsonEscapedFileContent = javaFile.jsonEscapedFileContent();
        assertTrue(jsonEscapedFileContent.contains("\\\"\\\\n\\\""),
                "The \\n wasn't properly escaped in the 'newline' " +
                        "method. \n" +
                        "Actual: " + jsonEscapedFileContent.substring(jsonEscapedFileContent.length() - 76) + "\n" +
                        "Expected:\n" +
                        "    private void newLine() {\n" +
                        "        stringBuilder.append(\\\"\\\\n\\\");\n" +
                        "    }\n" +
                        "}\n");
    }

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
