library("ggplot2")

dataOverview = read.csv("/Users/philipp/git_repos/projects/hearthstone_ladder_props/simulationresults.csv", sep = ";")
dataDetails = read.csv("/Users/philipp/git_repos/projects/hearthstone_ladder_props/simulationresultsDetails.csv", sep = ";")
dataDetails$ROUNDDURATION <- as.factor(dataDetails$ROUNDDURATION)
dataDetails$WINRATE_F <- as.factor(dataDetails$WINRATE)

# simple lineplot (export 500)
print(ggplot(data=dataOverview[dataOverview$MILESTONES_ON == "true",], aes(x=WINRATE, y=DURATION, group=ROUNDDURATION, colour=ROUNDDURATION)) +
        geom_line() +
        labs(title = "Hours of HS to go from Shieldbearer to Sea Giant", x = "Win Rate", y = "Duration [Hours]", colour = "Mins per Round") +
        geom_point())

# how big is the variation?
print(ggplot(data=dataDetails[dataDetails$WINRATE >0.49 & dataDetails$MILESTONES_ON == "true",], aes(x=WINRATE_F, y=(ROUNDS*10/60)))
      + geom_boxplot())  

# compare to 
print(ggplot(data=dataOverview[dataOverview$MILESTONES_ON == "true",], aes(x=WINRATE, y=DURATION, group=ROUNDDURATION, colour=ROUNDDURATION)) +
        geom_line() +
        labs(title = "Hours of HS to go from Shieldbearer to Sea Giant", x = "Win Rate", y = "Duration [Hours]", colour = "Mins per Round") +
        geom_point())

print(ggplot(data=dataOverview, aes(x=WINRATE, y=DURATION, group=ROUNDDURATION, colour=ROUNDDURATION)) +
  geom_line() +
  geom_point() +
  facet_wrap(~MILESTONES_ON))

