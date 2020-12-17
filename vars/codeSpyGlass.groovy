import pipeline.Jenkins
import pipeline.Pipeline
import stage.CodeSpyGlass

def call() {
    node {
        Jenkins jenkins = new Jenkins(this)
        Pipeline pipeline = new Pipeline(jenkins, new CodeSpyGlass(jenkins))
        pipeline.run()
    }
}