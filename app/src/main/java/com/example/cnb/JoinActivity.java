package com.example.cnb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinActivity extends AppCompatActivity {
    EditText idText;
    EditText pwText;
    EditText pwsignText;
    EditText nameText;
    EditText pnumText;
    // ID 정규표현식 : 시작은 영문(소문자)으로만, 숫자를 하나는 반드시 포함, 영문(소문자), 숫자, '_'으로만 이루어진 5 ~ 12자 이하
    String regex = "^[a-z]{1}(?=.*[0-9])[a-z0-9_]{4,11}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        Button join_btn = (Button) findViewById(R.id.Jjoin);
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idText = (EditText) findViewById(R.id.Jid);
                pwText = (EditText) findViewById(R.id.Jpassword);
                pwsignText = (EditText) findViewById(R.id.Jpasssign);
                nameText = (EditText) findViewById(R.id.Jname);
                pnumText = (EditText) findViewById(R.id.Jpnum);
                final String sid = idText.getText().toString();
                final String spw = pwText.getText().toString();
                final String spwsign = pwsignText.getText().toString();
                final String sname = nameText.getText().toString();
                final String spnum = pnumText.getText().toString();

                if(sid.equals("")||spw.equals("")||spwsign.equals("")||sname.equals("")||spnum.equals("")){
                    Toast.makeText(getApplicationContext(), "모든 항목에 값을 입력해주세요.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!sid.matches(regex)){
                    Toast.makeText(getApplicationContext(), "아이디는 시작은 영문(소문자)으로만, 숫자를 하나는 반드시 포함, 영문(소문자), 숫자, '_'으로만 이루어진 5 ~ 12자 이하여야합니다.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(spw.length() < 6){
                    Toast.makeText(getApplicationContext(), "비밀번호는 6자 이상이어야 합니다.",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Response.Listener<String> validate_rl = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response); // UserValidate.php에 response
                                boolean success = jsonObject.getBoolean( "success" ); // UserValidate.php에 success
                                if(!success) { // 이미 등록된 아이디인지 확인
                                    Toast.makeText( getApplicationContext(), "사용할 수 없는 아이디입니다.", Toast.LENGTH_SHORT ).show();
                                    return;
                                }
                                else{
                                    Response.Listener<String> join_rl=new Response.Listener<String>() {//volley
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jasonObject =new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}")+1)); // Register.php에 response
                                                boolean success=jasonObject.getBoolean("success"); // Register.php에 success
                                                if(spw.equals(spwsign)) { // 비밀번호 일치하는지
                                                    if (success) { // 회원등록 성공한 경우
                                                        Toast.makeText(getApplicationContext(), "회원 등록 성공", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                                else{ // 회원등록 실패한 경우
                                                    Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    // 서버로 volley를 이용해서 요청을 함
                                    RegisterRequest registerRequest=new RegisterRequest(sid, spw, sname, spnum, join_rl);
                                    RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                                    queue.add(registerRequest);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    // 서버로 volley를 이용해서 요청을 함
                    ValidateUser validateUser=new ValidateUser(sid,validate_rl);
                    RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                    queue.add(validateUser);
                }
            }

        });
    }

}
