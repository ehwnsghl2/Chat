package brandjunhoe89.chatting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import brandjunhoe89.chatting.fragment.AccountFragment;
import brandjunhoe89.chatting.fragment.ChatFragment;
import brandjunhoe89.chatting.fragment.PeopleFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.mainactivity_bottomnavigationview);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_people:
                        getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout,new PeopleFragment()).commit();
                        return true;
                    case R.id.action_chat:
                        getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout,new ChatFragment()).commit();
                        return true;
                    case R.id.action_account:
                        getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout,new AccountFragment()).commit();
                        return  true;


                }
                return false;
            }
        });
        passPushTokenToServer();
    }
    void passPushTokenToServer(){

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String token = FirebaseInstanceId.getInstance().getToken();
        Map<String, Object> map = new HashMap<>();
        map.put("pushToken", token);

        //푸쉬 토큰은 해쉬맵으로 생성해서 넣을 수 밖에 없음
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).updateChildren(map);
    }
}
