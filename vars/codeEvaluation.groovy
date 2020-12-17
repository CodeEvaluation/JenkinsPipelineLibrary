import pipeline.Jenkins
import pipeline.Pipeline
import stage.CodeEvaluation

def call() {
    node {
        Jenkins jenkins = new Jenkins(this)
        Pipeline pipeline = new Pipeline(jenkins, new CodeEvaluation(jenkins))
        pipeline.run()
    }
}