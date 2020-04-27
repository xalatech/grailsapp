package sivadm

class TestingJob {
    static triggers = {
        simple name: 'mySimpleTrigger', startDelay: 60000, repeatInterval: 1000
    }
    static group = "MyGroup"
    static description = "Example job with Simple Trigger"
    void execute() {
        println "Job run!"
    }
}
