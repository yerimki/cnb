package com.example.cnb;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class InsertFavoriteCompany extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://cnb.dothome.co.kr/FavoriteCompanyInsert.php";
    private Map<String,String> map;

    public InsertFavoriteCompany(String id, String local, String name, String content, String type, String category, String owner, String pnum, String address, String cite, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);//위 url에 post방식으로 값을 전송

        if(cite==null) // 기업의 사이트가 존재하지 않을 때
            cite = "not exist";

        map=new HashMap<>();
        map.put("id",id);
        map.put("local",local);
        map.put("name",name);
        map.put("content",content);
        map.put("type",type);
        map.put("category",category);
        map.put("owner",owner);
        map.put("pnum",pnum);
        map.put("address",address);
        map.put("cite",cite);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
