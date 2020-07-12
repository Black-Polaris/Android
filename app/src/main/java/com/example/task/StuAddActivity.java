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

public class StuAddActivity extends AppCompatActivity {
    private EditText addStuID;
    private EditText addStuUsername;
    private RadioButton boy;
    private RadioButton girl;
    private EditText addStuGrade;
    private EditText addStuClass;
    private EditText addStuScore;
    private Button addStuClear;
    private Button addStuRegister;

    private  String stuId = "";
    private  String stuName = "";
    private  String stuGender = "";
    private  String stuGrade = "";
    private  String stuClass = "";
    private  String stuScore = "";

    private SqlHelper sh = new SqlHelper("localhost", "task", "sa", "123456");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_add);

        addStuID = findViewById(R.id.addStuID);
        addStuUsername = findViewById(R.id.addStuUsername);
        boy = findViewById(R.id.boy);
        girl = findViewById(R.id.girl);
        addStuGrade = findViewById(R.id.addStuGrade);
        addStuClass = findViewById(R.id.addStuClass);
        addStuScore = findViewById(R.id.addStuScore);
        addStuClear = findViewById(R.id.addStuClear);
        addStuClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空文本输入框
                addStuID.setText("");
                addStuUsername.setText("");
                addStuGrade.setText("");
                addStuClass.setText("");
                addStuScore.setText("");
                //etId获得焦点
                addStuID.setFocusable(true);
                addStuID.setFocusableInTouchMode(true);
                addStuID.requestFocus();
                addStuID.requestFocusFromTouch();
            }
        });

        addStuRegister = findViewById(R.id.addStuRegister);
        addStuRegister.setOnClickListener(new View.OnClickListener() {
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

                        stuId = addStuID.getText().toString().trim();
                        stuName = addStuUsername.getText().toString().trim();
                        if (boy.isChecked()){
                            stuGender = "男";
                        }else{
                            stuGender = "女";
                        }
                        stuGrade= addStuGrade.getText().toString().trim();
                        stuClass = addStuClass.getText().toString().trim();
                        stuScore = addStuScore.getText().toString().trim();
                        List<Object> params2 = new ArrayList<>();
                        params2.add(stuId);
                        params2.add(stuName);
                        params2.add(stuGender);
                        params2.add(stuGrade);
                        params2.add(stuClass);
                        params2.add(stuId);
                        params2.add(cName);
                        params2.add(stuScore);
                        String sql2 = "begin Transaction t1\n" +
                                "insert Student (StudentID,Name,Gender,Grade,Class)\n" +
                                "values (?,?,?,?,?)\n" +
                                "if @@ERROR != 0\n" +
                                "rollback transaction t1\n" +
                                "save transaction t1\n" +
                                "insert Score (StudentID,CourseID,Score)\n" +
                                "values (?,?,?)\n" +
                                "if @@ERROR != 0\n" +
                                "rollback transaction t1\n" +
                                "else \n" +
                                "commit transaction t1\n";
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
}
