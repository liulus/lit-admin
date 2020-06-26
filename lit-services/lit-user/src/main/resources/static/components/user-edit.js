define(['vue', 'Lit'], function (Vue, Lit) {
    let tmpl = `
<main class="aui-main">
    <app-breadcrumb :titles="['用户管理', title]"></app-breadcrumb>

    <div class="aui-main__bd">
        <el-card shadow="never">
            <el-form ref="editForm" :model="editForm" label-width="100px" label-suffix=":">
                <el-row>
                    <el-col :span="22" class="t-center">
                        <span></span>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="11">
                        <el-form-item label="用户名">
                            <el-input v-model="editForm.username"></el-input>
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
                            <el-button @click="handleCancel">返 回</el-button>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        </el-card>
    </div>
</main>
    `
    return Vue.component('user-edit', {
        template: tmpl,
        props: {
            isAdd: Boolean
        },
        data: function () {
            return {
                editFormConfig: {
                    visible: false,
                    isAdd: true,
                    title: this.isAdd ? '新增用户' : '修改用户信息'
                },
                editForm: {
                    id: 0,
                    code: '',
                    username: '',
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
                Lit.httpRequest.post('/api/user', this.editForm).then(res => {
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
})