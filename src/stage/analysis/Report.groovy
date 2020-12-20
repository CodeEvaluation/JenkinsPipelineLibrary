package stage.analysis

import pipeline.Jenkins

class Report {
    private final List<ReportEntry> reportEntries

    Report(List<ReportEntry> reportEntries) {
        this.reportEntries = reportEntries
    }

    void printWith(Jenkins jenkins) {
        StringBuilder reportTextBuilder = new StringBuilder("=== Report ===")
        for (ReportEntry reportEntry : reportEntries) {
            reportTextBuilder.append(reportEntry.message())
        }
        String reportText = reportTextBuilder.toString()
        jenkins.println("${reportText}\n\n=== End of report ===")
    }
}
