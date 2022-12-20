<%--
  Created by IntelliJ IDEA.
  Author:Wantz
  Email:wantz@foxmail.com
  Date: 2022/11/11
  Time: 17:54
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>The School Hunter</title>
</head>
<body>
<h1>The School Hunter</h1>
<h2>——校园猎人</h2>
<form action="${pageContext.request.contextPath}/api/upload" method="post" enctype="multipart/form-data">
    <table>
        <tr>
            <th>
                文件上传
            </th>
        </tr>
        <tr>
            <td>
                <input id="file" type="file" name="file">
            </td>
            <td><input type="submit"></td>
        </tr>
    </table>
</form>
<form action="${pageContext.request.contextPath}/api/addMission" method="post">
    <table>
        <tr>
            <th>
                添加任务
            </th>
        </tr>
        <tr>
            <td>
                任务名称
            </td>
            <td>
                <input type="text" name="title" required>
            </td>
        </tr>
        <tr>
            <td>
                任务描述
            </td>
            <td>
                <input type="text" name="content">
            </td>
        </tr>
        <tr>
            <td>
                任务图片地址
            </td>
            <td>
                <input type="url" name="icon">
            </td>
        </tr>
        <tr>
            <td>
                任务时间
            </td>
            <td>
                <input type="datetime-local" name="mdate">
            </td>
        </tr>
        <tr>
            <td>
                任务地点
            </td>
            <td>
                <input type="text" name="mplace">
            </td>
        </tr>
        <tr>
            <td>
                任务鼓励
            </td>
            <td>
                <input type="number" name="rewards">
            </td>
        </tr>
        <tr>
            <td>
                <input type="reset">
            </td>
            <td>
                <input type="submit">
            </td>
        </tr>
    </table>
</form>
<form action="${pageContext.request.contextPath}/api/getMissionList" method="post">
    <table>
        <tr>
            <th>
                获取任务
            </th>
        </tr>
        <tr>
            <td>
                获取页数
            </td>
            <td>
                <input type="number" name="page" value="1" required>
            </td>
        </tr>
        <tr>
            <td>
                每页数据
            </td>
            <td>
                <input type="number" name="limit" value="5" required>
            </td>
        </tr>
        <tr>
            <td>选择地点</td>
            <td>
                <input type="text" name="place">
            </td>
        </tr>
        <tr>
            <td>选择时间范围</td>
            <td>
                <input type="text" name="timeRange">
            </td>
        </tr>
        <tr>
            <td>
                排序字段
            </td>
            <td>
                <input type="text" name="orderName" value="id" required>
            </td>
        </tr>
        <tr>
            <td>排序规则</td>
            <td>
                <input type="radio" value="asc" name="order" checked>
                <input type="radio" value="desc" name="order">
            </td>
        </tr>
        <tr>
            <th>
                <input type="reset">
            </th>
            <td>
                <input type="submit">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
