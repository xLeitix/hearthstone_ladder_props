class HearthstoneMonteCarloSimulator {

    final def RAND = new Random()

    def winRate
    def startingStars
    HearthstoneRankinator rankinator = new HearthstoneRankinator()

    def HearthstoneMonteCarloSimulator(String humanName) {
        startingStars = rankinator.toStars(humanName)
    }

    def simulateFixedRounds(def rounds){
        def currStars = startingStars
        def streak = 0  // simulations always start with a streak of 0 for now
        def log = new SimulationTrace()
        rounds.times { round ->
            def result = simulateRound(round, currStars, streak)
            streak = result.streak
            log.appendRound(round, result.result, currStars, result.resultStars, result.streak)
            currStars = result.resultStars
        }
        [result : log.lastRound().starsAfter, fullLog : log]
    }

    def simulateToRank(def humanRank){
        simulateToStars(rankinator.toStars(humanRank))
    }

    def simulateToStars(def stars){
        def currStars = startingStars
        def streak = 0  // simulations always start with a streak of 0 for now
        def log = new SimulationTrace()
        def round = 0
        while(currStars < stars) {
            def result = simulateRound(round++, currStars, streak)
            streak = result.streak
            log.appendRound(round, result.result, currStars, result.resultStars, result.streak)
            currStars = result.resultStars
        }
        [result : log.lastRound().starsAfter, fullLog : log, ranFor : round]
    }

    private def simulateRound(def currRound, def currStars, def prevWins) {
        def win = (nextMatchResult() <= winRate)
        def resultStars = rankinator.nextStars(win, currStars, prevWins)
        if(win) {
            [round : currRound, result : RoundResult.WIN, resultStars : resultStars, streak : ++prevWins]
        } else {
            [round : currRound, result : RoundResult.LOSS, resultStars : resultStars, streak : 0]
        }
    }

    private def nextMatchResult() {
        RAND.nextFloat()
    }

    enum RoundResult {
        WIN, LOSS
    }

}
