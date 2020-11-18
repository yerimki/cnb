package com.example.cnb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText idText;
    EditText pwText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button join_btn = (Button) findViewById(R.id.join);
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivityForResult(intent,5000);
            }
        });

        Button login_btn = (Button) findViewById(R.id.login);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idText = (EditText) findViewById(R.id.id);
                pwText = (EditText) findViewById(R.id.password);
                String id = idText.getText().toString();
                String pw = pwText.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("!!!login",response);
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}")+1)); // Login.php에 response
                            boolean success = jsonObject.getBoolean( "success" ); // Login.php에 success
                            if(success) { // 로그인 성공시
                                String id = jsonObject.getString( "id" );
                                String pw = jsonObject.getString( "pw" );
                                String name = jsonObject.getString( "name" );
                                String pnum = jsonObject.getString( "pnum" );

                                Toast.makeText( getApplicationContext(), id+"님 환영합니다!", Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent( getApplicationContext(), SelectStateActivity.class );

                                intent.putExtra( "userId", id);
                                intent.putExtra( "userPw", pw);
                                intent.putExtra( "userName", name);
                                intent.putExtra( "userPnum", pnum);

                                startActivityForResult(intent,5000);

                            } else { // 로그인 실패시
                                Toast.makeText( getApplicationContext(), "정확한 정보를 입력하세요.", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // 서버로 volley를 이용해서 요청을 함
                LoginRequest loginRequest = new LoginRequest( id, pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(loginRequest);
            }
        });
    }

}
