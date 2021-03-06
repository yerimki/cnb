package com.example.cnb;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteFavoriteCompany extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://cnb.dothome.co.kr/FavoriteCompanyDelete.php";
    private Map<String,String> map;

    public DeleteFavoriteCompany(String id, String name, Response.Listener<String>listener){
        super(Request.Method.POST,URL,listener,null);//위 url에 post방식으로 값을 전송

        map=new HashMap<>();
        map.put("id", id);
        map.put("name",name);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
