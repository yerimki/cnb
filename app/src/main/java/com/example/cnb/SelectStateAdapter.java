package com.example.cnb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectStateAdapter extends RecyclerView.Adapter<SelectStateAdapter.ItemViewHolder> {
    // adapter에 들어갈 list 입니다.
    private ArrayList<SelectStateActivity.Content> contents;
    private Context context;
    String state;
    List<User> userList;
    ArrayList<User> list = new ArrayList<User>();
    Serializable userId, userPw, userName, userPnum;

    private void initLoadDB() {
        DataAdapter mDbHelper = new DataAdapter(context.getApplicationContext());
        mDbHelper.createDatabase();
        mDbHelper.open();

        // db에 있는 값들을 model을 적용해서 넣는다.
        userList = mDbHelper.getTableData();

        // db 닫기
        mDbHelper.close();
    }

    public SelectStateAdapter(ArrayList<SelectStateActivity.Content> contents, Serializable userId, Serializable userPw, Serializable userName, Serializable userPnum) {
        this.contents = contents;
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userPnum = userPnum;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();

        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.state_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.imageView.setImageResource(contents.get(position).id);
        itemViewHolder.textView1.setText(contents.get(position).state);
        final int i = position;
        initLoadDB();
        itemViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = contents.get(i).state;
                list.clear();
                if (state.equals("강원도")) {
                    Intent intent = new Intent(context, SelectTypeAndSectorActivity.class);
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getLocal().equals("강원")) {
                            list.add(userList.get(i));
                        }
                    }
                    intent.putExtra("list", list);
                    intent.putExtra("userId", userId);
                    intent.putExtra( "userPw", userPw);
                    intent.putExtra( "userName", userName);
                    intent.putExtra( "userPnum", userPnum);
                    ((Activity) context).startActivityForResult(intent, 5000);
                } else if (state.equals("경기도")) {
                    Intent intent = new Intent(context, SelectTypeAndSectorActivity.class);
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getLocal().equals("경기")) {
                            list.add(userList.get(i));
                        }
                    }
                    intent.putExtra("list", list);
                    intent.putExtra("userId", userId);
                    intent.putExtra( "userPw", userPw);
                    intent.putExtra( "userName", userName);
                    intent.putExtra( "userPnum", userPnum);
                    ((Activity) context).startActivityForResult(intent, 5000);
                } else if (state.equals("경상남도")) {
                    Intent intent = new Intent(context, SelectTypeAndSectorActivity.class);
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getLocal().equals("경남")) {
                            list.add(userList.get(i));
                        }
                    }
                    intent.putExtra("list", list);
                    intent.putExtra("userId", userId);
                    intent.putExtra( "userPw", userPw);
                    intent.putExtra( "userName", userName);
                    intent.putExtra( "userPnum", userPnum);
                    ((Activity) context).startActivityForResult(intent, 5000);
                } else if (state.equals("경상북도")) {
                    Intent intent = new Intent(context, SelectTypeAndSectorActivity.class);
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getLocal().equals("경북")) {
                            list.add(userList.get(i));
                        }
                    }
                    intent.putExtra("list", list);
                    intent.putExtra("userId", userId);
                    intent.putExtra( "userPw", userPw);
                    intent.putExtra( "userName", userName);
                    intent.putExtra( "userPnum", userPnum);
                    ((Activity) context).startActivityForResult(intent, 5000);
                } else if (state.equals("광주광역시")) {
                    Intent intent = new Intent(context, SelectTypeAndSectorActivity.class);
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getLocal().equals("광주")) {
                            list.add(userList.get(i));
                        }
                    }
                    intent.putExtra("list", list);
                    intent.putExtra("userId", userId);
                    intent.putExtra( "userPw", userPw);
                    intent.putExtra( "userName", userName);
                    intent.putExtra( "userPnum", userPnum);
                    ((Activity) context).startActivityForResult(intent, 5000);
                } else if (state.equals("대구광역시")) {
                    Intent intent = new Intent(context, SelectTypeAndSectorActivity.class);
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getLocal().equals("대구")) {
                            list.add(userList.get(i));
                        }
                    }
                    intent.putExtra("list", list);
                    intent.putExtra("userId", userId);
                    intent.putExtra( "userPw", userPw);
                    intent.putExtra( "userName", userName);
                    intent.putExtra( "userPnum", userPnum);
                    ((Activity) context).startActivityForResult(intent, 5000);
                } else if (state.equals("대전광역시")) {
                    Intent intent = new Intent(context, SelectTypeAndSectorActivity.class);
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getLocal().equals("대전")) {
                            list.add(userList.get(i));
                        }
                    }
                    intent.putExtra("list", list);
                    intent.putExtra("userId", userId);
                    intent.putExtra( "userPw", userPw);
                    intent.putExtra( "userName", userName);
                    intent.putExtra( "userPnum", userPnum);
                    ((Activity) context).startActivityForResult(intent, 5000);
                } else if (state.equals("부산광역시")) {
                    Intent intent = new Intent(context, SelectTypeAndSectorActivity.class);
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getLocal().equals("부산")) {
                            list.add(userList.get(i));
                        }
                    }
                    intent.putExtra("list", list);
                    intent.putExtra("userId", userId);
                    intent.putExtra( "userPw", userPw);
                    intent.putExtra( "userName", userName);
                    intent.putExtra( "userPnum", userPnum);
                    ((Activity) context).startActivityForResult(intent, 5000);
                } else if (state.equals("서울특별시")) {
                    Intent intent = new Intent(context, SelectTypeAndSectorActivity.class);
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getLocal().equals("서울")) {
                            list.add(userList.get(i));
                        }
                    }
                    intent.putExtra("list", list);
                    intent.putExtra("userId", userId);
                    intent.putExtra( "userPw", userPw);
                    intent.putExtra( "userName", userName);
                    intent.putExtra( "userPnum", userPnum);
                    ((Activity) context).startActivityForResult(intent, 5000);
                } else if (state.equals("세종특별자치시")) {
                    Intent intent = new Intent(context, SelectTypeAndSectorActivity.class);
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getLocal().equals("세종")) {
                            list.add(userList.get(i));
                        }
                    }
                    intent.putExtra("list", list);
                    intent.putExtra("userId", userId);
                    intent.putExtra( "userPw", userPw);
                    intent.putExtra( "userName", userName);
                    intent.putExtra( "userPnum", userPnum);
                    ((Activity) context).startActivityForResult(intent, 5000);
                } else if (state.equals("울산광역시")) {
                    Intent intent = new Intent(context, SelectTypeAndSectorActivity.class);
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getLocal().equals("울산")) {
                            list.add(userList.get(i));
                        }
                    }
                    intent.putExtra("list", list);
                    intent.putExtra("userId", userId);
                    intent.putExtra( "userPw", userPw);
                    intent.putExtra( "userName", userName);
                    intent.putExtra( "userPnum", userPnum);
                    ((Activity) context).startActivityForResult(intent, 5000);
                } else if (state.equals("인천광역시")) {
                    Intent intent = new Intent(context, SelectTypeAndSectorActivity.class);
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getLocal().equals("인천")) {
                            list.add(userList.get(i));
                        }
                    }
                    intent.putExtra("list", list);
                    intent.putExtra("userId", userId);
                    intent.putExtra( "userPw", userPw);
                    intent.putExtra( "userName", userName);
                    intent.putExtra( "userPnum", userPnum);
                    ((Activity) context).startActivityForResult(intent, 5000);
                } else if (state.equals("전라남도")) {
                    Intent intent = new Intent(context, SelectTypeAndSectorActivity.class);
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getLocal().equals("전남")) {
                            list.add(userList.get(i));
                        }
                    }
                    intent.putExtra("list", list);
                    intent.putExtra("userId", userId);
                    intent.putExtra( "userPw", userPw);
                    intent.putExtra( "userName", userName);
                    intent.putExtra( "userPnum", userPnum);
                    ((Activity) context).startActivityForResult(intent, 5000);
                } else if (state.equals("전라북도")) {
                    Intent intent = new Intent(context, SelectTypeAndSectorActivity.class);
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getLocal().equals("전북")) {
                            list.add(userList.get(i));
                        }
                    }
                    intent.putExtra("list", list);
                    intent.putExtra("userId", userId);
                    intent.putExtra( "userPw", userPw);
                    intent.putExtra( "userName", userName);
                    intent.putExtra( "userPnum", userPnum);
                    ((Activity) context).startActivityForResult(intent, 5000);
                } else if (state.equals("제주특별자치도")) {
                    Intent intent = new Intent(context, SelectTypeAndSectorActivity.class);
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getLocal().equals("제주")) {
                            list.add(userList.get(i));
                        }
                    }
                    intent.putExtra("list", list);
                    intent.putExtra("userId", userId);
                    intent.putExtra( "userPw", userPw);
                    intent.putExtra( "userName", userName);
                    intent.putExtra( "userPnum", userPnum);
                    ((Activity) context).startActivityForResult(intent, 5000);
                } else if (state.equals("충청남도")) {
                    Intent intent = new Intent(context, SelectTypeAndSectorActivity.class);
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getLocal().equals("충남")) {
                            list.add(userList.get(i));
                        }
                    }
                    intent.putExtra("list", list);
                    intent.putExtra("userId", userId);
                    intent.putExtra( "userPw", userPw);
                    intent.putExtra( "userName", userName);
                    intent.putExtra( "userPnum", userPnum);
                    ((Activity) context).startActivityForResult(intent, 5000);
                } else {
                    Intent intent = new Intent(context, SelectTypeAndSectorActivity.class);
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getLocal().equals("충북")) {
                            list.add(userList.get(i));
                        }
                    }
                    intent.putExtra("list", list);
                    intent.putExtra("userId", userId);
                    intent.putExtra( "userPw", userPw);
                    intent.putExtra( "userName", userName);
                    intent.putExtra( "userPnum", userPnum);
                    ((Activity) context).startActivityForResult(intent, 5000);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return contents.size();
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private ImageView imageView;
        private LinearLayout linearLayout;

        ItemViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear);
            textView1 = itemView.findViewById(R.id.textView1);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}