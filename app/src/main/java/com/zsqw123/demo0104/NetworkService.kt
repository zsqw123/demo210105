package com.zsqw123.demo0104

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.Response
import retrofit2.http.*

interface NetworkService {
    @POST(API_LOGIN)
    suspend fun login(@Body jsonLogin: JsonLogin): Response<Resp>

    @GET(API_CHECK_NUM)
    suspend fun checkNum(@Query("mobile") checkNum: String): Response<Resp>

    @POST(API_REG)
    suspend fun regist(@Body regist: JsonRegist): Response<Resp>

    @POST(API_CHECK_USER)
    suspend fun checkUser(@Body check: JsonUserCheck): Response<Resp>

    @GET(API_USER_LIST)
    suspend fun userList(): Response<Resp>

    @GET(API_CHECK_NUM_LIST)
    suspend fun checkNumList(): Response<Resp>
}

@JsonClass(generateAdapter = true)
class JsonLogin(
    @Json(name = "user_no")
    var userNum: String,
    @Json(name = "user_pwd")
    var userPwd: String
)

@JsonClass(generateAdapter = true)
class JsonRegist(
    @Json(name = "user_no")
    var userNum: String,
    @Json(name = "user_name")
    var userName: String,
    @Json(name = "user_pwd")
    var userPwd: String,
    @Json(name = "mobile")
    var mobile: String,
    @Json(name = "check_no")
    var checkNum: String
)

@JsonClass(generateAdapter = true)
class JsonCheckNum(
    @Json(name = "mobile")
    var mobile: String
)

@JsonClass(generateAdapter = true)
class JsonUserCheck(
    @Json(name = "user_no")
    var userNum: String
)

@JsonClass(generateAdapter = true)
class Resp(
    @Json(name = "IsSuccess")
    var isSuccess: Boolean,
    @Json(name = "Result")
    var result: String,
    @Json(name = "ErrMsg")
    var errMsg: String
) {
    override fun toString(): String {
        return "isSuccess:$isSuccess\nresult:$result\nerrMsg:$errMsg"
    }
}

