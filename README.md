# 学生成绩管理系统+图灵聊天机器人课程设计（连接SqlServer数据库，图灵聊天机器人）
* 通过android studio连接SqlServer  
* 通过图灵官网接口，实现聊天机器人功能  
 
# 这是本人的一个课程设计，小白一个，功能也较为简单，是连接本地数据库通过安卓来实现对数据库进行增删改查等操作，记录一下自己的学习过程。  
## 注意点：
* 在代码中的config.class中需要将key修改为你自己在图灵官网中申请的key  
* 同时在其他的class文件中有使用到SqlHelper类的对象需要将其localhost修改为你电脑中的域名  
* 另外MainActivity中的变量connStr也需要将其localhost修改为你电脑中的域名  

# 功能需求分析  
 ![image](https://raw.githubusercontent.com/Black-Polaris/Image/main/1.png)  
 
# 结构分析（实体联系图）
 ![image](https://raw.githubusercontent.com/Black-Polaris/Image/main/2.png)  

# 具体实现效果图（包含三种使用用户：管理员；教师；学生）
## 登录页面
 ![image](https://raw.githubusercontent.com/Black-Polaris/Image/main/3.png)  
 
## 管理员操作页面
### 管理员首页（用户信息）
 ![image](https://raw.githubusercontent.com/Black-Polaris/Image/main/4.png) 
### 添加新用户
 ![image](https://raw.githubusercontent.com/Black-Polaris/Image/main/5.png)  
### 长按删除用户
 ![image](https://raw.githubusercontent.com/Black-Polaris/Image/main/6.png)  

## 教师操作页面
 ![image](https://raw.githubusercontent.com/Black-Polaris/Image/main/7.png)  
### 选择查询类型查询学生信息
 ![image](https://raw.githubusercontent.com/Black-Polaris/Image/main/8.png) ![image](https://raw.githubusercontent.com/Black-Polaris/Image/main/9.png) 
### 添加新学生信息
 ![image](https://raw.githubusercontent.com/Black-Polaris/Image/main/10.png) 
### 修改学生信息
 ![image](https://raw.githubusercontent.com/Black-Polaris/Image/main/11.png)  

## 学生操作页面
### 查询个人成绩
 ![image](https://raw.githubusercontent.com/Black-Polaris/Image/main/12.png)  
 
## 图灵聊天机器人
### 通过点击登录页面的图标进入聊天模式
 ![image](https://raw.githubusercontent.com/Black-Polaris/Image/main/13.png) ![image](https://raw.githubusercontent.com/Black-Polaris/Image/main/14.png)  

