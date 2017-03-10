import groovy.json.JsonSlurper

/**
 * This is a helper implementing the somewhat-complex
 * rules that govern how one gains and loses ranks
 * in HS.
 *
 * Business logics and names follow this description:
 *
 * http://hearthstone.gamepedia.com/Ranked
 *
 *
 */
class HearthstoneRankinator {

    final def RANKCFG = 'resources/ranks.json'

    final def STREAK_TH = 3

    def ranks

    public HearthstoneRankinator() {
        ranks = parseRanks(RANKCFG)
    }

    int nextStars(def win, def previousStars, def streak) {

        def newStars = previousStars

        def prevRank = ranks.find{ previousStars >= it.starsRequired }

        if(win) {

            // we definitely get one star
            newStars++

            // check if we also get a bonus from a win streak
            if(prevRank.allowsStreak && (streak + 1) >= STREAK_TH) {
                newStars++
            }

        } else {

            // we drop a star unless we are at a milestone or at a rank where we don't lose stars at all
            if(!(!prevRank.allowsDrop || atMilestone(prevRank, previousStars))) {
                newStars--
            }

        }

        return newStars

    }

    private def atMilestone(rank, stars) {
        rank.isMilestone && rank.starsRequired == stars
    }

    def toHumanReadable(def stars) {
        ranks.find{ stars >= it.starsRequired }.humanName
    }

    def toStars(def humanReadableRank) {
        ranks.find{ it.humanName == humanReadableRank }.starsRequired
    }

    private def parseRanks(def filename) {
        def json = new JsonSlurper().parse(new File(filename))
        def ranks = []
        json.each {
            ranks << new Rank( numericalRank: it.humanRank, starsInRank: it.stars,
                        humanName: it.humanName, isMilestone: it.isMilestone,
                        allowsStreak: it.allowsStreak, allowsDrop: it.allowsDrop
                     )
        }
        ranks.sort{ a, b -> -1 * (a.numericalRank <=> b.numericalRank) }
        def lastStars = 1
        ranks.each { rank ->
            rank.starsRequired = lastStars
            lastStars += rank.starsInRank
        }
        return ranks.reverse()
    }

    private class Rank {

        def numericalRank
        def starsInRank
        def humanName
        def isMilestone
        def allowsStreak
        def allowsDrop
        def starsRequired

    }

}
