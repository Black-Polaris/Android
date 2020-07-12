package com.example.task;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentActivity extends AppCompatActivity {
    private Button selectScoreBtn;
    private ListView lv3;
    private SqlHelper sh = new SqlHelper("localhost", "task", "sa", "123456");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        lv3 = findViewById(R.id.lv3);
        selectScoreBtn = findViewById(R.id.selectScoreBtn);
        selectScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = Message.obtain();
                        String jsonResult = SelectScore();
                        System.out.println(jsonResult);
                        msg.what = 1;
                        msg.obj = jsonResult;
                        handler.sendMessage(msg);
                    }
                });
                thread.start();
            }
        });
    }

    private String SelectScore() {
        Intent it = getIntent();
        String sId = it.getStringExtra("id");
        Log.e("错误",sId);
        String jsonResult;
        String sql = "select CourseID,Score,Term\n" +
                "from Score\n" +
                "where StudentID = (?)\n" +
                "order by Term";
        List<Object> params = new ArrayList<>();
        params.add(sId);
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
                    System.out.println(jsonResult + "/////");
                    List<Score> scores = new Gson().fromJson(jsonResult,new TypeToken<List<Score>>(){}.getType());
                    System.out.println(scores.get(0).CourseID + scores.get(0).Score+scores.get(0).Term+ "*****");
                    List<Map<String, String>> mScores = new ArrayList<>();
                    Map<String, String> title = new HashMap<>();
                    title.put("CourseID", "课程");
                    title.put("Score", "分数");
                    title.put("Term", "学期");
                    mScores.add(title);

                    for (Score score : scores) {
                        Map<String, String> hmScore = new HashMap<>();
                        hmScore.put("CourseID", score.CourseID);
                        hmScore.put("Score", score.Score);
                        hmScore.put("Term", score.Term);
                        mScores.add(hmScore);
                        Log.e("测试", hmScore.toString());
                    }

                    SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), mScores, R.layout.list_item3,
                            new String[]{"CourseID", "Score", "Term"},
                            new int[]{R.id.courseID, R.id.Score, R.id.Term});
                    lv3.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(), "读取成功！", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
