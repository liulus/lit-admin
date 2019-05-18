<#import '/pages/layout-main.ftl' as Layout>
<@Layout.adminLayout title='企业信息'>
<script type="text/x-template" id="app-main-template">
    <main class="aui-main">
        <app-breadcrumb :titles="['组织管理','企业信息']"></app-breadcrumb>

        <div class="aui-main__bd">
            <el-card shadow="never">
                <el-form ref="editForm" :model="editForm" label-width="100px" label-suffix=":">
                    <el-row>
                        <el-col :span="11">
                            <el-form-item label="机构名称">
                                <el-input v-model="editForm.fullName"></el-input>
                            </el-form-item>
                            <el-form-item label="邮箱">
                                <el-input v-model="editForm.email"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="11">
                            <el-form-item label="机构代码">
                                <el-input v-model="editForm.code"></el-input>
                            </el-form-item>
                            <el-form-item label="电话">
                                <el-input v-model="editForm.telephone"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col :span="22">
                            <el-form-item label="地址">
                                <el-input v-model="editForm.address"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col :span="22" class="t-center">
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
                editForm: {
                    code: '',
                    fullName: '',
                    shortName: '',
                    address: '',
                    remark: ''
                }
            }
        },
        created() {
            this.initData()
        },
        methods: {
            initData() {
                Lit.httpRequest.get('/api/corporation/info').then(res => {
                    if (res.success) {
                        this.editForm = res.result || {}
                    } else {
                        this.$message.error(res.message)
                    }
                })
            },
            handleSubmit() {
                Lit.httpRequest.post('/api/corporation/info', this.editForm).then(res => {
                    if (res.success) {
                        this.$message.success('保存企业信息成功')
                    } else {
                        this.$message.error(res.message)
                    }
                })
            },
            handleCancel() {
                window.location.href = contextPath + '/organization'
            }
        }
    })
</script>
</@Layout.adminLayout>