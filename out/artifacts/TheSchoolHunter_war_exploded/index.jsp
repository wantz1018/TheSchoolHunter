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
        <td>
          <input id="file" type="file" name="file">
        </td>
        <td><input type="submit"></td>
      </tr>
    </table>
  </form>
  </body>
</html>
