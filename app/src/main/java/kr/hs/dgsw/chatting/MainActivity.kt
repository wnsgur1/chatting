package kr.hs.dgsw.chatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.hs.dgsw.chatting.databinding.ActivityMainBinding
import kr.hs.dgsw.chatting.userlist.UserFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userFragment = UserFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.selectedItemId = R.id.myPage


        val currentUser = Firebase.auth.currentUser

        // 로그인이 안되어 있음
        if(currentUser == null){
            Log.d("TAG", "onCreate: $currentUser")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // 바텀 네이게이션 아이템 클릭했을때
        binding.bottomNavigationView.setOnItemSelectedListener {

            // 메뉴아이템에 있는 아이템의 아이디 겟
            when(it.itemId){
                // 가저온 아이디가 userList일때
                R.id.userList -> {
                    // userFragment 실행
                    replaceFragment(userFragment)
                    return@setOnItemSelectedListener true
                }
                // 가저온 아이디가 chatroomList일때
                R.id.chatroomList ->{
                    return@setOnItemSelectedListener true
                }

                // 가저온 아이디가 myPage일때
                R.id.myPage ->{
                    return@setOnItemSelectedListener true
                }
                // 예외
                else ->{
                    return@setOnItemSelectedListener false

                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment )
            commit()
        }
    }
}