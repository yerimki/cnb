package com.example.cnb;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.Serializable;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectStateActivity extends AppCompatActivity {
    private SelectStateAdapter adapter;

    class Content {
        int id;
        String state;

        public Content(int id, String state) {
            this.id = id;
            this.state = state;
        }
    }

    ArrayList<Content> contents;
    Serializable userId, userPw, userName, userPnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_state);

        userId = getIntent().getSerializableExtra("userId");
        userPw = getIntent().getSerializableExtra("userPw");
        userName = getIntent().getSerializableExtra("userName");
        userPnum = getIntent().getSerializableExtra("userPnum");

        contents = new ArrayList<>();
        contents.add(new Content(R.drawable.gangwon, "강원도"));
        contents.add(new Content(R.drawable.gyeonggi, "경기도"));
        contents.add(new Content(R.drawable.gyeongnam, "경상남도"));
        contents.add(new Content(R.drawable.gyeongbuk, "경상북도"));
        contents.add(new Content(R.drawable.gwangju, "광주광역시"));
        contents.add(new Content(R.drawable.daegu, "대구광역시"));
        contents.add(new Content(R.drawable.daejeon, "대전광역시"));
        contents.add(new Content(R.drawable.busan, "부산광역시"));
        contents.add(new Content(R.drawable.seoul, "서울특별시"));
        contents.add(new Content(R.drawable.sejong, "세종특별자치시"));
        contents.add(new Content(R.drawable.ulsan, "울산광역시"));
        contents.add(new Content(R.drawable.incheon, "인천광역시"));
        contents.add(new Content(R.drawable.jeonnam, "전라남도"));
        contents.add(new Content(R.drawable.jeonbuk, "전라북도"));
        contents.add(new Content(R.drawable.jeju, "제주특별자치도"));
        contents.add(new Content(R.drawable.chungnam, "충청남도"));
        contents.add(new Content(R.drawable.chungbuk, "충청북도"));

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent( getApplicationContext(), MyPageActivity.class );

        intent.putExtra( "userId", userId);
        intent.putExtra( "userPw", userPw);
        intent.putExtra( "userName", userName);
        intent.putExtra( "userPnum", userPnum);

        startActivityForResult(intent,5000);

        return true;
    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new SelectStateAdapter(contents, userId, userPw, userName, userPnum);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 5000:
                    Intent outintent = new Intent();
                    setResult(RESULT_OK, outintent);
                    finish();
                    break;
            }
        }
    }
}