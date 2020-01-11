package com.aidiscern.bill;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketTest extends AppCompatActivity {
    private long sendTime = 0L;
    // 发送心跳包
    private Handler mHandler = new Handler();
    // 每隔2秒发送一次心跳包，检测连接没有断开
    private static final long HEART_BEAT_RATE = 2 * 1000;

    // 发送心跳包
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            if (System.currentTimeMillis() - sendTime >= HEART_BEAT_RATE) {

                String message = sendData();
                if (null == mSocket){
                    Log.e("YM","mSocket为null");
                    return;
                }
                mSocket.send(message);
                sendTime = System.currentTimeMillis();
            }
            mHandler.postDelayed(this, HEART_BEAT_RATE); //每隔一定的时间，对长连接进行一次心跳检测
        }
    };

    private WebSocket mSocket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.websocket_test);
        setListener();
    }


    private void setListener() {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3, TimeUnit.SECONDS)//设置连接超时时间
                .build();

        Request request = new Request.Builder().url("ws://192.168.72.109:6999/websocket")
                .addHeader("token","09cab95031ef67eea25e38999a0ec59c")
// WebSocket不能使用post请求，因为他不是http请求，是无状态协议
//                .post(RequestBody.create("{'11':'22'}", MediaType.parse("application/json")))
                .build();
        EchoWebSocketListener socketListener = new EchoWebSocketListener();

        // 刚进入界面，就开启心跳检测
        mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);

        mOkHttpClient.newWebSocket(request, socketListener);
        mOkHttpClient.dispatcher().executorService().shutdown();

    }
    private final class EchoWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            mSocket = webSocket;
//            String openid = "1";
            //连接成功后，发送登录信息
//            String message = sendData();
//            mSocket.send(message);
            output("连接成功！");


        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
            output("receive bytes:" + bytes.hex());
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            output("服务器端发送来的信息：" + text);
            // {"msg":"付款成功","amount":"null","code":"0","qrCode":"123456","data":"cn.pay.entity.QCPOrder@3de382a5","userId":"f"}

            // 这里自己用于测试断开连接：就直接在接收到服务器发送的消息后，然后断开连接，然后清除 handler，
            //具体可以根据自己实际情况断开连接，比如点击返回键页面关闭时，执行下边逻辑
            if (!TextUtils.isEmpty(text)){
                if (mSocket  != null) {
                    mSocket .close(1000, null);
                }
                if (mHandler != null){
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler = null ;
                }
            }
            /*//收到服务器端发送来的信息后，每隔2秒发送一次心跳包
            final String message = sendHeart();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mSocket.send(message);
                }
            },2000);*/
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            output("closed:" + reason);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            output("closing:" + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            output("failure:" + t.getMessage());
        }
    }


    private void output(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG" , "text: " + text) ;
            }
        });
    }



    private String sendData() {
        String jsonHead="";
        Map<String,Object> mapHead=new HashMap<>();
        mapHead.put("qrCode", "123456") ;
        jsonHead=buildRequestParams(mapHead);
        Log.e("TAG" , "sendData: " + jsonHead) ;
        return jsonHead ;
    }


    public  static String buildRequestParams(Object params){
        Gson gson=new Gson();
        String jsonStr=gson.toJson(params);
        return jsonStr;
    }

    private String sendHeart() {
        String jsonHead="";
        Map<String,Object> mapHead=new HashMap<>();
        mapHead.put("heart", "heart") ;
        jsonHead=buildRequestParams(mapHead);
        Log.e("TAG" , "sendHeart：" + jsonHead) ;
        return jsonHead ;
    }
}