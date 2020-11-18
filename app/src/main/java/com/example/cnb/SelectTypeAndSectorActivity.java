package com.example.cnb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectTypeAndSectorActivity extends AppCompatActivity {
    ArrayList<User> userList = new ArrayList<User>();
    ArrayList<String> array_company;
    ListView mListView;
    CheckBox cbox1;
    CheckBox cbox2;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_type_sector);

        mListView = (ListView) findViewById(R.id.list_company);
        array_company = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array_company);
        mListView.setAdapter(adapter);

        userList = (ArrayList<User>) getIntent().getSerializableExtra("list");

        Button result_button = (Button) findViewById(R.id.result_button);
        result_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cbox1 = (CheckBox) findViewById(R.id.button1); // 일자리제공형
                cbox2 = (CheckBox) findViewById(R.id.button2); // 사회서비스제공형
                spinner = (Spinner) findViewById(R.id.spinner);
                text = (String) spinner.getSelectedItem();
                array_company.clear();

                if (cbox1.isChecked() && cbox2.isChecked()) {
                    for (int i = 0; i < userList.size(); i++) {
                        if (text.equals("전체") || text.equals(userList.get(i).getField()))
                            array_company.add(userList.get(i).getName());
                    }
                } else if (cbox1.isChecked()) {
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getType().equals("일자리제공형")) {
                            if (text.equals("전체") || text.equals(userList.get(i).getField()))
                                array_company.add(userList.get(i).getName());
                        }
                    }
                } else if (cbox2.isChecked()) {
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getType().equals("사회서비스제공형")) {
                            if (text.equals("전체") || text.equals(userList.get(i).getField()))
                                array_company.add(userList.get(i).getName());
                        }
                    }
                }
                // listview 갱신
                adapter.notifyDataSetChanged();

                if (array_company.isEmpty()) {
                    //다이얼 로그에 가져온 목록을 보여준다.
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SelectTypeAndSectorActivity.this);
                    dialogBuilder.setTitle("해당 항목의 사회적기업이 없음").setMessage("선택한 항목에 해당하는 사회적기업이 존재하지 않습니다.");
                    AlertDialog alertDialogObject = dialogBuilder.create();
                    alertDialogObject.show();
                }
            }
        });

        mListView.setOnItemClickListener(listener);
    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
            User user = null;
            for(int i=0; i<userList.size(); i++){
                if(array_company.get(position).equals(userList.get(i).getName()))
                    user = userList.get(i);
            }
            intent.putExtra("user", user);
            intent.putExtra("userId", getIntent().getSerializableExtra("userId"));
            intent.putExtra("userPw", getIntent().getSerializableExtra("userPw"));
            intent.putExtra("userName", getIntent().getSerializableExtra("userName"));
            intent.putExtra("userPnum", getIntent().getSerializableExtra("userPnum"));
            startActivityForResult(intent, 5000);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent( getApplicationContext(), MyPageActivity.class );

        intent.putExtra("userId", getIntent().getSerializableExtra("userId"));
        intent.putExtra("userPw", getIntent().getSerializableExtra("userPw"));
        intent.putExtra("userName", getIntent().getSerializableExtra("userName"));
        intent.putExtra("userPnum", getIntent().getSerializableExtra("userPnum"));

        startActivityForResult(intent,5000);

        return true;
    }

}



