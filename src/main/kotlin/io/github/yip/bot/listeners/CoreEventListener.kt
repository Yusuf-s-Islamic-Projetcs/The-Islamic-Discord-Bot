package io.github.yip.bot.listeners

import io.github.ydwk.ydwk.evm.event.events.gateway.ReadyEvent
import io.github.ydwk.ydwk.evm.event.events.gateway.ShutDownEvent
import io.github.ydwk.ydwk.evm.listeners.CoreListeners
import io.github.yip.bot.database
import io.github.yip.bot.databse.HandleDataBaseTables
import io.github.yip.bot.mainLogger

class CoreEventListener : CoreListeners {

    override fun onReady(event: ReadyEvent) {
        mainLogger.info("Ready to serve ${event.totalGuildsAmount} guilds")
        database.getDataSource().connection.use { connection -> HandleDataBaseTables(connection) }
    }

    override fun onShutDown(event: ShutDownEvent) {
        mainLogger.warn("Shutting down for ${event.closeCode.getReason()}")
        database.close()
    }
}