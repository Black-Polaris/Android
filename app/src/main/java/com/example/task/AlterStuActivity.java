package com.example.task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlterStuActivity extends AppCompatActivity {

    private EditText alterStuID;
    private EditText alterStuUsername;
    private RadioButton boy;
    private RadioButton girl;
    private EditText alterStuGrade;
    private EditText alterStuClass;
    private EditText alterStuScore;
    private Button alterStuClear;
    private Button alterStu;

    private  String alterstuId = "";
    private  String alterstuName = "";
    private  String alterstuGender = "";
    private  String alterstuGrade = "";
    private  String alterstuClass = "";
    private  String alterstuScore = "";
    Map<String ,Object> map;

    private SqlHelper sh = new SqlHelper("localhost", "task", "sa", "123456");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_stu);

        Intent it = getIntent();
        Bundle bundle = it.getExtras();
        ArrayList list = bundle.getParcelableArrayList("data");
        map = (Map<String,Object>)list.get(0);
        System.out.println(map);
        RefreshData();

        alterStuClear = findViewById(R.id.alterStuClear);
        alterStuClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空文本输入框
                alterStuID.setText("");
                alterStuUsername.setText("");
                boy.setChecked(true);
                girl.setChecked(false);
                alterStuGrade.setText("");
                alterStuClass.setText("");
                alterStuScore.setText("");
                //etId获得焦点
                alterStuID.setFocusable(true);
                alterStuID.setFocusableInTouchMode(true);
                alterStuID.requestFocus();
                alterStuID.requestFocusFromTouch();
            }
        });

        alterStu = findViewById(R.id.alterStu);
        alterStu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String sql1 = " select CName\n" +
                                "from Course\n" +
                                "where   TeacherID = (?)";
                        Intent it = getIntent();
                        String sId = it.getStringExtra("tId");
                        Log.e("添加id",sId);
                        String courseName ;
                        List<Object> params = new ArrayList<>();
                        params.add(sId);
                        courseName = sh.ExecuteQuery(sql1,params);
                        Message msg = Message.obtain();
                        msg.obj = courseName;
                        String jsonResult = msg.obj.toString();
                        System.out.println(jsonResult + "/////");
                        List<Course> courses = new Gson().fromJson(jsonResult,new TypeToken<List<Course>>(){}.getType());
                        String cName = courses.get(0).CName;
                        System.out.println(cName+ "*****");


                        alterstuId = alterStuID.getText().toString().trim();
                        alterstuName = alterStuUsername.getText().toString().trim();

                        if (boy.isChecked()){
                            alterstuGender = "男";
                        }else{
                            alterstuGender = "女";
                        }
                        alterstuGrade= alterStuGrade.getText().toString().trim();
                        alterstuClass = alterStuClass.getText().toString().trim();
                        alterstuScore = alterStuScore.getText().toString().trim();
                        List<Object> params2 = new ArrayList<>();
                        params2.add(alterstuName);
                        params2.add(alterstuGender);
                        params2.add(alterstuGrade);
                        params2.add(alterstuClass);
                        params2.add(alterstuId);
                        params2.add(alterstuScore);
                        params2.add(alterstuId);
                        params2.add(cName);
                        String sql2 = "begin transaction t2\n" +
                                "update Student \n" +
                                "set Name = (?),Gender = (?),Grade = (?),Class = (?)\n" +
                                "where StudentID = (?)\n" +
                                "if @@ERROR != 0\n" +
                                "rollback transaction t2\n" +
                                "save transaction t2\n" +
                                "update Score\n" +
                                "set Score = (?)\n" +
                                "where StudentID = (?) and CourseID = (?)\n" +
                                "if @@ERROR != 0\n" +
                                "rollback transaction t2\n" +
                                "else \n" +
                                "commit transaction t2";
                        System.out.println(params2.get(0).toString());
                        Looper.prepare();
                        try{
                            int count = sh.ExecuteNonQuery(sql2,params2);
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

    private void RefreshData() {
        alterStuID = findViewById(R.id.alterStuID);
        alterStuUsername = findViewById(R.id.alterStuUsername);
        boy = findViewById(R.id.alterboy);
        girl = findViewById(R.id.altergirl);
        alterStuGrade = findViewById(R.id.alterStuGrade);
        alterStuClass = findViewById(R.id.alterStuClass);
        alterStuScore = findViewById(R.id.alterStuScore);

        alterstuId = map.get("sId").toString().trim();
        alterstuName = map.get("Name").toString().trim();
        alterstuGender = map.get("Gender").toString().trim();
        alterstuGrade = map.get("Grade").toString().trim();
        alterstuClass = map.get("stuClass").toString().trim();
        alterstuScore = map.get("Score").toString().trim();

        alterStuID.setText(alterstuId);
        alterStuUsername.setText(alterstuName);
        if (alterstuGender.equals("男")){
            boy.setChecked(true);
        }else {
            girl.setChecked(true);
        }

        alterStuGrade.setText(alterstuGrade);
        alterStuClass.setText(alterstuClass);
        alterStuScore.setText(alterstuScore);
    }
}
