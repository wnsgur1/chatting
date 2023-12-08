package kr.hs.dgsw.chatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val currentUser = Firebase.auth.currentUser

        // 로그인이 안되어 있음
        if(currentUser == null){
            Log.d("TAG", "onCreate: $currentUser")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}