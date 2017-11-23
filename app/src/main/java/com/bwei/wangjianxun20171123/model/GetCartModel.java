package com.bwei.wangjianxun20171123.model;

import android.os.Handler;
import android.os.Looper;

import com.bwei.wangjianxun20171123.bean.GetCartsBean;
import com.bwei.wangjianxun20171123.model.imodel.IGetCartModel;
import com.bwei.wangjianxun20171123.net.Api;
import com.bwei.wangjianxun20171123.net.HttpUtils;
import com.bwei.wangjianxun20171123.net.OnNetListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 作者：王建勋
 * 时间：2017-11-23 14:59
 * 类的用途：GetCartModel层
 */

public class GetCartModel implements IGetCartModel{
    //创建Handler
    private Handler handler = new Handler(Looper.getMainLooper());

    public void getGetCart(final OnNetListener<GetCartsBean> onNetListener) {
        //创建Map集合添加数据
        Map<String, String> params = new HashMap<>();
        params.put("uid", "71");
        HttpUtils.getHttpUtils().doPost(Api.GETCART, params, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取传递过来的数据
                String string = response.body().string();
                //解析数据
                final GetCartsBean getCartsBean = new Gson().fromJson(string, GetCartsBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.onSuccess(getCartsBean);
                    }
                });
            }
        });
    }
}
