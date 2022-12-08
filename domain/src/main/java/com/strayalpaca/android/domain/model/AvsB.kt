package com.strayalpaca.android.domain.model

import com.strayalpaca.android.domain.util.getMax2Powers

class AvsB(
    index : Int,
    title : String,
    primaryColor : String,
    updateDate : String,
    thumbnailUrl : String,
    isLike : Boolean = false,
    val completeCount : Int = 0,
    private val itemList : List<AvsBItem> = listOf()
) : Post(index = index, title = title, primaryColor = primaryColor, updateDate = updateDate, thumbnailUrl = thumbnailUrl, isLike = isLike) {

    val abContentList : MutableList<AvsBContent> = mutableListOf()
    private val votedAbContentList : MutableList<AvsBContent> = mutableListOf()
    private var currentRound = 16

    fun createFirstRoundContent() {
        currentRound = getMax2Powers(itemList.size)
        val tempList = itemList.toMutableList()
        tempList.shuffle()
        for (i in 0 until currentRound / 2) {
            abContentList.add(AvsBContent(A = tempList[i * 2], B = tempList[i * 2 + 1], round = currentRound))
        }
    }

    fun createNextRoundContent() {
        val prevRound = currentRound
        currentRound /= 2
        val prevRoundVotes = votedAbContentList.filter { it.round == prevRound }

        for (i in 0 until currentRound / 2) {
            val itemA = prevRoundVotes[i * 2].getVotedItem()
            val itemB = prevRoundVotes[i * 2 + 1].getVotedItem()
            abContentList.add(AvsBContent(A = itemA, B = itemB, round = currentRound))
        }
    }

    fun addVoteData(votedContent : AvsBContent) {
        abContentList.remove(votedContent)
        votedAbContentList.add(votedContent)
    }
}
