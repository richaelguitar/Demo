package com.app.demo;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.demo.adapter.CommonRecyclerViewAdapter;
import com.app.demo.adapter.CommonViewHolder;
import com.app.demo.adapter.RecycleViewDivider;
import com.app.demo.basic.BaseActivity;
import com.app.demo.entity.Result;
import com.app.demo.util.Const;
import com.app.demo.util.LoginUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class UserListActivity extends BaseActivity {

    private List<Result.DataBean>  roomList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CommonRecyclerViewAdapter<Result.DataBean> commonRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        recyclerView = findViewById(R.id.rv_user_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(false);
        recyclerView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.VERTICAL));
        String roomId = getIntent().getExtras().getString("roomId");
        String userId = LoginUtils.getLoginInfo(this).getString("userId","222222");
        if("666666".equals(userId)){
            roomList.add(new Result.DataBean("888888",roomId));
        }else{
            roomList.add(new Result.DataBean("666666",roomId));
        }


        if(commonRecyclerViewAdapter == null){
            commonRecyclerViewAdapter = new CommonRecyclerViewAdapter<Result.DataBean>(this,roomList,R.layout.layout_user_item) {
                @Override
                protected void convert(CommonViewHolder viewHolder, final Result.DataBean room) {
                    ((TextView)viewHolder.getView(R.id.tv_user_id)).setText(room.getUserId());
                    viewHolder.getView(R.id.btn_call_video).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sendCallNotification(room);
                        }
                    });
                }
            };
            recyclerView.setAdapter(commonRecyclerViewAdapter);
        }else{
            commonRecyclerViewAdapter.notifyDataSetChanged();
        }



    }

    private void sendCallNotification(Result.DataBean room) {
        //发出请求
        OkHttpUtils.get()
                .url(Const.CREATE_ROOM_URL)
                .addParams("userId", ""+room.getUserId())
                .addParams("roomId", ""+room.getRoomId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(UserListActivity.this,"网络请求失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Result result = new Gson().fromJson(response.replaceAll("\\[\\]","{}"),Result.class);
                        if("200".equals(result.getCode())){
                            Intent intent = new Intent(UserListActivity.this, CommunicationVideoUI.class);
                            intent.putExtra("roomId",room.getRoomId());
                            intent.putExtra("userId",room.getUserId());
                            startActivity(intent);
                        }else{
                            Toast.makeText(UserListActivity.this,"网络请求失败",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
