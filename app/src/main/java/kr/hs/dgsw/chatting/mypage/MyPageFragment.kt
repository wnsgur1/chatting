package kr.hs.dgsw.chatting.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.hs.dgsw.chatting.LoginActivity
import kr.hs.dgsw.chatting.R
import kr.hs.dgsw.chatting.databinding.FragmentMypageBinding

class MyPageFragment:Fragment(R.layout.fragment_mypage) {

    private lateinit var binding: FragmentMypageBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMypageBinding.bind(view)

        binding.applyButton.setOnClickListener {
            val userName = binding.userName.text.toString()
            val description = binding.description.text.toString()

            if(userName.isEmpty()){
                // fragment는 this를 사용할 수 없음
                Toast.makeText(context, "유저이름에는 빈 값이 있을 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 파이어베이스 realtime database update
        }

        binding.signoutButton.setOnClickListener {
            Firebase.auth.signOut()

            // 로그아웃하고 로그인 화면으로 전환
            startActivity(Intent(context, LoginActivity::class.java))
            // fragment가 돌아가고 있는 activity를 종료
            activity?.finish()
        }
    }
}