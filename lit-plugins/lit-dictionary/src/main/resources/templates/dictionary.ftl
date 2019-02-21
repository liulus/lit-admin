<#import 'pages/layout-main.ftl' as Layout>
<@Layout.adminLayout title='字典管理'>
<script type="text/x-template" id="app-main-template">
    <div>
        <div class="aui-main__hd">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <svg class="icon-svg aui-aside__menu-icon" aria-hidden="true">
                        <use xlink:href="#icon-home"></use>
                    </svg>
                </el-breadcrumb-item>
                <el-breadcrumb-item>字典管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class="aui-main__bd">
            <el-card shadow="never">
                <div slot="header">
                    <el-row :gutter="20">
                        <el-col :span="4">
                            <el-button type="primary" plain icon="el-icon-plus" size="small" @click="openAddForm"></el-button>
                        </el-col>
                        <el-col :span="12" :offset="2">
                            <el-input placeholder="请输入搜索内容">
                                <el-button slot="append" type="primary" icon="el-icon-search"></el-button>
                            </el-input>
                        </el-col>
                    </el-row>
                </div>

                <el-row :gutter="15" class="mg-b-15">
                    <template v-for="(item, index) in 4">
                        <el-col :span="6">
                            <el-card shadow="hover" :body-style="{'padding': '5px', 'background-color': '#F2F6FC'}">
                                <div slot="header" class="t-center"">
                                    <span class="fz-lg">风险等级</span><p></p>
                                    <span class="fz-lg">risk_level</span>
                                </div>
                                <el-row >
                                    <el-col :span="8" class="t-center">
                                        <el-button type="text" icon="el-icon-edit" @click="handleEdit"></el-button>
                                    </el-col>
                                    <el-col :span="8" class="t-center">
                                        <el-button type="text" icon="el-icon-view" @click="handleSearch"></el-button>
                                    </el-col>
                                    <el-col :span="8" class="t-center">
                                        <el-button type="text" icon="el-icon-delete" @click="handleDetail"></el-button>
                                    </el-col>
                                </el-row>
                            </el-card>
                        </el-col>
                    </template>
                </el-row>
                <el-pagination background layout="prev, pager, next, total" :current-page="3" :page-size="12" :total="1000">
                </el-pagination>
            </el-card>
        </div>

        <#-- 编辑字典 -->
        <el-dialog :title="editFormConfig.title" :visible.sync="editFormConfig.visible" :close-on-click-modal="false" width="40%">
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
                <el-button type="primary" size="medium" @click="doAddDict">确 定</el-button>
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
                input: '',
                editFormConfig: {
                    visible: false,
                    title: '',
                },
                editForm: {
                    parentId: 0,
                    dictKey: '',
                    dictValue: '',
                    remark: '',
                    orderNum: ''
                }
            }
        },
        methods: {
            openAddForm() {
                this.editFormConfig.title = '新增字典'
                this.editFormConfig.visible = true
            },
            doAddDict() {
                this.editForm.parentId = 0
                fetch('/lit/plugin/dictionary.json', {
                    method: 'post',
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    },
                    body: JSON.stringify(this.editForm)
                }).then(res => {
                    console.log(res)
                    return res.json()
                }).then(res => console.log(res))
            },
            handleDetail: function () {
                console.log('to detail')
            },
            handleEdit: function () {
                console.log('handleEdit1')
            },
            handleSearch: function () {
                console.log('handleSearch1')
            }
        }
    })
</script>
</@Layout.adminLayout>