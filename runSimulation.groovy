
simulator = new HearthstoneMonteCarloSimulator('Novice Engineer')


results = []
simulator.winRate = 0.65
timePerRound = 15
1000.times {
    result = simulator.simulateToRank("Legend")
    println "Ran for ${result.ranFor} rounds"
    results << (timePerRound * result.ranFor)
}
avgMins = results.sum() / results.size()
avgHours = avgMins / 60
println "Mean duration was $avgHours hours of playing time"