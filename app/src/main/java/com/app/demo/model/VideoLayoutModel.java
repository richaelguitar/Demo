package com.app.demo.model;

import android.app.Activity;
import android.util.Log;
import android.view.TextureView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.demo.R;
import com.app.demo.widgets.window.FloatingView;


import java.util.ArrayList;
import java.util.LinkedHashMap;


/**
 * 本类为视频通话的推拉流UI布局Model类，由于与业务强相关，实现方式可不相同，这里仅作为示例参考用，业务应根据自己的需求自己实现
 *
 */
public class VideoLayoutModel {

    private ArrayList<String> streamIds = new ArrayList<>();

    private LinkedHashMap<LinearLayout, StreamidAndViewFlag> linearLayoutHasViewLinkedHashMap = new LinkedHashMap<>();

    Activity activity;


    /**
     * 定义一个单个渲染视图模型的内部类
     */
    class StreamidAndViewFlag{
        Boolean layoutHasViewFlag;
        String streamid;
        TextureView renderView;
        StreamidAndViewFlag(){
            this.layoutHasViewFlag = false;
            this.streamid = "";
            this.renderView = null;
        }
    }

    public VideoLayoutModel(Activity activity){

        this.activity = activity;
        LinearLayout llViewContainer = activity.findViewById(R.id.ll_view_container);
        // 这里使用写死的方式创建2个推流或拉流的模型对象

        LinearLayout llHeaderViewContainer = FloatingView.get().getView().findViewById(R.id.ll_header_view);
        StreamidAndViewFlag streamidAndViewFlag1 = new StreamidAndViewFlag();
        linearLayoutHasViewLinkedHashMap.put(llHeaderViewContainer, streamidAndViewFlag1);

        StreamidAndViewFlag streamidAndViewFlag0 = new StreamidAndViewFlag();
        linearLayoutHasViewLinkedHashMap.put(llViewContainer, streamidAndViewFlag0);

    }

    /**
     * 当有新增推流时，调用此方法创建渲染的 TextureView，将其加入到布局中
     *
     * @param streamid
     * @return
     */
    public TextureView addStreamToViewInLayout(String streamid){

        TextureView renderView = new TextureView(activity);
        Log.d("StreamId",""+streamid);
        for(LinearLayout linearLayout : this.linearLayoutHasViewLinkedHashMap.keySet()){

            if(this.linearLayoutHasViewLinkedHashMap.get(linearLayout).layoutHasViewFlag == false){

                this.linearLayoutHasViewLinkedHashMap.get(linearLayout).renderView = renderView;

                this.linearLayoutHasViewLinkedHashMap.get(linearLayout).layoutHasViewFlag = true;
                this.linearLayoutHasViewLinkedHashMap.get(linearLayout).streamid = streamid;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                linearLayout.addView(renderView, layoutParams);
                break;
            }
        }
        return renderView;

    }

    /**
     * 当有流关闭时，调用此方法释放之前渲染的View
     * @param streamid
     */
    public void removeStreamToViewInLayout(String streamid){

        for(LinearLayout linearLayout : this.linearLayoutHasViewLinkedHashMap.keySet()){

            if(this.linearLayoutHasViewLinkedHashMap.get(linearLayout).streamid.equals(streamid)){
                linearLayout.removeView(this.linearLayoutHasViewLinkedHashMap.get(linearLayout).renderView);
                this.linearLayoutHasViewLinkedHashMap.get(linearLayout).renderView = null;
                this.linearLayoutHasViewLinkedHashMap.get(linearLayout).streamid = "";
                this.linearLayoutHasViewLinkedHashMap.get(linearLayout).layoutHasViewFlag = false;
                break;
            }

        }

    }

    /**
     * 当退出时，应释放所有渲染的View
     */
    public void removeAllStreamToViewInLayout(){

        for(LinearLayout linearLayout : this.linearLayoutHasViewLinkedHashMap.keySet()) {

            if (this.linearLayoutHasViewLinkedHashMap.get(linearLayout).layoutHasViewFlag == true) {
                linearLayout.removeView(this.linearLayoutHasViewLinkedHashMap.get(linearLayout).renderView);
                this.linearLayoutHasViewLinkedHashMap.get(linearLayout).renderView = null;
                this.linearLayoutHasViewLinkedHashMap.get(linearLayout).streamid = "";
                this.linearLayoutHasViewLinkedHashMap.get(linearLayout).layoutHasViewFlag = false;

            }
        }

    }
}
