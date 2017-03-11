class CsvHelper {

    static def writeHeader(def file) {
        file.write("START;GOAL;WINRATE;ROUNDS;DURATION;ROUNDDURATION;MILESTONES_ON\n")
    }

    static def writeDetailsHeader(def file) {
        file.write("START;GOAL;WINRATE;TRIAL;ROUNDS;MILESTONES_ON\n")
    }

    static def writeResultLine(def file, def starting, def goal, def winrate,
                               def rounds, def duration, def durationPerRound, def milestones) {

        file << "$starting;$goal;$winrate;$rounds;$duration;$durationPerRound;$milestones\n"

    }

    static def writeDetailedResultLine(def file, def starting, def goal, def winrate,
                               def trial, def rounds, def milestones) {

        file << "$starting;$goal;$winrate;$trial;$rounds;$milestones\n"

    }

}
