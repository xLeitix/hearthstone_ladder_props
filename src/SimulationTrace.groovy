/**
 * This is a separate class that represents a (partial or full) simulation run.
 *
 * Will mostly be used to be able to print a log of a simulation (to CSV or to the console).
 *
 */
class SimulationTrace {

    def simulationResults = []
    def rankinator = new HearthstoneRankinator()

    def appendRound(def roundnr, def outcome, def starsBefore, def starsAfter, def streak){
        def round = new Round(roundnr : roundnr, outcome : outcome, starsBefore: starsBefore,
                starsAfter : starsAfter, streak : streak)
        // println round
        simulationResults << round
    }

    def lastRound(){
        simulationResults[-1]
    }

    @Override
    String toString(){
        super.toString()
    }

    void toCsv(def outfileName) {
        // TODO
    }

    private class Round {
        def roundnr
        def outcome
        def starsBefore
        def starsAfter
        def streak

        @Override
        public String toString() {
            "$roundnr: $outcome : $starsBefore -> $starsAfter (${rankinator.toHumanReadable(starsAfter)})"
        }

    }

}
