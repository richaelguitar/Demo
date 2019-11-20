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
import com.app.demo.util.ActivityUtils;
import com.app.demo.util.Const;
import com.app.demo.util.LoginUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Date;
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
        String userId = LoginUtils.getLoginInfo(this).getString("userId","222222");
        if("3".equalsIgnoreCase(userId)){
            roomList.add(new Result.DataBean(4));
        }else{
            roomList.add(new Result.DataBean(3));
        }



        if(commonRecyclerViewAdapter == null){
            commonRecyclerViewAdapter = new CommonRecyclerViewAdapter<Result.DataBean>(this,roomList,R.layout.layout_user_item) {
                @Override
                protected void convert(CommonViewHolder viewHolder, final Result.DataBean room) {
                    ((TextView)viewHolder.getView(R.id.tv_user_id)).setText(""+room.getConsumer());
                    if("4".equalsIgnoreCase(userId)){
                        viewHolder.getView(R.id.btn_call_video).setVisibility(View.GONE);
                    }else{
                        viewHolder.getView(R.id.btn_call_video).setVisibility(View.VISIBLE);
                    }
                    viewHolder.getView(R.id.btn_call_video).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //发起视频
                            ActivityUtils.with(UserListActivity.this).startVideo(userId,"room"+System.currentTimeMillis());
                        }
                    });
                }
            };
            recyclerView.setAdapter(commonRecyclerViewAdapter);
        }else{
            commonRecyclerViewAdapter.notifyDataSetChanged();
        }



    }
}
