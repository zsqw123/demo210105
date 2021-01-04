package com.zsqw123.demo0104

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun requestAndShow(response: suspend () -> Response<Resp>?) {
            tv_output.text = "running...."
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val resp = response()
                    if (resp != null && resp.isSuccessful && resp.body() != null) {
                        runOnUiThread { tv_output.text = resp.body().toString() }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    runOnUiThread { tv_output.text = "request error\n" + e.stackTraceToString() }
                }
            }
        }

        bt_login.setOnClickListener {
            requestAndShow { service?.login(JsonLogin(edit_num.text.toString(), edit_pwd.text.toString())) }
        }
        bt_regist.setOnClickListener {
            requestAndShow {
                service?.regist(
                    JsonRegist(
                        edit_num.text.toString(),
                        edit_name.text.toString(),
                        edit_pwd.text.toString(),
                        edit_mobile.text.toString(),
                        edit_check.text.toString()
                    )
                )
            }
        }
        bt_check_num.setOnClickListener {
            requestAndShow { service?.checkNum(edit_mobile.text.toString()) }
        }
        bt_check_user.setOnClickListener {
            requestAndShow { service?.checkUser(JsonUserCheck(edit_num.text.toString())) }
        }
        bt_user_list.setOnClickListener {
            requestAndShow { service?.userList() }
        }
        bt_check_num_list.setOnClickListener {
            requestAndShow { service?.checkNumList() }
        }
    }
}