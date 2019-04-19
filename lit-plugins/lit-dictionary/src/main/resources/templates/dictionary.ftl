<#import 'pages/layout-main.ftl' as Layout>
<@Layout.adminLayout title='字典管理'>
<script type="text/x-template" id="app-main-template">
    <div>
    <#--<div class="aui-main__hd">-->
    <#--<el-breadcrumb separator="/">-->
    <#--<el-breadcrumb-item>-->
    <#--<svg class="icon-svg aui-aside__menu-icon" aria-hidden="true">-->
    <#--<use xlink:href="#icon-home"></use>-->
    <#--</svg>-->
    <#--</el-breadcrumb-item>-->
    <#--<el-breadcrumb-item>字典管理</el-breadcrumb-item>-->
    <#--</el-breadcrumb>-->
    <#--</div>-->

    <div class="aui-main__bd">
        <el-card shadow="never">
            <div slot="header">
                <el-row :gutter="20">
                    <el-col :span="4">
                        <el-button type="primary" plain icon="el-icon-plus" size="small" @click="openAddForm"></el-button>
                    </el-col>
                    <el-col :span="12" :offset="2">
                        <el-form :model="queryForm">
                            <el-input v-model="queryForm.keyword" placeholder="请输入搜索内容">
                                <el-button slot="append" icon="el-icon-search" @click="initList"></el-button>
                            </el-input>
                        </el-form>
                    </el-col>
                </el-row>
            </div>

            <el-row :gutter="15">
                <el-col :span="6" class="mg-b-15" v-for="(item, index) in dictionaryList" :key="item.id">
                    <el-card shadow="hover" :body-style="{'padding': '5px', 'background-color': '#F2F6FC'}">
                        <div slot="header" class="t-center"">
                            <span class="fz-lg">{{item.dictKey}}</span><p></p>
                            <span class="fz-lg">{{item.dictValue}}</span>
                        </div>
                        <el-row type="flex" align="middle">
                            <el-col :span="8" class="t-center">
                                <el-button type="text" icon="el-icon-edit" @click="handleEdit(index)"></el-button>
                            </el-col>
                            <el-col :span="8" class="t-center">
                                <a :href="`${rc.contextPath}/dictionary/${r'${item.id}'}`"><i class="el-icon-view"></i></a>
                            </el-col>
                            <el-col :span="8" class="t-center">
                                <el-button type="text" icon="el-icon-delete" @click="handleDelete(item.id)"></el-button>
                            </el-col>
                        </el-row>
                    </el-card>
                </el-col>
            </el-row>
            <el-pagination background layout="prev, pager, next, total"
                           :current-page="page.pageNum" :page-size="page.pageSize" :total="page.totalRecord">
            </el-pagination>
        </el-card>
    </div>

        <#-- 编辑字典 -->
    <el-dialog :title="editFormConfig.title" :visible.sync="editFormConfig.visible" :close-on-click-modal="false"
               width="40%">
        <el-form :model="editForm" label-width="100px" label-suffix=":">
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
                <el-input v-model="editForm.remark"></el-input>
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
                queryForm: {
                    keyword: '',
                    pageNum: 1,
                    pageSize: 12
                },
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
            this.initList()
        },
        methods: {
            initList() {
                HttpRequest.get('/api/dictionary/root/list', this.queryForm).then(res => {
                    console.log(res)
                    if (res.success) {
                        this.dictionaryList = res.data.content
                        this.page = res.data.pageInfo
                    }
                })
            },
            openAddForm() {
                this.editForm = {}
                this.editFormConfig.title = '新增字典'
                this.editFormConfig.isAdd = true
                this.editFormConfig.visible = true
            },
            handleEdit: function (index) {
                this.editForm = this.dictionaryList[index]
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
            handleDelete(id) {
                HttpRequest.delete('/api/dictionary/' + id,).then(res => {
                    if (res.success) {
                        this.$message.success('删除字典成功')
                        this.initList()
                    } else {
                        this.$message.error(res.message)
                    }
                })
            }
        }
    })
</script>
</@Layout.adminLayout>