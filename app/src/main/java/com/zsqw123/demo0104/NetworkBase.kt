package com.zsqw123.demo0104

import android.util.Log
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

const val API_LOGIN = "/Login"
const val API_CHECK_NUM = "/CheckNo"
const val API_REG = "/Register"
const val API_CHECK_USER = "/UserCheck"
const val API_USER_LIST = "/UserList"
const val API_CHECK_NUM_LIST = "/CheckNoList"

val service by lazy { NetworkBase.creatService(NetworkService::class.java) }

object NetworkBase {

//    private const val baseUrl = "http://1.2.3.187:5000/api/"
    private const val baseUrl = "http://10.0.2.2:5000/api/"
//    private const val baseUrl = "http://127.0.0.1:5000/api/"
    private val networkServiceMap = hashMapOf<String, Any>()

    /**
     * 注意 json需要使用moshi 严禁gson
     * @param clazz Class<T>
     * @param tag String log tag
     * @param useCachedService Boolean 是否复用已初始化的service,按类名复用
     * @return T? if crash null else service
     */
    @Synchronized
    fun <T> creatService(clazz: Class<T>, tag: String = "NetworkBase---${clazz.name}", useCachedService: Boolean = true): T? {
        return try {
            @Suppress("UNCHECKED_CAST")
            if (useCachedService && networkServiceMap.containsKey(clazz.name)) return networkServiceMap[clazz.name] as T
            val mLogging = HttpLoggingInterceptor { message -> Log.d(tag, "log: $message") }
            mLogging.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().proxy(Proxy.NO_PROXY).addInterceptor(mLogging)
                .connectTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .build()
            val retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(
                MoshiConverterFactory.create(Moshi.Builder().build())
            ).client(client).build()
            return retrofit.create(clazz).also {
                networkServiceMap[clazz.name] = it!!
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}