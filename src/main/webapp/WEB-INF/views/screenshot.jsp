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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui.css?t=<%=new Date().getTime()%>" media="all">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico" />
    <script src="${pageContext.request.contextPath}/layui.js?t=<%=new Date().getTime()%>"></script>
</head>
<body class="layui-bg-gray" style="text-align: center">
    <div class="layui-card" style="width: 500px; padding: 30px; margin: 0 auto;">
        <div class="layui-card-header" style="margin-bottom: 20px;"><h1>小花仙 - 根据米米号获取高清形象</h1></div>
        <form class="layui-form" action="screenshotAction/screenshot.png" method="POST">
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
                    <input class="layui-input" type="number" required lay-verify="required" name="scale" step="0.1" min="1" max="8" value="4" />
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
        <hr>
        <p style="text-align: left !important;">说明：可能加载会比较慢，因为是服务器模拟鼠标点击小花仙Flash花灵派对的按钮来加载指定米米号用户的面板，
            然后使用后台控制模拟浏览器的开发者工具来做高清截图。所以目前暂不支持同时多个用户操作，
            同时只允许一个截图过程进行，因此如果遇到503功能正在被占用，请稍等一段时间后再尝试。
            此外服务器每3个小时会自动重启，重启期间短时间将无法访问，此举用以节省服务器系统资源特别是运行内存的资源，
            否则浏览器Flash随时会发生崩溃，崩溃后所有截图请求都将无法进行。</p>
    </div>

    <div class="layui-footer" style="margin: 20px;">
        © 2021
        <a href="https://no5972.tk" target="_blank">wujiuqier</a>
    </div>
</body>
</html>
