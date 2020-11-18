package com.example.cnb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoActivity extends AppCompatActivity {

    private TextView state_tv, cn_tv, type_tv, field_tv, owner_tv, pn_tv, web_tv, map_addr_tv, write_id_tv; // text view for showing company information
    private ListView listView;
    private ImageButton refreshCommentBtn;
    private Button openCommentBtn;
    private String userId = "";
    private String company = "";

    private CommentDataAdapter commentDataAdapter;
    private RequestQueue queue;
    private ArrayList<CommentData> commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_company);

        getHashKey();

        final User getData = (User) getIntent().getSerializableExtra("user");
        this.userId = getIntent().getExtras().getString("userId");
        this.company = getData.getName();

        // show data
        print_infoData(getData);

        // show map
        show_map(getData.getAddress(), getData.getName());

        // show comment list view
        commentList = new ArrayList<>();
        show_comment_view(company);
        openCommentBtn = findViewById(R.id.open_comment);
        openCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                findViewById(R.id.refresh_comment).setVisibility(View.VISIBLE);
                show_comment_view(company);
            }
        });

        // define refresh comment button
        refreshCommentBtn = findViewById(R.id.refresh_comment);
        refreshCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_comment_view(company);
                Toast.makeText(getApplicationContext(), "새로고침 완료", Toast.LENGTH_SHORT).show();
            }
        });

        // insert new comment
        write_id_tv = (TextView) findViewById(R.id.write_id);
        write_id_tv.setText(userId);

        findViewById(R.id.write_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String write_id = write_id_tv.getText().toString();
                EditText new_review_et = (EditText)  findViewById(R.id.write_review);
                String new_review = new_review_et.getText().toString();

                insert_comment(getData.getName(), write_id, new_review);
            }
        });

        //delete comment
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(InfoActivity.this ,commentDataList.get(position).getUserNum(),Toast.LENGTH_LONG).show();
                String sel_userId = commentList.get(position).getUserId();
                final String sel_userNum = commentList.get(position).getUserNum();
                if(userId.equals(sel_userId)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
                    builder.setTitle("삭제").setMessage("후기를 삭제하시겠습니까?").setCancelable(false)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int whichButton){
                                    delete_comment(sel_userNum);
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int whichButton){

                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        // insert favorite company
        findViewById(R.id.insert_favorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("validate!!!",response);
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}")+1)); // FavortieCompanyValidate.php에 response
                            boolean success = jsonObject.getBoolean("success"); // FavortieCompanyValidate.php에 success
                            if (!success) { // 관심기업이 이미 등록되었는지 확인
                                Toast.makeText(getApplicationContext(), "이미 등록된 관심기업입니다.", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            Log.e("insert!!!",response);
                                            JSONObject jasonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}")+1)); // FavoriteCompanyInsert.php에 response
                                            boolean success = jasonObject.getBoolean("success"); // FavoriteCompanyInsert.php에 success
                                            if (success) { // 관심기업 등록 성공한 경우
                                                Toast.makeText(getApplicationContext(), "관심기업 등록 성공", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(), "관심기업 등록 실패", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                // 서버로 volley를 이용해서 요청을 함
                                InsertFavoriteCompany insertFavoriteCompany = new InsertFavoriteCompany(userId, getData.getLocal(), getData.getName(), getData.getContent(), getData.getType(),
                                        getData.getField(), getData.getOwner(), getData.getPnum(), getData.getAddress(), getData.getCite(), responseListener);
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                queue.add(insertFavoriteCompany);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // 서버로 volley를 이용해서 요청을 함
                ValidateFavoriteCompany validateFavoriteCompany = new ValidateFavoriteCompany(userId, getData.getName(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(validateFavoriteCompany);
            }
        });
    } //END onCreate.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent( getApplicationContext(), MyPageActivity.class );

        intent.putExtra( "userId", userId);
        intent.putExtra("userPw", getIntent().getSerializableExtra("userPw"));
        intent.putExtra("userName", getIntent().getSerializableExtra("userName"));
        intent.putExtra("userPnum", getIntent().getSerializableExtra("userPnum"));

        startActivityForResult(intent,5000);

        return true;
    }

    private void getHashKey(){
        try{
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String key = new String(Base64.encode(md.digest(), 0));
                Log.d("Hash key:", "!!!!!!!"+key+"!!!!!!"); // print hash key
            }
        } catch (Exception e){
            Log.e("name not found", e.toString());
        }
    }

    private void print_infoData(User data) {
        final User getData = data;

        // show data
        state_tv = findViewById(R.id.state_tv);
        state_tv.setText(getData.getLocal());
        cn_tv = findViewById(R.id.cn_tv);
        cn_tv.setText(getData.getName());
        cn_tv = findViewById(R.id.info_tv);
        cn_tv.setText(getData.getContent());
        type_tv = findViewById(R.id.type_tv);
        type_tv.setText(getData.getType());
        field_tv = findViewById(R.id.field_tv);
        field_tv.setText(getData.getField());
        owner_tv = findViewById(R.id.owner_tv);
        owner_tv.setText(getData.getOwner());
        pn_tv = findViewById(R.id.pn_tv);
        pn_tv.setText(getData.getPnum());
        web_tv = findViewById(R.id.web_tv);
        web_tv.setText(getData.getCite());

        // click calling button
        findViewById(R.id.call_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pn_str = getData.getPnum().replaceAll("-","");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+pn_str));
                startActivity(intent);
            }
        });

        // click website button
        findViewById(R.id.web_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getData.getCite()!=null){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://"+getData.getCite()));
                    startActivity(intent);
                }
            }
        });
    }

    private void show_map(String address, String name) {
        /**
         * Kakao Map Api 사용 가이드
         *
         * 1. 플랫폼 등록하기 (pakage명 확인 필요. AndroidManifest.xml에 있음.) (com.example.cnb)
         * 2. 키 해시 등록하기. (getHashKey() 실행해서 얻으면 됨.) ('='까지 적어야함.)
         * 3. 프로젝트에 sdk 추가하기: lib, jniLibs
         * 4. .jar 파일 등록하기: File -> Project Structure -> +버튼 -> 2.jar dependency -> libs -> libDaumMapAndroid.jar
         * 5. AndroidManifest.xml에 퍼미션, 네이티브 앱 키 추가하기. (*추가하는 '위치' 주의하기*)
         * 6. build.gradle -> targetSdkVersion 26 으로 수정.
         * 7. 반드시 import net.daum.mf.map.api.MapView 로 import 해야함.
         *
         */
        // get 위도, 경도
        Geocoder geocoder = new Geocoder(this);
        List<Address>  addr_list = null;
        Double latitude = 0.0; // 위도
        Double longitude = 0.0; // 경도

        try{
            addr_list = geocoder.getFromLocationName(address, 1);
        } catch(IOException e){
            e.printStackTrace();
            Log.e("TEST" ,"getLongitudeLatitude - ERROR!");
        }
        if(addr_list!=null){
            if(addr_list.isEmpty()) {
                Log.e("Address" ,"주소가 잘못되었습니다.");
            }else {
                latitude = addr_list.get(0).getLatitude();
                longitude = addr_list.get(0).getLongitude();
            }
        }

        // show map
        MapView mapView = new MapView(this);
        RelativeLayout mapViewContainer = (RelativeLayout) findViewById(R.id.map_view);
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);
        mapView.setMapCenterPoint(mapPoint, true);
        mapViewContainer.addView(mapView);
        map_addr_tv = findViewById(R.id.map_address);
        map_addr_tv.setText(address);

        // marker in map
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(name);
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin);
        mapView.addPOIItem(marker);
    }

    private void show_comment_view(String name) {
        listView = (ListView)findViewById(R.id.comment_lv);
        commentDataAdapter = new CommentDataAdapter(this, R.layout.comment_item ,commentList);
        listView.setAdapter(commentDataAdapter);
        setListViewHeightBasedOnChildren(listView);

        queue = Volley.newRequestQueue(getApplicationContext());
        sendRequest();
    }

    // 디비에 저장된 후기를 응답받아 리스트에 추가
    private void sendRequest(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("get comment!!!", response);
                    String sarr[] = response.split("<br />");
                    commentList.clear();
                    for (int i = 0; i < sarr.length; i++) {
                        JSONObject jsonObject = new JSONObject(sarr[i]);
                        CommentData commentdata = new CommentData();
                        commentdata.setUserNum(convertString(jsonObject.getString("num")));
                        commentdata.setUserId(convertString(jsonObject.getString("id")));
                        commentdata.setReview(convertString(jsonObject.getString("review")));
                        commentList.add(commentdata);
                    }
                    // listview 갱신
                    commentDataAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        // 서버로 volley를 이용해서 요청을 함
        CommentList commentlist = new CommentList(company, responseListener);
        RequestQueue queue = Volley.newRequestQueue(InfoActivity.this);
        queue.add(commentlist);
    }

    // 유니코드에서 String으로 변환
    private String convertString(String val) {
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


    private static void setListViewHeightBasedOnChildren(ListView listView) { // For dynamic height of list view
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    private void insert_comment(String name, String id, String _review) {
        final String review = _review;

        if(review.getBytes().length<=0) {
            Toast.makeText(getApplicationContext(), "후기를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return ;
        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("insert comment!!!",response);
                    JSONObject jasonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}")+1));
                    boolean success = jasonObject.getBoolean("success");
                    if (success) {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(InfoActivity.this);
                        dialogBuilder.setTitle("완료").setMessage("후기 작성이 완료되었습니다. 후기가 보이지 않다면, 우측의 새로 고침 버튼을 클릭해주세요.").setNeutralButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                openCommentBtn.performClick();
                                show_comment_view(company);
                                EditText et = (EditText) findViewById(R.id.write_review);
                                et.setText("");
                            }
                        });
                        AlertDialog alertDialogObject = dialogBuilder.create();
                        alertDialogObject.show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "후기 등록 실패", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        // 서버로 volley를 이용해서 요청을 함
        CommentInsert insertcomment = new CommentInsert(this.company, userId, review, responseListener);
        RequestQueue queue = Volley.newRequestQueue(InfoActivity.this);
        queue.add(insertcomment);

    }

    private void delete_comment(String num) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("delete comment!!!",response);
                    JSONObject jasonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}")+1));
                    boolean success = jasonObject.getBoolean("success");
                    if (success) {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(InfoActivity.this);
                                    dialogBuilder.setTitle("완료").setMessage("삭제가 완료되었습니다. 후기가 리스트에서 삭제되지 않았다면, 우측의 새로 고침 버튼을 클릭해주세요.").setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            show_comment_view(company);
                                       }
                                    });
                                    AlertDialog alertDialogObject = dialogBuilder.create();
                                    alertDialogObject.show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "삭제 실패", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        // 서버로 volley를 이용해서 요청을 함
        CommentDelete deletecomment = new CommentDelete(num, responseListener);
        RequestQueue queue = Volley.newRequestQueue(InfoActivity.this);
        queue.add(deletecomment);

    }

}

