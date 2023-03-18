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
package io.github.yip.bot.api

val baseUrl = "https://api.aladhan.com/v1"

open class AladhanEndpoints {
    interface IAladhanEndpoints {
        /**
         * The endpoint.
         *
         * @return the endpoint
         */
        fun getEndpoint(): String

        /**
         * The endpoint with the rest api url.
         *
         * @return the endpoint with the rest api url
         */
        fun getFullEndpoint(): String {
            return baseUrl + getEndpoint()
        }

        /**
         * The rest api url.
         *
         * @return the rest api url
         */
        fun getRestApiUrl(): String {
            return baseUrl
        }

        fun containsParam(): Boolean {
            return getEndpoint().contains("%s")
        }

        /**
         * The full endpoint with the rest api url, including the parameters.
         *
         * @param params the parameters
         * @return the full endpoint with the rest api url, including the parameters
         */
        // vararg = variable number of arguments
        fun getFullEndpointWithParams(vararg params: String): String {
            val endpoint = getEndpoint()
            val sb = StringBuilder(endpoint)
            sb.insert(0, getRestApiUrl())
            params.forEach { param -> sb.replace(sb.indexOf("%s"), sb.indexOf("%s") + 2, param) }
            return sb.toString()
        }
    }
}
