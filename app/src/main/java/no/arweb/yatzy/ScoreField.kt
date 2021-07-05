package no.arweb.yatzy

import android.widget.TextView
import no.arweb.yatzy.util.Util

class ScoreField(
    val combination: Combination,
    private val player: String,
    private val variation: Variation,
    private val view: TextView
) {
    private fun getScore(fields: Iterable<ScoreField>): Int {

        if (this.combination == Combination.UPPER_SUM) {
            return fields
                .filter { it.player == player && Util.UPPER_SECTION.contains(it.combination) }
                .map { it.getScore(fields) }
                .sum()
        }

        if (this.combination === Combination.BONUS) {
            val upperSumField: ScoreField =
                fields.find { it.player == player && it.combination == Combination.UPPER_SUM }
                    ?: throw IllegalArgumentException("Missing upper sum field for player $player")

            val score: Int = upperSumField.getScore(fields)
            val isForced: Boolean = variation == Variation.FORCED
            if (isForced && score >= 63 || !isForced && score >= 84) {
                return if (isForced) 50 else 100
            }

            return 0
        }

        if (this.combination === Combination.SUM) {
            val scores: List<Int> = fields
                .filter {
                    !Util.UPPER_SECTION.contains(it.combination)
                            && it.player == player
                            && it.combination != Combination.SUM
                }
                .map { it.getScore(fields) }
            return scores.sum()
        }

        val text: String = view.text.toString().trim()
        if (text == "") return 0

        return text.toInt()
    }

    fun updateScore(fields: Iterable<ScoreField>) {
        view.text = getScore(fields).toString()
    }
}
