package com.example.cnb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MyPageActivity extends AppCompatActivity {
    TextView myId, myPw, myName, myPnum;
    ListView mListView;
    ArrayList<User> userList = new ArrayList<User>();
    ArrayList<String> array_favorite = new ArrayList<>();;
    ArrayAdapter<String> adapter;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_page);

        myId = (TextView) findViewById(R.id.my_id);
        myId.setText(getIntent().getExtras().getString("userId"));
        myPw = (TextView) findViewById(R.id.my_pw);
        myPw.setText(getIntent().getExtras().getString("userPw"));
        myName = (TextView) findViewById(R.id.my_name);
        myName.setText(getIntent().getExtras().getString("userName"));
        myPnum = (TextView) findViewById(R.id.my_pnum);
        myPnum.setText(getIntent().getExtras().getString("userPnum"));

        mListView = (ListView) findViewById(R.id.list_favorite);
        array_favorite = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, array_favorite);
        mListView.setAdapter(adapter);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        queue = Volley.newRequestQueue(getApplicationContext());
        sendRequest();
        findViewById(R.id.list_delete).setOnClickListener(mClickListener);
        findViewById(R.id.list_info).setOnClickListener(mClickListener);

    }

    // 서버에서 응답을 받아 관심기업을 리스트에 추가
    public void sendRequest(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("!!!", response);
                    String sarr[] = response.split("<br />");
                    array_favorite.clear();
                    for(int i = 0; i < sarr.length; i++){
                        JSONObject jsonObject = new JSONObject(sarr[i]);
                        User user = new User();
                        user.setLocal(convertString(jsonObject.getString("local")));
                        user.setName(convertString(jsonObject.getString( "name" )));
                        user.setContent(convertString(jsonObject.getString("content")));
                        user.setType(convertString(jsonObject.getString( "type" )));
                        user.setField(convertString(jsonObject.getString("category")));
                        user.setOwner(convertString(jsonObject.getString( "owner" )));
                        user.setPnum(convertString(jsonObject.getString("pnum")));
                        user.setAddress(convertString(jsonObject.getString( "address" )));
                        user.setCite(convertString(jsonObject.getString("cite")));
                        array_favorite.add(convertString(jsonObject.getString( "name" )));
                        userList.add(user);
                    }
                    // listview 갱신
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        // 서버로 volley를 이용해서 요청을 함
        FavoriteCompanyList favoritecompanylist = new FavoriteCompanyList(getIntent().getExtras().getString("userId"), responseListener);
        RequestQueue queue = Volley.newRequestQueue(MyPageActivity.this);
        queue.add(favoritecompanylist);

    }

    // 유니코드에서 String으로 변환
    public String convertString(String val) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < val.length(); i++) {
            if ('\\' == val.charAt(i) && 'u' == val.charAt(i + 1)) {
                Character r = (char) Integer.parseInt(val.substring(i + 2, i + 6), 16);
                sb.append(r);
                i += 5;
            } else {
                sb.append(val.charAt(i));
            }
        }
        return sb.toString();
    }

    // 리스트뷰의 상단 버튼에서 사용하는 listener
    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            if(mListView.getCheckedItemPosition()<0){
                Toast.makeText(getApplicationContext(), "선택한 관심기업이 없습니다.", Toast.LENGTH_SHORT).show();
            }
            else {
                switch (v.getId()) {
                    case R.id.list_delete: // 관심기업 삭제
                        deleteList(array_favorite.get(mListView.getCheckedItemPosition()));
                        array_favorite.remove(mListView.getCheckedItemPosition());
                        // listview 갱신
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.list_info: // 상세페이지로 이동
                        Intent intent = new Intent(getApplicationContext(), InfoActivity.class);

                        User user = null;
                        for (int i = 0; i < userList.size(); i++) {
                            if (array_favorite.get(mListView.getCheckedItemPosition()).equals(userList.get(i).getName()))
                                user = userList.get(i);
                        }

                        intent.putExtra("user", user);
                        intent.putExtra("userId", getIntent().getSerializableExtra("userId"));
                        intent.putExtra("userPw", getIntent().getSerializableExtra("userPw"));
                        intent.putExtra("userName", getIntent().getSerializableExtra("userName"));
                        intent.putExtra("userPnum", getIntent().getSerializableExtra("userPnum"));
                        startActivityForResult(intent, 5000);
                        break;
                }
            }
        }
    };

    // 서버를 통해 mysql에 사용자 아이디 테이블에서 관심기업 삭제
    public void deleteList(final String name){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("!!!", response);
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}")+1));
                    System.out.println(name);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Toast.makeText(getApplicationContext(), "관심기업 삭제 성공", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        // 서버로 volley를 이용해서 요청을 함
        DeleteFavoriteCompany deletefavoritecompany = new DeleteFavoriteCompany(getIntent().getExtras().getString("userId"), name, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MyPageActivity.this);
        queue.add(deletefavoritecompany);

    }

}


