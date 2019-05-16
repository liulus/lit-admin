define(['text!/views/dictionary.html'], function (tmpl) {
    return {
        template: tmpl,
        data: function () {
            return {
                queryForm: {
                    keyword: '',
                    pageNum: 1
                },
                dataModel: localStorage.getItem('dataModel') || 'card',
                dictionaryList: [],
                page: {},
                editFormConfig: {
                    visible: false,
                    isAdd: true,
                    title: '',
                },
                editForm: {
                    id: 0,
                    dictKey: '',
                    dictValue: '',
                    remark: '',
                    orderNum: ''
                }
            }
        },
        created() {
            appendStyle('.el-table--medium td, .el-table--medium th {padding: 3px 0;}', 'dict')
            this.initList()
        },
        destroyed: function() {
            removeStyle('dict')
        },
        methods: {
            initList() {
                this.queryForm.pageSize = this.dataModel === 'card' ? 12 : 10
                HttpRequest.get('/api/dictionary/root/list', this.queryForm).then(res => {
                    if (res.success) {
                        this.dictionaryList = res.result.data || []
                        this.page = res.result.pageInfo
                    }
                })
            },
            handleQuery() {
                this.queryForm.pageNum = 1
                this.initList()
            },
            handleModelChange(dataModel) {
                localStorage.setItem('dataModel', dataModel)
                this.initList()
            },
            handleAdd() {
                this.editForm = {}
                this.editFormConfig.title = '新增字典'
                this.editFormConfig.isAdd = true
                this.editFormConfig.visible = true
            },
            handleEdit: function (dict) {
                this.editForm = dict
                this.editFormConfig.title = '修改字典'
                this.editFormConfig.isAdd = false
                this.editFormConfig.visible = true
            },
            doEdit() {
                let method = this.editFormConfig.isAdd ? 'post' : 'put'
                HttpRequest.request(method, '/api/dictionary', this.editForm).then(res => {
                    if (res.success) {
                        this.$message.success(this.editFormConfig.title + '成功')
                        this.initList()
                    } else {
                        this.$message.error(res.message)
                    }
                })
                this.editFormConfig.visible = false
            },
            handleDetail(id) {
                window.location.href = contextPath + '/dictionary/' + id
            },
            handleDelete(id) {
                this.$confirm('此操作将删除该字典数据, 是否继续?', '提示', {
                    closeOnClickModal: false,
                    type: 'warning'
                }).then(() => {
                    HttpRequest.delete('/api/dictionary/' + id,).then(res => {
                        if (res.success) {
                            this.$message.success('删除字典成功')
                            this.initList()
                        } else {
                            this.$message.error(res.message)
                        }
                    })
                }).catch(() => {
                })
            },
            handlePageChange(pageNum) {
                this.queryForm.pageNum = pageNum;
                this.initList()
            }
        }
    }
})