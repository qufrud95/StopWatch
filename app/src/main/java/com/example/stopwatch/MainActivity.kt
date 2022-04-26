package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() , View.OnClickListener{
    var isRunning = false; // 실행 여부 확인용 변수

    var timer : Timer? = null
    var time = 0

    private lateinit var btn_start : Button
    private lateinit var btn_refresh : Button
    private lateinit var tv_millisecond : TextView
    private lateinit var tv_second : TextView
    private lateinit var tv_minute : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //뷰 가져오기
        btn_start = findViewById(R.id.btn_start)
        btn_refresh = findViewById(R.id.btn_refresh)
        tv_millisecond = findViewById(R.id.tv_millsecond)
        tv_second = findViewById(R.id.tv_second)
        tv_minute = findViewById(R.id.tv_minute)

        btn_start.setOnClickListener(this)
        btn_refresh.setOnClickListener(this)

    }
    // 클릭 이벤트 처리
    override fun onClick(v: View?) {

        when(v?.id){
            R.id.btn_start ->{
                if(isRunning){
                    pause()
                }else{
                    start()
                }
            }
            R.id.btn_refresh -> {
                refresh()
            }
        }
    }

    private fun start(){
        btn_start.text = "일시정지"
        btn_start.setBackgroundColor(getColor(R.color.red))
        isRunning = true

        timer = timer(period = 10){
            time ++

            val milli_second = time % 100
            val second = (time%6000) / 100
            val minute = time / 6000

            runOnUiThread{   //UI Thread 생성
                if(isRunning){
                    tv_millisecond.text = if(milli_second < 10 ) ".0${milli_second}" else ".${milli_second}"
                    tv_second.text = if(second < 10 )".0${second}" else ".${second}"
                    tv_minute.text = "${minute}"
                }
            }


        }
    }
    private  fun pause(){

        btn_start.text = "시작"
        btn_start.setBackgroundColor(getColor(R.color.blue))

        isRunning = false
        timer?.cancel()

    }

    private fun refresh(){

        timer?.cancel()

        btn_start.text = "시작"
        btn_start.setBackgroundColor(getColor(R.color.blue))
        isRunning = false

        time = 0
        tv_millisecond.text=".00"
        tv_second.text=":00"
        tv_minute.text="00"
    }
}