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

    String toCsv() {
        def builder = new StringBuilder()
        def uuid = UUID.randomUUID()
        simulationResults.each { round ->
            builder << "${uuid.toString()};"
            builder << round.toCsvString()
        }
        return builder.toString()
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

        def toCsvString() {
            "$roundnr;$outcome;$starsBefore;$starsAfter;$streak"
        }

    }

}
