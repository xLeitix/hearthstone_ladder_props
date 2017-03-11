# Monte-Carlo Simulating The Hearthstone Ladder

Recently, after being off it for years, I started to get back into [Hearthstone](https://en.wikipedia.org/wiki/Hearthstone_(video_game)). Hearthstone is a fairly simple (yeah, yeah) digital card game with an active competitive scene. Although there are a few alternative ways to play as well as out-of-client tournaments, the "default" in-client way to play the game with your own cards ("constructed") is called "Ranked".

In ranked games, winning allows you to "climb the ladder" (that is, get to higher ranks in a 25-scale ladder ranking) while losing makes you lose ranks. Ladder rules seem simple at first, but are [actually somewhat convoluted if you look at it in detail](http://hearthstone.gamepedia.com/Ranked).

Playing ranked games is free - you can play as many games as you are willing to dedicate time to. On the other hand, climbing the ladder can actually be quite the grind. Leaving aside some of the more involved details of laddering for now, you will need a whopping 85 more wins than losses in a month to go from the lowest rank to the highest. This has led to a somewhat twisted incentive model, where many players opt to play aggressive decks, not because they like them or because these decks are inherently better, but because playing an aggressive deck allows them to get wins in quicker.

Initially, I wanted to know how much of a factor this really is. **How much of an advantage is it to play a deck that wins or loses in 5 minutes rather than 15 or 20?**

## Simulating Hearthstone games

To answer this question, I wrote a simple [Monte Carlo simulation](https://en.wikipedia.org/wiki/Monte_Carlo_method) in the Groovy programming language to simulate many, many games on the Hearthstone ladder. All the simulation code is [available on GitHub](https://github.com/xLeitix/hearthstone_ladder_props) if you want to check it out - it's actually really simple.

I may post a more detailed explanation of my simulation method here in the future, but for now let's focus on the basics. The simulation takes four important parameters that you need to specify upfront:

1. **Your winrate** (What percentage of ranked games do you win on average? Tools such as [Track-o-Bot](https://trackobot.com) can give you a nice, unbiased number here.)
2. **Your starting rank** (e.g., "Shieldbearer")
3. **Your goal rank** (e.g., "Sea Giant")
3. **The number of simulation runs** (How often do you want to repeat the simulation to account for randomness in the results? I used 1000 repetitions in my experiments.)

I did not account for the fact that your winrate will also depend on how far up the rankings you already are. My simulation would be easy to extend with that, but it becomes a bit iffy to accurately determine your winrate depending on your current ranking and the time of the month. I think just assuming a uniform winrate is the cleanest approach for now.

My simulation is based on the current ladder rules in March 2017, and I followed the description on [Gamepedia](http://hearthstone.gamepedia.com/Ranked). Most importantly, my simulation already takes the new [milestones](http://hearthstone.metabomb.net/news/hearthstone-ranked-play-changes-will-add-milestone-progress-floors) into account, which is a fairly important change (more on this at the end of this blog post).

## So how much better is it to play a quick deck?

Well, as it turns out, **a lot**. In the graph below you can see the duration of pure playing time you will need to get from rank 20 ("Shieldbearer") to rank 5 ("Sea Giant"), depending on your win rate (between 45% and 65%) and how long your games take on average (5, 10, 15, and 20 minutes).

FIGURE winrate.png

Don't let yourself get confused by the scales in this graph. Even with a high winrate, playing a fast deck still matters a lot, even if the difference becomes less pronounced the more frequently you win. For example, if your deck takes 20 minutes to win, you will need a winrate of 57% to climb in the same time than a player with a winrate of just 50% and a deck that finishes games in 10 minutes.

Some other interesting observations:

* You can climb the ladder even with a winrate below 50%, but it will take **a long time**. With a fast deck like [Pirate Warrior](http://hearthstone.metabomb.net/deck-guides/pirate-warrior-standard-deck-list-and-guide-hearthstone-4) and 45% winrate, you can get to Rank 5 in about 75 hours of playing. Of course, if you play that much Hearthstone you will probably improve your winrate as well.
* Yes, you will need to play plenty of Hearthstone in any case. For example, with a solid winrate of 55% and a deck that takes about 10 minutes to win, you are still in for about 40 hours of Hearthstone to climb from 20 to 5.

## But can I not get lucky and get there much faster anyway?

Yes, of course you can - it's just not very likely. The graph below shows in [Boxplot](http://stattrek.com/statistics/charts/boxplot.aspx?Tutorial=AP) notation the individual simulation results for different winrates. This plot assumes a deck that takes 10 minutes to win.

FIGURE boxplot.png

What you can see here primarily is that a higher winrate most importantly leads to more predictability in terms of how long you will need to grind. If you sport a cool 65% winrate, you can expect to climb in about 20 hours, and it will rarely take you longer than 25 or so. If your winrate is 55%, you might get there in 25 hours as well, but if you have a bad month than 50 hours is not entirely unexpected either.

For this plot I have skipped the winrates below 50%. For these, you need to get **very** lucky to climb anyway, so the variation between runs is very, very high.

## Doesn't the new milestone rule make climbing much faster?

The Hearthstone ladder recently had a change where, once you have reached rank 20, 15, 10, and 5, you can't fall below this rank anymore in the same month. The data above already took these milestones into account. Let's see if this change matters at all. In the plot below, the left side are the old rules without the milestones, the right side is with milestones.

FIGURE milestones.png

**Holy smokes**, this rule change certainly made the world a better place for players with a winrate below 50%. Before milestones, laddering with such a low winrate was essentially impossible - you would run out of month before you would reach a high rank.

For players with higher winrate, the changes matter as well, but much less so. This can be seen easier if we plot the same data again, but without the winrates lower than 50%.

FIGURE milestones_f.png

If you are a dead average player with a winrate of 50% and you play a 10-minute-per-round deck, the change saves you about 15 hours of playtime in the mean. For strong players with 60% or 65% winrate, the impact of this change is negligible.

## Closing thoughts

These are just a few analyses we can do with my simple Monte Carlo simulation. Other questions that you may be interested in, and which my program can help you answer, are:

* If I play 50 matches every season, how good would I need to be to get to at least rank *X* most seasons?
* If I can dedicate *X* hours of play this season, what rank can I expect to land at?
* How much of a higher winrate do I need with slow deck *X* to justify playing it over the much faster deck *Y*? (Spoiler: it's probably not worth it if you are primarily interested in laddering quickly)
* And so on ...

Currently, the best way to play with these questions is to go to [GitHub](https://github.com/xLeitix/hearthstone_ladder_props) and run the simulation yourself. A little bit of programming intimacy is required, but other than that the program should be rather self-explanatory. If there is enough interest, I may turn this tool int a Web application that is easier to use for non-programmers in the future.
