package no.arweb.yatzy.util

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import no.arweb.yatzy.Combination

class Util {
    companion object {
        val UPPER_SECTION: Array<Combination> = arrayOf(
            Combination.ONES,
            Combination.TWOS,
            Combination.THREES,
            Combination.FOURS,
            Combination.FIVES,
            Combination.SIXES
        )

        val SPECIAL_COMBINATIONS: Array<Combination> = arrayOf(
            Combination.UPPER_SUM,
            Combination.BONUS,
            Combination.SUM
        )

        fun getViewsByTag(root: ViewGroup, tag: String): ArrayList<View> {
            val views: ArrayList<View> = ArrayList()
            for (child in root.children) {
                if (child is ViewGroup) {
                    views.addAll(getViewsByTag(child, tag))
                }

                if (child.tag == tag) {
                    views.add(child)
                }
            }

            return views
        }
    }
}
