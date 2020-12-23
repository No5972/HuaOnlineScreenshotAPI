<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 12/23/2020
  Time: 10:48 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>小花仙 - 根据米米号获取高清形象</title>
    <link rel="stylesheet" href="/layui.css?t=<%=new Date().getTime()%>" media="all">
    <script src="/layui.js?t=<%=new Date().getTime()%>"></script>
</head>
<body class="layui-bg-gray" style="text-align: center">
    <div class="layui-card" style="width: 500px; padding: 30px; margin: 0 auto;">
        <div class="layui-card-header" style="margin-bottom: 20px;"><h1>小花仙 - 根据米米号获取高清形象</h1></div>
        <form class="layui-form" action="screenshotAction/screenshot" method="POST">
            <div class="layui-form-item">
                <label class="layui-form-label">米米号：</label>
                <div class="layui-input-block">
                    <input class="layui-input" type="number" required lay-verify="required" name="miNum" step="1" min="100000" value="299313080" />
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">分辨率宽：</label>
                <div class="layui-input-block">
                    <input class="layui-input" type="number" required lay-verify="required" name="resolutionX" step="1" min="0" max="7680" value="4320" />
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">分辨率高：</label>
                <div class="layui-input-block">
                    <input class="layui-input" type="number" required lay-verify="required" name="resolutionY" step="1" min="0" max="7680" value="7680" />
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">放大倍率：</label>
                <div class="layui-input-block">
                    <input class="layui-input" type="number" required lay-verify="required" name="scale" step="0.1" min="1" max="8" value="5" />
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">视野移动X：</label>
                <div class="layui-input-block">
                    <input class="layui-input" type="number" required lay-verify="required" name="offsetX" step="1" min="-65536" max="65536" value="-4500" />
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">视野移动Y：</label>
                <div class="layui-input-block">
                    <input class="layui-input" type="number" required lay-verify="required" name="offsetY" step="1" min="-65536" max="65536" value="-2000" />
                </div>
            </div>
            <input type="submit" class="layui-btn"  value="获取图片" />
        </form>
    </div>

    <div class="layui-footer" style="margin: 20px;">
        © 2021
        <a href="https://no5972.tk" target="_blank">wujiuqier</a>
    </div>
</body>
</html>
