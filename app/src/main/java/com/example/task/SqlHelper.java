package com.example.task;


import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class SqlHelper {
    private String drive = "net.sourceforge.jtds.jdbc.Driver";
    private String connStr;
    private String server;
    private String dbName;
    private String userName;
    private String userPwd;
    private Connection con;
    private PreparedStatement pstm;

    public SqlHelper(String server, String dbName, String userName, String userPwd) {
        this.server = server;
        this.dbName = dbName;
        this.connStr = "jdbc:jtds:sqlserver://" + this.server + ":1433/" + this.dbName;
        this.userName = userName;
        this.userPwd = userPwd;

        try {
            Class.forName(drive);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int ExecuteNonQuery(String sql, List<Object> params) {
        try {
            con = DriverManager.getConnection(this.connStr, this.userName, this.userPwd);
            pstm = con.prepareStatement(sql);
            if (params != null && !params.equals("")) {
                for (int i = 0; i < params.size(); i++) {
                    pstm.setObject(i + 1, params.get(i));
                }
            }
            return  pstm.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return -1;
        } finally {
            try {
                pstm.close();
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public String ExecuteQuery(String sql, List<Object> params) {
        // TODO Auto-generated method stub
        JSONArray jsonArray = new JSONArray();
        try {
            con = DriverManager.getConnection(this.connStr, this.userName, this.userPwd);
            pstm = con.prepareStatement(sql);
            if (params != null && !params.equals("")) {
                for (int i = 0; i < params.size(); i++) {
                    pstm.setObject(i + 1, params.get(i));
                }
            }
            ResultSet rs = pstm.executeQuery();
            ResultSetMetaData rsMetaData = rs.getMetaData();
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < rsMetaData.getColumnCount(); i++) {
                    String columnName = rsMetaData.getColumnLabel(i + 1);
                    String value = rs.getString(columnName);
                    jsonObject.put(columnName, value);
                }
                jsonArray.put(jsonObject);
            }
            return jsonArray.toString();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        } finally {
            try {
                pstm.close();
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }





}