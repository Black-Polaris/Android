package com.example.task;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends Activity {

    private List<ChatMessage> list;
    private ListView listView;
    private EditText et_content;
    private Button btn_send;
    private ChatAdapter chatAdapter;
    private ChatMessage chatMessage = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
        initView();
        initListener();
        initData();
    }

    // 1.初始试图
    private void initView() {
        // 1.初始化
        listView = (ListView) findViewById(R.id.listView);
        et_content = (EditText) findViewById(R.id.et_content);
        btn_send = (Button) findViewById(R.id.btn_send);
    }
    // 2.设置监听事件
    private void initListener() {
        btn_send.setOnClickListener(onClickListener);
    }

    // 3.初始化数据
    private void initData() {
        list = new ArrayList<ChatMessage>();
        list.add(new ChatMessage("你好呀!我是你的聊天机器人", ChatMessage.Type.INCOUNT));
        chatAdapter = new ChatAdapter(list);
        listView.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }

    // 4.发送消息聊天
    private void chat() {
        // 1.判断是否输入内容
        final String send_message = et_content.getText().toString().trim();
        if (TextUtils.isEmpty(send_message)) {
            Toast.makeText(ChatActivity.this, "对不起，您还未发送任何消息",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // 2.自己输入的内容也是一条记录，记录刷新
        ChatMessage sendChatMessage = new ChatMessage();
        sendChatMessage.setMessage(send_message);
        sendChatMessage.setType(ChatMessage.Type.OUTCOUNT);
        list.add(sendChatMessage);
        chatAdapter.notifyDataSetChanged();
        et_content.setText("");

        // 3.发送你的消息，去服务器端，返回数据
        new Thread() {
            public void run() {
                ChatMessage chat = HttpUtils.sendMessage(send_message);
                Message message = new Message();
                message.what = 0x1;
                message.obj = chat;
                handler.sendMessage(message);
            };
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x1) {
                if (msg.obj != null) {
                    chatMessage = (ChatMessage) msg.obj;
                }
                // 添加数据到list中，更新数据
                list.add(chatMessage);
                chatAdapter.notifyDataSetChanged();
            }
        }
    };

    // 点击事件监听
    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_send:
                    chat();
                    break;
            }
        }
    };



}
