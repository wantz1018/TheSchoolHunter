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
<form action="${pageContext.request.contextPath}/api/addMission" method="get" enctype="multipart/form-data">
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
                <label>
                    <input type="text" name="title" required>
                </label>
            </td>
        </tr>
        <tr>
            <td>
                任务描述
            </td>
            <td>
                <label>
                    <input type="text" name="content">
                </label>
            </td>
        </tr>
        <tr>
            <td>
                任务图片地址
            </td>
            <td>
                <label>
                    <input type="url" name="icon">
                </label>
            </td>
        </tr>
        <tr>
            <td>
                任务时间
            </td>
            <td>
                <label>
                    <input type="date" name="mdate">
                </label>
            </td>
        </tr>
        <tr>
            <td>
                任务地点
            </td>
            <td>
                <label>
                    <input type="text" name="mplace">
                </label>
            </td>
        </tr>
        <tr>
            <td>
                任务鼓励
            </td>
            <td>
                <label>
                    <input type="number" name="rewards">
                </label>
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
<form action="${pageContext.request.contextPath}/api/getMissionList" method="get" enctype="multipart/form-data">
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
                <label>
                    <input type="number" name="page" value="1" required>
                </label>
            </td>
        </tr>
        <tr>
            <td>
                每页数据
            </td>
            <td>
                <label>
                    <input type="number" name="limit" value="5" required>
                </label>
            </td>
        </tr>
        <tr>
            <td>选择地点</td>
            <td>
                <label>
                    <select name="place">
                        <option value="" selected>全部</option>
                        <option value="三江楼">三江楼</option>
                        <option value="三山楼">三山楼</option>
                    </select>
                </label>
            </td>
        </tr>
        <tr>
            <td>选择时间范围</td>
            <td>
                <label>
                    <select name="timeRange">
                        <option value="all" selected>全部</option>
                        <option value="today">今天</option>
                        <option value="threeDays">三天内</option>
                        <option value="fiveDays">五天内</option>
                        <option value="oneWeek">一周内</option>
                    </select>
                </label>
            </td>
        </tr>
        <tr>
            <td>
                排序字段
            </td>
            <td>
                <label>
                    <select name="orderName" required>
                        <option value="m_id" selected>id</option>
                        <option value="deadline">时间</option>
                        <option value="points">奖励</option>
                    </select>
                </label>
            </td>
        </tr>
        <tr>
            <td>排序规则</td>
            <td>
                <label>
                    <input type="radio" value="asc" name="order" checked>
                    升序
                </label>
                <label>
                    <input type="radio" value="desc" name="order">
                    降序
                </label>
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
<form action="${pageContext.request.contextPath}/api/delMission" method="get" enctype="multipart/form-data">
    <table>
        <tr>
            <th>删除任务</th>
        </tr>
        <tr>
            <td>
                输入任务id
            </td>
            <td>
                <label>
                    <input type="text" name="id" required>
                </label>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit">
            </td>
        </tr>
    </table>
</form>
<form action="${pageContext.request.contextPath}/api/receiveMission" method="get">
    <table>
        <tr>
            <th>
                接受任务
            </th>
        </tr>
        <tr>
            <td>
                任务id
            </td>
            <td>
                <label>
                    <input type="number" name="missionId">
                </label>
            </td>
        </tr>
        <tr>
            <td>
                发布者id
            </td>
            <td>
                <label>
                    <input type="number" name="publisherId">
                </label>
            </td>
        </tr>
        <tr>
            <td>
                领取者id
            </td>
            <td>
                <label>
                    <input type="number" name="receiverId">
                </label>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit">
            </td>
        </tr>
    </table>
</form>
<form action="${pageContext.request.contextPath}/api/finishMission" method="get">
    <table>
        <tr>
            <th>
                结束任务
            </th>
        </tr>
        <tr>
            <td>
                id
            </td>
            <td>
                <label>
                    <input type="number" name="taskId">
                </label>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
