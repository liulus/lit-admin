<!DOCTYPE html>
<html lang="en">
<head>
    <title>欢迎注册</title>
    <#include "/pages/html-header.ftl"/>
</head>

<body>
<div v-cloak id="app" class="aui-page page-login">
    <div class="login-wrapper">
        <!-- page-header -->
        <header class="aui-page-header">
            <h2 class="aui-brand">lit-admin</h2>
            <ul class="aui-intro">
                <li>Out-of-the-box Admin system.</li>
            </ul>
        </header>
        <!-- page-main -->
        <main class="aui-page-main">
            <h3 class="login-title">注册</h3>
            <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmitHandle()">
                <el-form-item prop="mobileNum">
                    <el-input v-model="dataForm.mobileNum" placeholder="手机号码">
                        <i slot="prefix" class="el-input__icon ic icall"></i>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="dataForm.password" type="password" placeholder="密码">
                        <i slot="prefix" class="el-input__icon ic icall"></i>
                    </el-input>
                </el-form-item>
                <el-form-item prop="confirmPassword">
                    <el-input v-model="dataForm.confirmPassword" type="password" placeholder="确认密码">
                        <i slot="prefix" class="el-input__icon ic icall"></i>
                    </el-input>
                </el-form-item>
                <el-form-item prop="smsCaptcha">
                    <el-input v-model="dataForm.smsCaptcha" placeholder="短信验证码">
                        <i slot="prefix" class="el-input__icon ic icall"></i>
                        <el-button slot="append">获取验证码</el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="agreement" size="mini">
                    <div class="t-left">
                        <el-checkbox v-model="dataForm.agreement">已阅读并同意</el-checkbox>
                        <a href="#">服务条款</a>
                    </div>
                </el-form-item>
                <el-form-item>
                    <el-button class="w-100" type="primary" @click="dataFormSubmitHandle()">注 册</el-button>
                </el-form-item>
            </el-form>
            <div class="aui-shortcut">
                <h4 class="aui-shortcut__title"><span>使用社交账号直接登录</span></h4>
                <ul class="aui-shortcut__list">
                    <li>
                        <a href="#" style="color: #191717;">
                            <i class="ic icall"></i>
                        </a>
                    </li>
                    <li>
                        <a href="#" style="color: #4bb772;">
                            <i class="ic icall"></i>
                        </a>
                    </li>
                    <li>
                        <a href="#" style="color: #26a4de;">
                            <i class="ic icall"></i>
                        </a>
                    </li>
                    <li>
                        <a href="#" style="color: #ec5c58;">
                            <i class="ic icall"></i>
                        </a>
                    </li>
                </ul>
            </div>
            <p class="login-guide">已经注册过？<a href="${springMacroRequestContext.contextPath}/login.html">立即登录</a></p>
        </main>
    </div>
</div>

<#include "/pages/html-script.ftl"/>
<script>
    (function () {
        let vm = new Vue({
            el: '#app',
            data: function () {
                return {
                    dataForm: {
                        mobileNum: '',
                        password: '',
                        confirmPassword: '',
                        smsCaptcha: '',
                        agreement: false
                    },
                    dataRule: {
                        mobileNum: [
                            {required: true, message: '必填项不能为空', trigger: 'blur'}
                        ],
                        password: [
                            {required: true, message: '必填项不能为空', trigger: 'blur'}
                        ],
                        confirmPassword: [
                            {required: true, message: '必填项不能为空', trigger: 'blur'}
                        ],
                        smsCaptcha: [
                            {required: true, message: '必填项不能为空', trigger: 'blur'}
                        ],
                        agreement: [
                            {
                                validator: function (rule, value, callback) {
                                    if (!value) {
                                        callback(new Error('请先阅读并勾选注册协议'));
                                    } else {
                                        callback();
                                    }
                                }, trigger: 'change'
                            }
                        ]
                    }
                }
            },
            methods: {
                dataFormSubmitHandle() {
                    this.$refs['dataForm'].validate(valid => {
                        if (valid) {
                            vm.registerUser()
                        }
                    })
                },
                registerUser() {
                    Lit.httpRequest.post('/api/user/register', this.dataForm).then(res => {
                        if (res.success) {
                            this.$message.success('注册成功')
                        } else {
                            this.$message.error(res.message)
                        }
                    })
                }
            }
        });
    })();
</script>
</body>

</html>