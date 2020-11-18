package com.example.cnb;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
import com.android.volley.toolbox.StringRequest;

public class RegisterRequest extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://cnb.dothome.co.kr/Register.php";
    private Map<String,String>map;

    public RegisterRequest(String id, String pw, String name, String pnum, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);//위 url에 post방식으로 값을 전송

        map=new HashMap<>();
        map.put("id",id);
        map.put("pw",pw);
        map.put("name",name);
        map.put("pnum",pnum);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
