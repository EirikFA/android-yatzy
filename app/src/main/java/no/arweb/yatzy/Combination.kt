package no.arweb.yatzy

import android.content.Context

enum class Combination {
    ONES {
        override fun friendlyName(context: Context): String = context.getString(R.string.ones)
    },
    TWOS {
        override fun friendlyName(context: Context): String = context.getString(R.string.twos)
    },
    THREES {
        override fun friendlyName(context: Context): String = context.getString(R.string.threes)
    },
    FOURS {
        override fun friendlyName(context: Context): String = context.getString(R.string.fours)
    },
    FIVES {
        override fun friendlyName(context: Context): String = context.getString(R.string.fives)
    },
    SIXES {
        override fun friendlyName(context: Context): String = context.getString(R.string.sixes)
    },
    UPPER_SUM {
        override fun friendlyName(context: Context): String = context.getString(R.string.sum)
    },
    BONUS {
        override fun friendlyName(context: Context): String = context.getString(R.string.bonus)
    },
    PAIR {
        override fun friendlyName(context: Context): String = context.getString(R.string.pair)
    },
    TWO_PAIRS {
        override fun friendlyName(context: Context): String = context.getString(R.string.two_pairs)
    },
    THREE_PAIRS {
        override val isMaxiOnly: Boolean = true

        override fun friendlyName(context: Context): String =
            context.getString(R.string.three_pairs)
    },
    THREE_OF_A_KIND {
        override fun friendlyName(context: Context): String =
            context.getString(R.string.three_of_a_kind)
    },
    FOUR_OF_A_KIND {
        override fun friendlyName(context: Context): String =
            context.getString(R.string.four_of_a_kind)
    },
    FIVE_OF_A_KIND {
        override val isMaxiOnly: Boolean = true

        override fun friendlyName(context: Context): String =
            context.getString(R.string.five_of_a_kind)
    },
    SMALL_STRAIGHT {
        override fun friendlyName(context: Context): String =
            context.getString(R.string.small_straight)
    },
    LARGE_STRAIGHT {
        override fun friendlyName(context: Context): String =
            context.getString(R.string.large_straight)
    },
    FULL_STRAIGHT {
        override val isMaxiOnly: Boolean = true

        override fun friendlyName(context: Context): String =
            context.getString(R.string.full_straight)
    },
    FULL_HOUSE {
        override fun friendlyName(context: Context): String = context.getString(R.string.full_house)
    },
    CASTLE {
        override val isMaxiOnly: Boolean = true

        override fun friendlyName(context: Context): String = context.getString(R.string.castle)
    },
    TOWER {
        override val isMaxiOnly: Boolean = true

        override fun friendlyName(context: Context): String = context.getString(R.string.tower)
    },
    CHANCE {
        override fun friendlyName(context: Context): String = context.getString(R.string.chance)
    },
    YATZY {
        override fun friendlyName(context: Context): String = context.getString(R.string.yatzy)
    },
    SUM {
        override fun friendlyName(context: Context): String = context.getString(R.string.sum)
    };

    open val isMaxiOnly: Boolean = false

    abstract fun friendlyName(context: Context): String
}
