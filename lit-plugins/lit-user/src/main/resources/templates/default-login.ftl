<!DOCTYPE html>
<html lang="en">
<head>
    <title>登录</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <link rel="stylesheet" href="${rc.contextPath}/libs/bootstrap/3.3.7/css/default/bootstrap.min.css"/>
    <link rel="stylesheet" href="${rc.contextPath}/css/default-login.css">
</head>
<body>
<div class="bs-login">
    <div class="bs-signin">
        <h1>登录</h1>
        <form action="" method="post">
            <fieldset>
                <div class="clearfix holding">
                    <input type="text" class="form-control" name="userName" placeholder="用户名">
                </div>
                <div class="clearfix holding">
                    <input type="password" class="form-control" name="password" placeholder="密码">
                </div>
            </fieldset>
            <div class="alert alert-error alert-danger">
                <button type="button" class="close">×</button>
                <span></span>
            </div>
            <div class="form-horizontal normal-button">
                <input type="submit" class="btn btn-primary" value="登录">
            </div>
        </form>
    </div>
</div>

</body>
</html>