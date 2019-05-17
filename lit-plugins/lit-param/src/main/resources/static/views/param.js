define(function () {
    let tmpl = `
<main class="aui-main">
    <app-breadcrumb title="参数管理"></app-breadcrumb>

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
                <el-table-column prop="code" label="参数编码"></el-table-column>
                <el-table-column prop="value" label="参数值"></el-table-column>
                <el-table-column prop="remark" label="备注"></el-table-column>
                <el-table-column label="操作" width="150px">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.row)"></el-button>
                        <el-button type="text" icon="el-icon-delete" @click="handleDelete(scope.row.id)"></el-button>
                    </template>
                </el-table-column>
            </el-table>

            <el-pagination v-if="page" background layout="prev, pager, next, total"
                           :current-page="page.pageNum" :page-size="page.pageSize" :total="page.totalRecord"
                           @current-change="handlePageChange">
            </el-pagination>
        </el-card>
    </div>

    <el-dialog :title="editFormConfig.title" :visible.sync="editFormConfig.visible" :close-on-click-modal="false">
        <el-form :model="editForm" label-width="100px" label-suffix=":">
            <el-form-item label="参数编码">
                <el-input v-model="editForm.code"></el-input>
            </el-form-item>
            <el-form-item label="参数值">
                <el-input v-model="editForm.value"></el-input>
            </el-form-item>
            <el-form-item label="备注">
                <el-input type="textarea" :rows="2" v-model="editForm.remark"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="doEdit">确 定</el-button>
            <el-button @click="editFormConfig.visible = false">取 消</el-button>
        </div>
    </el-dialog>
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
            appendStyle('.el-table--medium td, .el-table--medium th {padding: 3px 0;}', 'param')
            this.initData()
        },
        destroyed: function() {
            removeStyle('param')
        },
        methods: {
            initData() {
                HttpRequest.get('/api/param/list', this.queryForm).then(res => {
                    this.dataList = res.result.data || []
                    this.page = res.result.pageInfo
                })
            },
            handleSearch() {
                this.queryForm.pageNum = 1
                this.initData()
            },
            handleAdd() {
                this.editForm = {}
                this.editFormConfig.title = '新增参数'
                this.editFormConfig.isAdd = true
                this.editFormConfig.visible = true
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
            handleDelete(id) {
                this.$confirm('此操作将删除该参数数据, 是否继续?', '提示', {
                    closeOnClickModal: false,
                    type: 'warning'
                }).then(() => {
                    HttpRequest.delete('/api/param/' + id,).then(res => {
                        if (res.success) {
                            this.$message.success('删除参数成功')
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