package no.arweb.yatzy

import android.content.Context

enum class Variation {
    FORCED {
        override fun friendlyName(context: Context): String = context.getString(R.string.forced)
    },
    MAXI {
        override fun friendlyName(context: Context): String = context.getString(R.string.maxi)
    };

    abstract fun friendlyName(context: Context): String
}
