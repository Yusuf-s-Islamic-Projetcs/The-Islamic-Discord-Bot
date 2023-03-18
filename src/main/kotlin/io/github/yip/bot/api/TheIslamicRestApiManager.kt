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

import io.github.ydwk.yde.rest.type.*
import okhttp3.RequestBody

interface TheIslamicRestApiManager {
    /**
     * Adds a query parameters to the request
     *
     * @param key The key of the query parameter
     * @param value The value of the query parameter
     * @return The RestApiManager
     */
    fun addQueryParameter(key: String, value: String): TheIslamicRestApiManager

    /**
     * Gets a certain endpoint from the API.
     *
     * @param endPoint The endpoint to get.
     * @param params The parameters to be used in the endpoint.
     * @return The [GetRestApi] instance.
     */
    fun get(endPoint: AladhanEndpoints.IAladhanEndpoints, vararg params: String): GetRestApi

    /**
     * Gets a certain endpoint from the API.
     *
     * @param endPoint The endpoint to get.
     * @return The [GetRestApi] instance.
     */
    fun get(endPoint: AladhanEndpoints.IAladhanEndpoints): GetRestApi {
        return get(endPoint, *arrayOf())
    }

    /**
     * Posts something to the API.
     *
     * @param body The body of the request.
     * @param endPoint The endpoint to post to.
     * @param params The parameters to be added to the endpoint.
     * @return The [PostRestApi] object.
     */
    fun post(
        body: RequestBody?,
        endPoint: AladhanEndpoints.IAladhanEndpoints,
        vararg params: String,
    ): PostRestApi

    /**
     * Pots something to the API.
     *
     * @param body The body of the request.
     * @param endPoint The endpoint to post to.
     * @return The [PostRestApi] object.
     */
    fun post(body: RequestBody?, endPoint: AladhanEndpoints.IAladhanEndpoints): PostRestApi {
        return post(body, endPoint, *arrayOf())
    }

    /**
     * Puts something to the API.
     *
     * @param body The body of the request.
     * @param endPoint The endpoint to put to.
     * @param params The parameters to put to the endpoint.
     * @return The [DeleteRestApi] object.
     */
    fun put(
        body: RequestBody?,
        endPoint: AladhanEndpoints.IAladhanEndpoints,
        vararg params: String
    ): PutRestApi

    /**
     * Puts something to the API.
     *
     * @param body The body of the request.
     * @param endPoint The endpoint to put to.
     * @return The [DeleteRestApi] object.
     */
    fun put(body: RequestBody?, endPoint: AladhanEndpoints.IAladhanEndpoints): PutRestApi {
        return put(body, endPoint, *arrayOf())
    }

    /**
     * Deletes something from the API.
     *
     * @param endPoint The endpoint to delete from.
     * @param params The parameters to be used in the endpoint.
     * @return The [DeleteRestApi] object.
     */
    fun delete(
        body: RequestBody?,
        endPoint: AladhanEndpoints.IAladhanEndpoints,
        vararg params: String,
    ): DeleteRestApi

    /**
     * Deletes something from the API.
     *
     * @param body The body of the request.
     * @param endPoint The endpoint to delete from.
     * @return The [DeleteRestApi] object.
     */
    fun delete(body: RequestBody, endPoint: AladhanEndpoints.IAladhanEndpoints): DeleteRestApi {
        return delete(body, endPoint, *arrayOf())
    }

    /**
     * Deletes something from the API.
     *
     * @param endPoint The endpoint to delete from.
     * @param params The parameters to be used in the endpoint.
     * @return The [DeleteRestApi] object.
     */
    fun delete(endPoint: AladhanEndpoints.IAladhanEndpoints, vararg params: String): DeleteRestApi {
        return delete(null, endPoint, *params)
    }

    /**
     * Deletes something from the API.
     *
     * @param endPoint The endpoint to delete from.
     * @return The [DeleteRestApi] object.
     */
    fun delete(endPoint: AladhanEndpoints.IAladhanEndpoints): DeleteRestApi {
        return delete(null, endPoint)
    }

    /**
     * Patches something from the API.
     *
     * @param body The body of the request.
     * @param endPoint The endpoint to patch from.
     * @param params The parameters to be used in the endpoint.
     * @return The [PatchRestApi] object.
     */
    fun patch(
        body: RequestBody,
        endPoint: AladhanEndpoints.IAladhanEndpoints,
        vararg params: String,
    ): PatchRestApi

    /**
     * Patches something from the API.
     *
     * @param body The body of the request.
     * @param endPoint The endpoint to patch from.
     * @return The [PatchRestApi] object.
     */
    fun patch(body: RequestBody, endPoint: AladhanEndpoints.IAladhanEndpoints): PatchRestApi {
        return patch(body, endPoint, *arrayOf())
    }
}
