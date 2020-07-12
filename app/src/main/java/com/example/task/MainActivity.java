package com.example.task;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    private Connection con;
    private String drive = "net.sourceforge.jtds.jdbc.Driver";
    private String connStr = "jdbc:jtds:sqlserver://localhost:1433/task";
    private PreparedStatement pstm;
    private EditText etId ;
    private EditText etPassword;
    private Button loginBtn;
    private Button clear;
    private Button robot;
    private String id = "000";
    private String password = "123456";


    private SqlHelper sh = new SqlHelper("localhost", "task", "sa", "123456");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        clear = findViewById(R.id.clear);
        loginBtn = findViewById(R.id.loginBtn);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etId.setText("");
                etPassword.setText("");
                etId.setFocusable(true);
                etId.setFocusableInTouchMode(true);
                etId.requestFocus();
                etId.requestFocusFromTouch();
            }
        });

        robot = findViewById(R.id.robot);
        robot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this,ChatActivity.class);
                startActivity(it);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(etId.getText().toString().equals(id) && etPassword.getText().toString().equals(password)){
                    Intent it = new Intent(MainActivity.this,AdminActivity.class);
                    startActivity(it);

                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Class.forName(drive);
                                con = DriverManager.getConnection(connStr,"sa","123456");
                                pstm = con.prepareStatement("select [ID] as id,[Password] as password ,Type  from admin  where ID like ? and Password like ?");
                                pstm.setString(1,etId.getText().toString());
                                pstm.setString(2,etPassword.getText().toString());
                                ResultSet rs = pstm.executeQuery();
                                if (rs.next() == false){
                                    Looper.prepare();
                                    Toast.makeText(getApplicationContext(),"账号或密码输入错误",Toast.LENGTH_LONG).show();
                                    Looper.loop();
                                }else{
                                    pstm = con.prepareStatement("select Type from admin where ID = ? and Password = ?");
                                    pstm.setString(1,etId.getText().toString().trim());
                                    pstm.setString(2,etPassword.getText().toString().trim());
                                    ResultSet rs2 = pstm.executeQuery();
                                    ResultSetMetaData resultSetMetaData = rs2.getMetaData();
                                    String columnName = resultSetMetaData.getColumnLabel(1);
                                    String value = rs.getString(columnName);
                                    if(value.equals("1")){
                                        Intent it = new Intent(MainActivity.this, TeacherActivity.class);
                                        it.putExtra("id",etId.getText().toString().trim());
                                        startActivity(it);

                                    }else if(value.equals("2")){
                                        Intent it = new Intent(MainActivity.this,StudentActivity.class);
                                        it.putExtra("id",etId.getText().toString().trim());
                                        startActivity(it);

                                    }
                                }
                                pstm.close();
                                con.close();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }


    }
});
    }
}