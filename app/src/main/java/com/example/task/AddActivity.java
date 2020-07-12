package com.example.task;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    private EditText addID;
    private EditText addUsername;
    private EditText addPassword;
    private EditText addType;
    private Button addClear;
    private Button addRegister;

    private  String id = "";
    private  String name = "";
    private  String password = "";
    private  String type = "";

    private SqlHelper sh = new SqlHelper("localhost", "task", "sa", "123456");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addID = findViewById(R.id.addID);
        addUsername = findViewById(R.id.addUsername);
        addPassword = findViewById(R.id.addPassword);
        addType = findViewById(R.id.addType);

        addClear = findViewById(R.id.addClear);
        addClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空文本输入框
                addID.setText("");
                addUsername.setText("");
                addPassword.setText("");
                addType.setText("");
                //etId获得焦点
                addID.setFocusable(true);
                addID.setFocusableInTouchMode(true);
                addID.requestFocus();
                addID.requestFocusFromTouch();
            }
        });

        addRegister = findViewById(R.id.addRegister);
        addRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String sql = " INSERT INTO [admin] ([ID],[Name],[Password],[Type]) values (?,?,?,?)";
                        id = addID.getText().toString().trim();
                        name = addUsername.getText().toString().trim();
                        password = addPassword.getText().toString().trim();
                        type = addType.getText().toString().trim();
                        List<Object> params = new ArrayList<>();
                        params.add(id);
                        params.add(name);
                        params.add(password);
                        params.add(type);
                        System.out.println(params.get(0).toString());
                        Looper.prepare();
                        try{
                            int count = sh.ExecuteNonQuery(sql,params);
                            if(count == 1){
                                Toast.makeText(getApplicationContext(),"添加成功！",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getApplicationContext(),"操作失败！",Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            Toast.makeText(getApplicationContext(),"操作失败！",Toast.LENGTH_LONG).show();
                        }
                        Looper.loop();
                    }
                }).start();
            }
        });

    }

}
