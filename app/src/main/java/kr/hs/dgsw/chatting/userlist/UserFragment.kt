package kr.hs.dgsw.chatting.userlist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.hs.dgsw.chatting.Key.Companion.DB_USERS
import kr.hs.dgsw.chatting.R
import kr.hs.dgsw.chatting.databinding.FragmentUserlistBinding

class UserFragment: Fragment(R.layout.fragment_userlist) {

    private lateinit var binding: FragmentUserlistBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentUserlistBinding.bind(view)


        val userListAdapter = UserAdapter()
        binding.userListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userListAdapter
        }


        val currentUserId = Firebase.auth.currentUser?.uid?:""

        Firebase.database.reference.child(DB_USERS)
            .addListenerForSingleValueEvent(object: ValueEventListener{


                override fun onDataChange(snapshot: DataSnapshot) {

                    val userList = mutableListOf<UserItem>()

                    snapshot.children.forEach {
                        val user = it.getValue(UserItem::class.java)

                        user?: return

                        if(user.userId != currentUserId){
                            Log.d("TAG", "onDataChange: $user")
                            userList.add(user)
                        }
                    }
                    userListAdapter.submitList(userList)
                }

                // 데이터를 조회하다 실패한 경우
                override fun onCancelled(error: DatabaseError) {
                }

            })

        // 더미 데이터
//        userListAdapter.submitList(
//            mutableListOf<UserItem?>().apply {
//                add(UserItem("11", "22", "33"))
//            }
//        )
    }
}