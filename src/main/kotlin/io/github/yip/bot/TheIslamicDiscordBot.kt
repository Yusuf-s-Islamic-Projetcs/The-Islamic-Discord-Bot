package io.github.yip.bot

import io.github.realyusufismail.jconfig.JConfig
import io.github.realyusufismail.jconfig.util.JConfigUtils
import io.github.ydwk.ydwk.BotBuilder.createDefaultBot
import io.github.ydwk.ydwk.YDWK

val config : JConfig = JConfig.build()

fun main() {
    val ydwk : YDWK = createDefaultBot(config.get("token").asString)
        .build()
}

