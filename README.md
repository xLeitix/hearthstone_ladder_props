This is the code associated to the blog post [Monte-Carlo Simulating The Hearthstone Ladder]()
on Medium. This repository contains all the Groovy code I wrote, the raw data I analyzed
for the blog post, and an R script I used for blogging.

To run your own simulations, adapt the script runSimulation.groovy and execute it:

`groovy -cp ".:src" runSimulation.groovy`

The parameters etc. should be reasonably self-explanatory. Unless I screwed up, the
business logics of how one gains and drops ranks should be accurate and complete
at the time of writing (March 11th, 2017).
