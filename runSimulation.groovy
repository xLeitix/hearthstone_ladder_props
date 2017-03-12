STARTING_RANK = 'Shieldbearer'
GOAL_RANK = 'Sea Giant'
WIN_RATES = [0.45, 0.48, 0.5, 0.52, 0.55, 0.57, 0.6, 0.62, 0.65]
// GAME_DURATIONS = [5, 10, 15, 20] // first draft
// real durations based on http://www.vicioussyndicate.com/game-duration-mean-streets-gadgetzan-meta/
// Pirate Warrior, Midrange Shaman, Dragon Priest, Reno Mage
GAME_DURATIONS = [5.5, 7, 8.25, 10.5]
REPS = 1000
OUTPUT_FILE = 'simulationresults.csv'
DETAILED_FILE = 'simulationresultsDetails.csv'

def runSimulations(def out, def details, def milestones) {
    HearthstoneRankinator.USE_MILESTONES = milestones
    WIN_RATES.each { winrate ->
        simulator = new HearthstoneMonteCarloSimulator(STARTING_RANK)
        simulator.winRate = winrate
        results = []
        REPS.times {
            result = simulator.simulateToRank(GOAL_RANK)
            results << result.ranFor
            CsvHelper.writeDetailedResultLine(details, STARTING_RANK, GOAL_RANK, winrate, it, result.ranFor, milestones)
        }
        avgRounds = results.sum() / results.size()
        GAME_DURATIONS.each{ duration ->
            CsvHelper.writeResultLine(out, STARTING_RANK, GOAL_RANK, winrate, avgRounds, (avgRounds*duration)/60, duration, milestones)
        }
        println "With a winrate of $winrate and milestones set to $milestones, we needed a mean of $avgRounds rounds to get from $STARTING_RANK to $GOAL_RANK"
    }
}

//
// Run all simulations
//

startTime = System.nanoTime()
out = new File(OUTPUT_FILE)
details = new File(DETAILED_FILE)
CsvHelper.writeHeader(out)
CsvHelper.writeDetailsHeader(details)
// first run regularly, with milestones
runSimulations(out, details, true)
// now again without milestones
runSimulations(out, details, false)
endTime = System.nanoTime()
println "Simulations took ${(endTime-startTime)/1000000000.0} seconds. All done. Bye."
