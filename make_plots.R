library("ggplot2")

dataOverview = read.csv("/Users/philipp/git_repos/projects/hearthstone_ladder_props/simulationresults.csv", sep = ";")
dataOverview$DECK <- NA
dataOverview[dataOverview$ROUNDDURATION == 5.50,]$DECK <- "Pirate Warrior"
dataOverview[dataOverview$ROUNDDURATION == 7,]$DECK <- "Midrange Shaman"
dataOverview[dataOverview$ROUNDDURATION == 8.25,]$DECK <- "Dragon Priest"
dataOverview[dataOverview$ROUNDDURATION == 10.50,]$DECK <- "Reno Mage"
dataDetails = read.csv("/Users/philipp/git_repos/projects/hearthstone_ladder_props/simulationresultsDetails.csv", sep = ";")
dataDetails$ROUNDDURATION <- as.factor(dataDetails$ROUNDDURATION)
dataDetails$WINRATE_F <- as.factor(dataDetails$WINRATE)

# simple lineplot (export 500)
print(ggplot(data=dataOverview[dataOverview$MILESTONES_ON == "true",], aes(x=WINRATE, y=DURATION, group=DECK, colour=DECK)) +
        geom_line() +
        labs(title = "Hours of HS to go from Shieldbearer to Sea Giant", x = "Win Rate", y = "Duration [Hours]", colour = "Decks") +
        geom_point())

# how big is the variation?
print(ggplot(data=dataDetails[dataDetails$WINRATE >0.49 & dataDetails$MILESTONES_ON == "true",], aes(x=WINRATE_F, y=(ROUNDS*10/60)))
      + geom_boxplot()
      + labs(title = "Hours of HS to go from Shieldbearer to Sea Giant", x = "Win Rate", y = "Duration [Hours]"))  

# did the introduction of milestones change anything
facetlabels <- c(
  `false` = "No Milestones",
  `true` = "With Milestones"
)
print(ggplot(data=dataOverview, aes(x=WINRATE, y=DURATION, group=ROUNDDURATION, colour=ROUNDDURATION)) +
        geom_line() +
        geom_point() +
        labs(title = "Hours of HS to go from Shieldbearer to Sea Giant", x = "Win Rate", y = "Duration [Hours]", colour = "Mins per Round") +
        facet_wrap(~MILESTONES_ON,labeller = as_labeller(facetlabels)))
print(ggplot(data=dataOverview[dataOverview$WINRATE > 0.49,], aes(x=WINRATE, y=DURATION, group=ROUNDDURATION, colour=ROUNDDURATION)) +
        geom_line() +
        geom_point() +
        labs(title = "Hours of HS to go from Shieldbearer to Sea Giant", x = "Win Rate", y = "Duration [Hours]", colour = "Mins per Round") +
        facet_wrap(~MILESTONES_ON,labeller = as_labeller(facetlabels)))

