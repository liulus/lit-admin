define(['text!/views/menu.html'], function (tmpl) {
    return {
        template: tmpl,
        data: function () {
            return {
                data: [],
                keyword: '',
                columns: [
                    { label: '名称', prop: 'name', width: '300px' },
                    { label: '编码', prop: 'code', width: '200px' },
                    { label: '图标', prop: 'icon', width: '100px' },
                    { label: 'url', prop: 'url', width: '250px' },
                    { label: '顺序号', prop: 'orderNum', width: '80px' },
                    { label: '状态', prop: 'enable', width: '100px' }
                ],
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
        },
        methods: {
            initData() {
                this.$http.get('/api/menu/tree').then(res => {
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
                this.$http.request(method, '/api/menu', this.editForm).then(res => {
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
                    this.$http.delete('/api/menu/' + id,).then(res => {
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
                this.$http.post("/api/menu/change/status/" + id).then(res => {
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