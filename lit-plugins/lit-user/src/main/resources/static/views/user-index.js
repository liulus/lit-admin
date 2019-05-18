define(['Lit'], function (Lit) {
    let tmpl = `
<main class="aui-main">
    <app-breadcrumb title="用户管理"></app-breadcrumb>

    <div class="aui-main__bd">
        <el-card shadow="never">
            <div slot="header">
                <el-row>
                    <el-col :span="4">
                        <el-button type="primary" plain icon="el-icon-plus" @click="handleAdd"></el-button>
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
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.row.id)"></el-button>
                        <el-button type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"></el-button>
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
    `
    return {
        template: tmpl,
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
            Lit.appendStyle('.el-table--medium td, .el-table--medium th {padding: 3px 0;}')
            this.initData()
        },
        methods: {
            initData() {
                Lit.httpRequest.get('/api/user/list', this.queryForm).then(res => {
                    this.dataList = res.result.data || []
                    this.page = res.result.pageInfo
                })
            },
            handleSearch() {
                this.queryForm.pageNum = 1
                this.initData()
            },
            handleAdd() {
                redirect('/user/add')
            },
            handleEdit(id) {
                redirect('user/edit/' + id)
            },
            handleDelete(row) {
                this.$confirm(`确定删除用户 ${row.userName} ?`, '提示', {
                    closeOnClickModal: false,
                    type: 'warning'
                }).then(() => {
                    console.log('233323')
                    Lit.httpRequest.delete('/api/user/' + row.id).then(res => {
                        if (res.success) {
                            this.$message.success('删除用户成功')
                            this.initData()
                        } else {
                            this.$message.error(res.message)
                        }
                    })
                }).catch(() => {
                })
            },
            handlePageChange(pageNum) {
                this.queryForm.pageNum = pageNum;
                this.initData()
            }
        }
    }
})