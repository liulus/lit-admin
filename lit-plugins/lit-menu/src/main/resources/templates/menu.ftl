<#import 'pages/layout-main.ftl' as Layout>
<@Layout.adminLayout title='菜单管理'>
<script type="text/x-template" id="app-main-template">
    <div>
    <#--<div class="aui-main__hd">-->
    <#--<el-breadcrumb separator="/">-->
    <#--<el-breadcrumb-item>-->
    <#--<svg class="icon-svg aui-aside__menu-icon" aria-hidden="true">-->
    <#--<use xlink:href="#icon-home"></use>-->
    <#--</svg>-->
    <#--</el-breadcrumb-item>-->
    <#--<el-breadcrumb-item>字典详情</el-breadcrumb-item>-->
    <#--</el-breadcrumb>-->
    <#--</div>-->

        <div class="aui-main__bd">
            <el-card shadow="never">
                <div slot="header">
                    <el-row>
                        <el-col :span="4">
                            <el-button type="primary" plain icon="el-icon-plus" size="medium" @click="handleAddChild"></el-button>
                        </el-col>
                        <el-col :span="12" :offset="2">
                            <el-input v-model="keyword" placeholder="请输入搜索内容">
                                <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
                            </el-input>
                        </el-col>
                    </el-row>
                </div>

                <el-row style="height: 40px;border-bottom: 1px solid #ebeef5;">
                    <el-col :span="5"><span class="mg-l-25 fz-lg">编码</span></el-col>
                    <el-col :span="5"><span class="mg-l-25 fz-lg">名称</span></el-col>
                    <el-col :span="10"><span class="mg-l-25 fz-lg">备注</span></el-col>
                </el-row>
                <el-tree :data="data"
                <#--:props="defaultProps"-->
                         :expand-on-click-node="false"
                         :filter-node-method="filterNode"
                         default-expand-all
                         ref="menuTree">
                    <div slot-scope="{ node, data }" style="width: 100%">
                        <el-row type="flex" align="middle">
                            <el-col :span="5"><span>{{ data.code }}</span></el-col>
                            <el-col :span="5"><span> {{ data.name }}</span></el-col>
                            <el-col :span="10"><span> {{ data.remark }}</span></el-col>
                            <el-col :span="3">
                                <el-button type="text" icon="el-icon-plus" @click="handleAddChild(data)"></el-button>
                                <el-button type="text" icon="el-icon-edit" @click="handleEdit(data)"></el-button>
                                <el-button type="text" icon="el-icon-delete" @click="handleDelete(data.id)"></el-button>
                            </el-col>
                        </el-row>
                    </div>
                </el-tree>
            </el-card>
        </div>

        <el-dialog :title="editFormConfig.title" :visible.sync="editFormConfig.visible" width="40%" :close-on-click-modal="false">
            <el-form :model="editForm" label-width="100px" label-suffix=":">
                <el-form-item label="父节点" v-if="editFormConfig.isAdd">
                    <span>{{editFormConfig.parent}}</span>
                </el-form-item>
                <el-form-item label="字典key">
                    <el-input v-model="editForm.dictKey"></el-input>
                </el-form-item>
                <el-form-item label="字典value">
                    <el-input v-model="editForm.dictValue"></el-input>
                </el-form-item>
                <el-form-item label="顺序号">
                    <el-input v-model="editForm.orderNum" type="number"></el-input>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input type="textarea" :rows="2" v-model="editForm.remark"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" size="medium" @click="doEdit">确 定</el-button>
                <el-button size="medium" @click="editFormConfig.visible = false">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</script>
<script>
    VueUtils.registerComponent({
        template: '#app-main-template',
        data: function () {
            return {
                data:[],
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
                HttpRequest.get('/api/menu/tree').then(res => {
                    this.data = res.result || []
                })
            },
            handleAddChild(parentNode) {
                this.editForm = {}
                this.editFormConfig.title = '新增菜单'
                this.editFormConfig.parent = parentNode.dictKey + ' -- ' + parentNode.dictValue
                this.editFormConfig.isAdd = true
                this.editFormConfig.visible = true
                this.editForm.parentId = parentNode.id
            },
            handleEdit(node) {
                this.editForm.id = node.id
                this.editForm.dictKey = node.dictKey
                this.editForm.dictValue = node.dictValue
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
                    HttpRequest.delete('/api/dictionary/' + id,).then(res => {
                        if (res.success) {
                            this.$message.success('删除菜单成功')
                            this.initData()
                        } else {
                            this.$message.error(res.message)
                        }
                    })
                }).catch(() => {})
            },
            handleSearch() {
                this.$refs.dictTree.filter(this.keyword);
            },
            filterNode(value, data) {
                if (value) {
                    return data.dictKey.indexOf(value) !== -1
                            || data.dictValue.indexOf(value) !== -1
                            || data.remark.indexOf(value) !== -1
                }
                return true;
            }
        }
    })
</script>
<style>
    .el-tree-node__content{
        height: 40px;
        border-bottom: 1px solid #ebeef5;
    }
</style>
</@Layout.adminLayout>