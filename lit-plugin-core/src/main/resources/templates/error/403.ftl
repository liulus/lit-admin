<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
</head>
<body>

<p>您未登录或登录信息已过期, 请重新登录</p>

<button onclick="window.history.back()">返回</button>

<a href="${springMacroRequestContext.contextPath}/user/login">去登录</a>


</body>
</html>