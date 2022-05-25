package com.nousguide.android.common.models

import android.graphics.drawable.Icon
import java.io.Serializable


open class ButtonLink(
    val label: String,
    val id: String,
    val type: Type,
    val action: Action,
    val icon: Icon
) : Serializable {

    enum class Type {
        BUTTON,
        UNKNOWN
    }

    enum class Action {
        PLAY,
        PLAYLIST,
        VIEW,
        EXTERNAL,
        TV,
        LINEUP, // Internal only
        FAVORITE, // Internal only
        DISMISS, // Internal only
        UNKNOWN
    }
}
