<#import '/pages/layout-main.ftl' as Layout>
<@Layout.adminLayout title='用户管理'>
<script type="text/x-template" id="app-main-template">
    <main class="aui-main">
        <app-breadcrumb :titles="['用户管理','新增用户']"></app-breadcrumb>

        <div class="aui-main__bd">
            <el-card shadow="never">
                <el-form ref="editForm" :model="editForm" label-width="100px" label-suffix=":">
                    <el-row>
                        <el-col :span="11">
                            <el-form-item label="用户名">
                                <el-input v-model="editForm.userName"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="11">
                            <el-form-item label="昵称">
                                <el-input v-model="editForm.nickName"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col :span="11">
                            <el-form-item label="工号">
                                <el-input v-model="editForm.jobNum"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="11">
                            <el-form-item label="手机号">
                                <el-input v-model="editForm.mobileNum"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col :span="11">
                            <el-form-item label="邮箱">
                                <el-input v-model="editForm.email"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="11">
                            <el-form-item label="性别">
                                <el-radio-group v-model="editForm.gender">
                                    <el-radio :label="1">男</el-radio>
                                    <el-radio :label="0">女</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col :span="20" class="t-center">
                            <el-form-item>
                                <el-button type="primary" @click="handleSubmit">确 定</el-button>
                                <el-button @click="handleCancel">返回</el-button>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </el-card>
        </div>
    </main>
</script>
<script>
    VueUtils.registerComponent({
        template: '#app-main-template',
        data: function () {
            return {
                editFormConfig: {
                    visible: false,
                    isAdd: true,
                    title: ''
                },
                editForm: {
                    id: 0,
                    code: '',
                    userName: '',
                    value: '',
                    gender: 1,
                    remark: ''
                }
            }
        },
        created() {
        },
        methods: {
            handleSubmit() {
                HttpRequest.post('/api/user', this.editForm).then(res => {
                    if (res.success) {
                        this.$message.success('新增用户成功')
                        window.history.back()
                    } else {
                        this.$message.error(res.message)
                    }
                })
            },
            handleCancel() {
                window.history.back()
            }
        }
    })
</script>
</@Layout.adminLayout>