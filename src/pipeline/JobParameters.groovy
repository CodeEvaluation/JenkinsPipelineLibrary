package pipeline

class JobParameters {
    public final String githubUrl

    JobParameters(String githubUrl) {
        this.githubUrl = githubUrl
    }

    @Override
    String toString() {
        return "JobParameters{" +
                "githubUrl='" + githubUrl + '\'' +
                '}'
    }
}
