package com.example.task;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AdminActivity extends AppCompatActivity {

    private Button add;
    private Button select;
    private Button refresh;
    private ListView lv;

    private SqlHelper sh = new SqlHelper("localhost", "task", "sa", "123456");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        add = findViewById(R.id.addBtn);
        select = findViewById(R.id.selectBtn);
        refresh = findViewById(R.id.refresh);
        lv = findViewById(R.id.lv);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AdminActivity.this,AddActivity.class);
                startActivity(it);

            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = Message.obtain();
                        String jsonResult = Select();
                        System.out.println("*****"+jsonResult);
                        msg.what = 1;
                        msg.obj = jsonResult;
                        handler.sendMessage(msg);
                    }
                });
                thread.start();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HashMap<String,String> map = (HashMap<String,String>)lv.getItemAtPosition(position);
                        System.out.println(map.toString()+"/*/*/");
                        String user_id = map.get("ID");
                        System.out.println(user_id + "/*/*//*///////");
                        String sql = "delete from admin where ID = ?";
                        List<Object> params = new ArrayList<>();
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
                SimpleAdapter adapter = (SimpleAdapter) lv.getAdapter();
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = Message.obtain();
                        String jsonResult = Select();
                        msg.what = 1;
                        msg.obj = jsonResult;
                        handler.sendMessage(msg);
                    }
                });
                thread.start();
            }
        });

    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String jsonResult = msg.obj.toString();
                    System.out.println(jsonResult);
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<List<Plan>>() {
//                    }.getType();
//                    // 使用gson的fromJson()方法，参数1：json结果，参数2：想要转哪一个类型
//                    List<Plan> plans = gson.fromJson(jsonResult, type);
                    List<Plan> plans = new Gson().fromJson(jsonResult,new TypeToken<List<Plan>>(){}.getType());
                    System.out.println(plans.get(0).id + plans.get(0).name+plans.get(0).password+plans.get(0).type);

                    List<Map<String, String>> mPlans = new ArrayList<>();
                    Map<String, String> title = new HashMap<>();
                    title.put("ID", "账号");
                    title.put("Name", "姓名");
                    title.put("Password", "密码");
                    title.put("Type", "级别");
                    mPlans.add(title);

                    for (Plan plan : plans) {
                        Map<String, String> hmPlans = new HashMap<>();
                        hmPlans.put("ID", plan.id);
                        hmPlans.put("Name", plan.name);
                        hmPlans.put("Password", plan.password);
                        hmPlans.put("Type", plan.type);
                        mPlans.add(hmPlans);
                        Log.e("测试", hmPlans.toString());
                    }

                    SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), mPlans, R.layout.list_item,
                            new String[]{"ID", "Name", "Password", "Type"}, new int[]{R.id.id, R.id.name, R.id.password, R.id.type});
                    lv.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(), "读取成功！", Toast.LENGTH_SHORT).show();
            }
        }
    };
    
    public String Select(){
        String sql = "select [ID] as id,[Name] as name,[Password] as password,[Type] as type from [admin] order by id";
        String jsonResult;
        try {
            jsonResult = sh.ExecuteQuery(sql,new ArrayList<Object>());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return jsonResult;
    }
    
}
