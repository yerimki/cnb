package com.example.cnb;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class CommentInsert extends StringRequest{
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://cnb.dothome.co.kr/boyun_CommentInsert.php";
    private Map<String,String> map;

    public CommentInsert(String company, String id, String review, Response.Listener<String>listener){
        super(Request.Method.POST,URL,listener,null);//위 url에 post방식으로 값을 전송

        map=new HashMap<>();
        map.put("company", company);
        String num = getCurrentTime();
        map.put("num", num);
        map.put("id", id);
        map.put("review", review);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

    private String getCurrentTime() {
        SimpleDateFormat format1 = new SimpleDateFormat ( "yyMMddHHmmss");
        String format_time = format1.format (System.currentTimeMillis());
        return format_time;
    }
}
