package kr.hs.dgsw.chatting

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.hs.dgsw.chatting.Key.Companion.DB_URL
import kr.hs.dgsw.chatting.Key.Companion.DB_USERS
import kr.hs.dgsw.chatting.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            // 이메일과 비번을 넘겨서 만듬
            // 조건을 거는게 좋음 ex) 이메일과 비번이 없으면 실행하지 않음
            if (email.isEmpty() && password.isEmpty()){
                Toast.makeText(this, "이메일과 비밀번호가 입력되지 않았습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Log.d("TAG", "아이디 비번: $email, $password")
            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){task ->
                    // 회원가입 성공
                    if(task.isSuccessful){


                        Log.d("TAG", "tjdrhd: ")
                        Toast.makeText(this, "회원가입에 성공했습니다. 로그인해주세요", Toast.LENGTH_SHORT).show()

                    }
                    // 회원가입 실패
                    else{
                        Log.d("TAG", "onCreate: ${task.exception?.message}")
                        Log.d("TAG", "Tlfvo: ${task.isSuccessful}")
                        Toast.makeText(this, "회원가입에 실패했습니다", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.signInButton.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            // 이메일과 비번을 넘겨서 만듬
            // 조건을 거는게 좋음 ex) 이메일과 비번이 없으면 실행하지 않음
            if (email.isEmpty() && password.isEmpty()){
                Toast.makeText(this, "이메일과 비밀번호가 입력되지 않았습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {taks ->

                    val currentUser = Firebase.auth.currentUser
                    // 로그인 성공
                    if(taks.isSuccessful && currentUser != null){
                        val userId = currentUser.uid

                        val user = mutableMapOf<String, Any>()
                        // 데이터 클래스 변수이름과 일치시켜야함
                        user["userId"] = userId
                        user["userName"] = email

                        Log.d("TAG", "onCreate: $userId, $email")

                        // 파이어 베이스를 만든 위치가 미국이면 url를 생략할 수 있음
                        Firebase.database(DB_URL).reference.child(DB_USERS).child(userId).updateChildren(user)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                    // 로그인 실패
                    else{
                        Toast.makeText(this, "로그인에 실패했습니다", Toast.LENGTH_SHORT).show()
                        Log.d("TAG", "onCreate: ${taks.exception}")
                    }

                }
        }

    }
}