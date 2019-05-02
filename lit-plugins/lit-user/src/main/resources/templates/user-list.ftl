<#import 'layout/layout-main.ftl' as Layout>
<@Layout.adminLayout title='用户管理'>
<script type="text/x-template" id="app-main-template">
    <main class="aui-main">
        <app-breadcrumb title="用户管理"></app-breadcrumb>

        <div class="aui-main__bd">
            <el-card shadow="never">
                <div slot="header">
                    <el-row>
                        <el-col :span="4">
                            <el-button type="primary" plain icon="el-icon-plus" size="medium"
                                       @click="handleAdd"></el-button>
                        </el-col>
                        <el-col :span="12" :offset="2">
                            <el-input v-model="queryForm.keyword" placeholder="请输入搜索内容"
                                      v-on:keyup.native.enter="handleSearch">
                                <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
                            </el-input>
                        </el-col>
                    </el-row>
                </div>

                <el-table :data="dataList">
                    <el-table-column prop="userName" label="用户名"></el-table-column>
                    <el-table-column prop="mobileNum" label="手机号"></el-table-column>
                    <el-table-column prop="gender" label="性别">
                        <template slot-scope="scope">
                            <span>{{scope.row.gender === 1 ? '男' : '女'}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="150px">
                        <template slot-scope="scope">
                            <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.row)"></el-button>
                            <el-button type="text" icon="el-icon-delete"
                                       @click="handleDelete(scope.row.id)"></el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <el-pagination v-if="page" background layout="prev, pager, next, total"
                               :current-page="page.pageNum" :page-size="page.pageSize" :total="page.totalRecord"
                               @current-change="handlePageChange">
                </el-pagination>
            </el-card>
        </div>
    </main>
</script>
<script>
    VueUtils.registerComponent({
        template: '#app-main-template',
        data: function () {
            return {
                queryForm: {
                    keyword: '',
                    pageNum: 1,
                    pageSize: 10
                },
                dataList: [],
                page: {},
                editFormConfig: {
                    visible: false,
                    isAdd: true,
                    title: ''
                },
                editForm: {
                    id: 0,
                    code: '',
                    value: '',
                    remark: ''
                }
            }
        },
        created() {
            this.initData()
        },
        methods: {
            initData() {
                HttpRequest.get('/api/user/list', this.queryForm).then(res => {
                    this.dataList = res.result.data || []
                    this.page = res.result.pageInfo
                })
            },
            handleSearch() {
                this.queryForm.pageNum = 1
                this.initData()
            },
            handleAdd() {
                window.location.href = contextPath + '/user/add'
            },
            handleEdit(node) {
                this.editForm = node

                this.editFormConfig.title = '修改参数'
                this.editFormConfig.isAdd = false
                this.editFormConfig.visible = true
            },
            doEdit() {
                let method = this.editFormConfig.isAdd ? 'post' : 'put'
                HttpRequest.request(method, '/api/param', this.editForm).then(res => {
                    if (res.success) {
                        this.$message.success(this.editFormConfig.title + '成功')
                        this.initData()
                    } else {
                        this.$message.error(res.message)
                    }
                })
                this.editFormConfig.visible = false
            },
            handlePageChange(pageNum) {
                this.queryForm.pageNum = pageNum;
                this.initData()
            }
        }
    })
</script>
<style>
    .el-table--medium td, .el-table--medium th {
        padding: 3px 0;
    }
</style>
</@Layout.adminLayout>