<%--
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
</head>
<body style="text-align: center">
    <h1>小花仙 - 根据米米号获取高清形象</h1>
    <hr>
    <form action="screenshotAction/screenshot" method="POST">
        <label>米米号：<input type="number" name="miNum" step="1" min="100000" value="299313080" /></label><br/>
        <label>分辨率宽：<input type="number" name="resolutionX" step="1" min="0" max="7680" value="4320" /></label><br/>
        <label>分辨率高：<input type="number" name="resolutionY" step="1" min="0" max="7680" value="7680" /></label><br/>
        <label>放大倍率：<input type="number" name="scale" step="0.1" min="1" max="8" value="5" /></label><br/>
        <label>视野移动X：<input type="number" name="offsetX" step="1" min="-65536" max="65536" value="-4500" /></label><br/>
        <label>视野移动Y：<input type="number" name="offsetY" step="1" min="-65536" max="65536" value="-4500" /></label><br/>
        <input type="submit" value="提交" />
    </form>

</body>
</html>
