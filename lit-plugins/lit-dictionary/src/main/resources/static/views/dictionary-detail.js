define(['text!/views/dictionary-detail.html'], function (tmpl) {
    return {
        template: tmpl,
        data: function () {
            return {
                dictId: getCurrentPathVariable(),
                data: {},
                keyword: '',
                editFormConfig: {
                    visible: false,
                    isAdd: true,
                    title: '',
                    parent: ""
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
            this.initData()
        },
        methods: {
            initData() {
                HttpRequest.get('/api/dictionary/detail/' + this.dictId).then(res => {
                    this.data = res.result || {}
                })
            },
            handleAdd(parentNode) {
                this.editForm = {}
                if (!parentNode) {
                    parentNode = this.data
                }
                this.editFormConfig.title = '新增字典'
                this.editFormConfig.parent = parentNode.dictKey + ' -- ' + parentNode.dictValue
                this.editFormConfig.isAdd = true
                this.editFormConfig.visible = true
                this.editForm.parentId = parentNode.id
            },
            handleEdit: function (node) {
                this.editForm.id = node.id
                this.editForm.dictKey = node.dictKey
                this.editForm.dictValue = node.dictValue
                this.editForm.orderNum = node.orderNum
                this.editForm.remark = node.remark

                this.editFormConfig.title = '修改字典'
                this.editFormConfig.isAdd = false
                this.editFormConfig.visible = true
            },
            doEdit() {
                let method = this.editFormConfig.isAdd ? 'post' : 'put'
                HttpRequest.request(method, '/api/dictionary', this.editForm).then(res => {
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
                this.$confirm('此操作将删除该字典数据, 是否继续?', '提示', {
                    closeOnClickModal: false,
                    type: 'warning'
                }).then(() => {
                    HttpRequest.delete('/api/dictionary/' + id,).then(res => {
                        if (res.success) {
                            this.$message.success('删除字典成功')
                            this.initData()
                        } else {
                            this.$message.error(res.message)
                        }
                    })
                }).catch(() => {
                })
            },
            handleSearch() {
                this.$refs.dictTree.filter(this.keyword);
            },
            filterNode(value, data) {
                if (value) {
                    return (data.dictKey && data.dictKey.indexOf(value) !== -1)
                        || (data.dictValue && data.dictValue.indexOf(value) !== -1)
                        || (data.remark && data.remark.indexOf(value) !== -1)
                }
                return true;
            }
        }
    }
})