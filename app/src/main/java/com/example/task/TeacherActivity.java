package com.example.task;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TeacherActivity extends AppCompatActivity {
    private Button addStuBtn;
    private Button selectStuBtn;
    private Button stuRefresh;
    private EditText etType;
    private RadioButton rbtnDefault;
    private RadioButton rbAsc;
    private RadioButton rbDesc;
    private EditText searchStu;
    private Button searchStuBtn;
    private ListView lv2;
    private int choose;

    private SqlHelper sh = new SqlHelper("localhost", "task", "sa", "123456");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        addStuBtn = findViewById(R.id.addStuBtn);
        selectStuBtn = findViewById(R.id.selectStuBtn);
        stuRefresh = findViewById(R.id.stuRefresh);
        etType = findViewById(R.id.type);
        rbtnDefault = findViewById(R.id.rbtnDefault);
        rbAsc = findViewById(R.id.asc);
        rbDesc = findViewById(R.id.desc);
        searchStu = findViewById(R.id.searchStu);
        searchStuBtn = findViewById(R.id.searchStuBtn);
        lv2 = findViewById(R.id.lv2);

        addStuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it2 = getIntent();
                String tId = it2.getStringExtra("id");
                Intent it = new Intent(TeacherActivity.this,StuAddActivity.class);
                it.putExtra("tId",tId);
                startActivity(it);
            }
        });

        stuRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (searchStu.getText().toString().trim() != null && searchStu.getText().toString().trim().equals("")){
                            Message msg = Message.obtain();
                            String jsonResult = SelectStu();
                            msg.what = 1;
                            msg.obj = jsonResult;
                            handler.sendMessage(msg);
                        }else{
                            Message msg = Message.obtain();
                            String jsonResult = SelectOneStu();
                            msg.what = 1;
                            msg.obj = jsonResult;
                            handler.sendMessage(msg);
                        }

                    }
                });
                thread.start();
            }
        });

        selectStuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = Message.obtain();
                        String jsonResult = SelectStu();
                        System.out.println(jsonResult);
                        msg.what = 1;
                        msg.obj = jsonResult;
                        handler.sendMessage(msg);
                    }
                });
                thread.start();
            }
        });

        etType = findViewById(R.id.type);
        etType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signProfile();
            }
            private void signProfile() {
                final String[] ttype = getResources().getStringArray(R.array.pick_type);
                AlertDialog.Builder dlg = new AlertDialog.Builder(TeacherActivity.this);
                dlg.setTitle("请选择查询类型");
                dlg.setIcon(null);
                dlg.setCancelable(false);
                dlg.setSingleChoiceItems(ttype, choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choose = which;
                    }
                });
                dlg.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        etType.setText("查询类型为： "+ttype[choose]);
                    }
                });
                dlg.setNegativeButton("取消",null);
                dlg.create();
                dlg.show();
            }
        });

        searchStuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = Message.obtain();
                        String jsonResult = SelectOneStu();
                        System.out.println(jsonResult);
                        msg.what = 1;
                        msg.obj = jsonResult;
                        handler.sendMessage(msg);
                    }
                });
                thread.start();
            }
        });

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                Map<String,Object> map = (Map<String, Object>) listView.getItemAtPosition(position);
                Intent intent = new Intent(TeacherActivity.this,AlterStuActivity.class);
                ArrayList list = new ArrayList();
                Bundle bundle = new Bundle();
                list.add(map);
                for (int i=0;i<list.size();i++)
                {
                    Map  map1=(Map)list.get(i);
                    Iterator iterator = map1.keySet().iterator();
                    while (iterator.hasNext())
                    {
                        String key = (String) iterator.next();
                        Object object = map1.get(key);
                        System.out.println(object+"///////////*/*/*******************");
                    }
                }
                bundle.putParcelableArrayList("data",list);
                Intent it3 = getIntent();
                String tId = it3.getStringExtra("id");
                intent.putExtra("tId",tId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        lv2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HashMap<String,String> map = (HashMap<String,String>)lv2.getItemAtPosition(position);
                        String user_id = map.get("sId");
                        String sql = "delete from Student where StudentID = (?);delete from Score where StudentID = (?)";
                        List<Object> params = new ArrayList<>();
                        params.add(user_id);
                        params.add(user_id);
                        Looper.prepare();
                        try {
                            int count = sh.ExecuteNonQuery(sql,params);
                            if (count == 1){
                                Toast.makeText(getApplicationContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(), "删除失败！", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());

                            Toast.makeText(getApplicationContext(), "操作失败！", Toast.LENGTH_SHORT).show();
                        }
                        Looper.loop();

                    }
                });
                thread.start();
                SimpleAdapter adapter = (SimpleAdapter) lv2.getAdapter();
                adapter.notifyDataSetChanged();
                return false;
            }
        });

    }

    private String SelectOneStu() {
        Intent it = getIntent();
        String tId = it.getStringExtra("id");
        String jsonResult;
        //默认学号排序查询
        String sql = "select stu.[StudentID] as sId,[Name] as name,[Gender] as gender,[Grade] as grade,[Class] as stuClass,[Score]as score\n" +
                "from [Student] as stu,[Score],[Course] \n" ;

        if(etType.getText().toString().trim().equals("请选择查询类型：")){
                sql += "where Course.TeacherID = (?) and stu.StudentID like (?) and stu.StudentID = Score.StudentID and Score.CourseID = Course.CName \n";
                if (rbtnDefault.isChecked()){
                    sql += "order by sId";
                }else if (rbAsc.isChecked()){
                    sql += "order by score asc ";
                }else if (rbDesc.isChecked()){
                    sql += "order by score desc";
                }
        }else if (etType.getText().toString().trim().equals("查询类型为： 学号")) {
            sql += "where Course.TeacherID = (?) and stu.StudentID like (?) and stu.StudentID = Score.StudentID and Score.CourseID = Course.CName \n";
            if (rbtnDefault.isChecked()) {
                sql += "order by sId";
            } else if (rbAsc.isChecked()) {
                sql += "order by score asc ";
            } else if (rbDesc.isChecked()) {
                sql += "order by score desc";
            }
        }else if(etType.getText().toString().trim().equals("查询类型为： 姓名")) {
            sql += "where Course.TeacherID = (?) and Name like (?) and stu.StudentID = Score.StudentID and Score.CourseID = Course.CName \n";
            if (rbtnDefault.isChecked()) {
                sql += "order by sId";
            } else if (rbAsc.isChecked()) {
                sql += "order by score asc ";
            } else if (rbDesc.isChecked()) {
                sql += "order by score desc";
            }
        }else if(etType.getText().toString().trim().equals("查询类型为： 性别")) {
            sql += "where Course.TeacherID = (?) and Gender like (?) and stu.StudentID = Score.StudentID and Score.CourseID = Course.CName \n";
            if (rbtnDefault.isChecked()) {
                sql += "order by sId";
            } else if (rbAsc.isChecked()) {
                sql += "order by score asc ";
            } else if (rbDesc.isChecked()) {
                sql += "order by score desc";
            }
        }else if (etType.getText().toString().trim().equals("查询类型为： 年级")) {
            sql += "where Course.TeacherID = (?) and Grade like(?) and stu.StudentID = Score.StudentID and Score.CourseID = Course.CName \n";
            if (rbtnDefault.isChecked()) {
                sql += "order by sId";
            } else if (rbAsc.isChecked()) {
                sql += "order by score asc ";
            } else if (rbDesc.isChecked()) {
                sql += "order by score desc";
            }
        }else if (etType.getText().toString().trim().equals("查询类型为： 班级")) {
            sql += "where Course.TeacherID = (?) and Class like (?) and stu.StudentID = Score.StudentID and Score.CourseID = Course.CName \n";
            if (rbtnDefault.isChecked()) {
                sql += "order by sId";
            } else if (rbAsc.isChecked()) {
                sql += "order by score asc ";
            } else if (rbDesc.isChecked()) {
                sql += "order by score desc";
            }
        }else if(etType.getText().toString().trim().equals("查询类型为： 分数")) {
            sql += "where Course.TeacherID = (?) and Score like (?) and stu.StudentID = Score.StudentID and Score.CourseID = Course.CName \n";
            if (rbtnDefault.isChecked()) {
                sql += "order by sId";
            } else if (rbAsc.isChecked()) {
                sql += "order by score asc ";
            } else if (rbDesc.isChecked()) {
                sql += "order by score desc";
            }
        }

        String stuMes = "%" + searchStu.getText().toString() +"%";
        List<Object> params = new ArrayList<>();
        params.add(tId);
        params.add(stuMes);
        try {
            jsonResult = sh.ExecuteQuery(sql,params);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return jsonResult;
    }


    private String SelectStu() {
        Intent it = getIntent();
        String tId = it.getStringExtra("id");
        Log.e("错误",tId);
        String jsonResult;
        String sql = "select stu.[StudentID] as sId,[Name] as name,[Gender] as gender,[Grade] as grade,[Class] as stuClass,[Score]as score \n" +
                "from [Student] as stu,[Score],[Course] \n" +
                "where Course.TeacherID =(?) and stu.StudentID = Score.StudentID and Score.CourseID = Course.CName \n" ;
        if (rbtnDefault.isChecked()){
            sql += "order by sId";
        }else if (rbAsc.isChecked()){
            sql += "order by score asc ";
        }else if (rbDesc.isChecked()){
            sql += "order by score desc";
        }
        List<Object> params = new ArrayList<>();
        params.add(tId);
        try {
            jsonResult = sh.ExecuteQuery(sql,params);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return jsonResult;

    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String jsonResult = msg.obj.toString();
                    System.out.println(jsonResult + "//*********///");
                    if(jsonResult.equals("[]") || jsonResult == null){
                        Toast.makeText(getApplicationContext(), "类型数据输入错误,读取失败！", Toast.LENGTH_SHORT).show();
                    }else {
                        List<Student> students = new Gson().fromJson(jsonResult, new TypeToken<List<Student>>() {
                        }.getType());
//                        System.out.println(students.get(0).sId + students.get(0).name + students.get(0).gender + students.get(0).grade
//                                + students.get(0).stuClass + students.get(0).score + "*****");
                        List<Map<String, String>> mStudents = new ArrayList<>();
                        Map<String, String> title = new HashMap<>();
                        title.put("sId", "学号");
                        title.put("Name", "姓名");
                        title.put("Gender", "性别");
                        title.put("Grade", "年级");
                        title.put("stuClass", "班级");
                        title.put("Score", "分数");
                        mStudents.add(title);

                        for (Student student : students) {
                            Map<String, String> hmStudent = new HashMap<>();
                            hmStudent.put("sId", student.sId);
                            hmStudent.put("Name", student.name);
                            hmStudent.put("Gender", student.gender);
                            hmStudent.put("Grade", student.grade);
                            hmStudent.put("stuClass", student.stuClass);
                            hmStudent.put("Score", student.score);
                            mStudents.add(hmStudent);
                            Log.e("测试", hmStudent.toString());
                        }

                        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), mStudents, R.layout.list_item2,
                                new String[]{"sId", "Name", "Gender", "Grade", "stuClass", "Score"},
                                new int[]{R.id.stuID, R.id.stuName, R.id.stuGender, R.id.stuGrade, R.id.stuClass, R.id.stuScore});
                        lv2.setAdapter(adapter);
                        Toast.makeText(getApplicationContext(), "读取成功！", Toast.LENGTH_SHORT).show();
                    }
            }
        }
    };

}
