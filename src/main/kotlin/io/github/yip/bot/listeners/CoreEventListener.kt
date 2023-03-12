/*
 * Copyright 2022 Yusuf's Islamic projects.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package io.github.yip.bot.listeners

import io.github.ydwk.ydwk.evm.event.events.gateway.ReadyEvent
import io.github.ydwk.ydwk.evm.event.events.gateway.ShutDownEvent
import io.github.ydwk.ydwk.evm.listeners.CoreListeners
import io.github.yip.bot.database
import io.github.yip.bot.mainLogger

class CoreEventListener : CoreListeners {

    override fun onReady(event: ReadyEvent) {
        mainLogger.info("Ready to serve ${event.totalGuildsAmount} guilds")
    }

    override fun onShutDown(event: ShutDownEvent) {
        mainLogger.warn("Shutting down for ${event.closeCode.getReason()}")
        database.close()
    }
}
