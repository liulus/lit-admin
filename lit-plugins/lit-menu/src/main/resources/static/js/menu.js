define(['text!/html/menu.html'], function (tmpl) {
    return {
        template: tmpl,
        data: function () {
            return {
                data: [],
                keyword: '',
                editFormConfig: {
                    visible: false,
                    isAdd: true,
                    title: '',
                    parent: ""
                },
                editForm: {
                    id: 0,
                    code: '',
                    name: '',
                    url: '',
                    icon: '',
                    orderNum: '',
                    remark: ''
                }
            }
        },
        created() {
            this.initData()

            let style = document.createElement('style');
            style.id = 'menuStyle'
            var text = document.createTextNode('.el-tree-node__content {height: 45px;border-bottom: 1px solid #ebeef5;}');
            style.appendChild(text)
            document.getElementsByTagName("head")[0].appendChild(style);
        },
        methods: {
            initData() {
                HttpRequest.get('/api/menu/tree').then(res => {
                    this.data = res.result || []
                })
            },
            handleAdd(parentNode) {
                this.editForm = {}
                this.editFormConfig.title = '新增菜单'
                this.editFormConfig.parent = parentNode ? parentNode.code + ' -- ' + parentNode.name : '--'
                this.editFormConfig.isAdd = true
                this.editFormConfig.visible = true
                this.editForm.parentId = parentNode ? parentNode.id : 0
            },
            handleEdit(node) {
                this.editForm.id = node.id
                this.editForm.code = node.code
                this.editForm.name = node.name
                this.editForm.url = node.url
                this.editForm.icon = node.icon
                this.editForm.orderNum = node.orderNum
                this.editForm.remark = node.remark

                this.editFormConfig.title = '修改菜单'
                this.editFormConfig.isAdd = false
                this.editFormConfig.visible = true
            },
            doEdit() {
                let method = this.editFormConfig.isAdd ? 'post' : 'put'
                HttpRequest.request(method, '/api/menu', this.editForm).then(res => {
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
                this.$confirm('此操作将删除该菜单数据, 是否继续?', '提示', {
                    closeOnClickModal: false,
                    type: 'warning'
                }).then(() => {
                    HttpRequest.delete('/api/menu/' + id,).then(res => {
                        if (res.success) {
                            this.$message.success('删除菜单成功')
                            this.initData()
                        } else {
                            this.$message.error(res.message)
                        }
                    })
                }).catch(() => {
                })
            },
            handleChange(id) {
                HttpRequest.post("/api/menu/change/status/" + id).then(res => {
                    if(!res.success) {
                        this.$message.error(res.message)
                    }
                })
            },
            handleSearch() {
                this.$refs.menuTree.filter(this.keyword);
            },
            filterNode(value, data) {
                if (value) {
                    return (data.code && data.code.indexOf(value) !== -1)
                        || (data.name && data.name.indexOf(value) !== -1)
                        || (data.remark && data.remark.indexOf(value) !== -1)
                }
                return true;
            }
        }
    }
})