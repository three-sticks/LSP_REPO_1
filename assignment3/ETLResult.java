package org.howard.edu.lsp.assignment3;

public class ETLResult {
    private final int rowsRead;
    private final int rowsTransformed;
    private final int rowsSkipped;
    private final String outputPath;

    public ETLResult(int rowsRead, int rowsTransformed, int rowsSkipped, String outputPath) {
        this.rowsRead = rowsRead;
        this.rowsTransformed = rowsTransformed;
        this.rowsSkipped = rowsSkipped;
        this.outputPath = outputPath;
    }

    public void printSummary() {
        System.out.println("Rows read: " + rowsRead);
        System.out.println("Rows transformed: " + rowsTransformed);
        System.out.println("Rows skipped: " + rowsSkipped);
        System.out.println("Output file: " + outputPath);
    }
}
