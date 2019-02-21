<#import 'pages/layout-main.ftl' as Layout>
<@Layout.adminLayout title='字典详情'>
<script type="text/x-template" id="app-main-template">
    <div>
        <div class="aui-main__hd">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <svg class="icon-svg aui-aside__menu-icon" aria-hidden="true">
                        <use xlink:href="#icon-home"></use>
                    </svg>
                </el-breadcrumb-item>
                <el-breadcrumb-item>字典详情</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class="aui-main__bd">
            <el-card shadow="never">
                <div slot="header">
                    <el-row :gutter="20">
                        <#--<el-col :span="4"><el-button type="primary" plain icon="el-icon-plus" size="small"></el-button></el-col>-->
                        <el-col :span="12" :offset="6">
                            <el-input placeholder="请输入搜索内容">
                                <el-button slot="append" type="primary" icon="el-icon-search"></el-button>
                            </el-input>
                        </el-col>
                    </el-row>
                </div>
                <#--<el-row :gutter="20" style="height: 40px;border-bottom: 1px solid #ebeef5;">-->
                    <#--<el-col :span="5"><span class="fz-lg">{{data1[0].key}}</span></el-col>-->
                    <#--<el-col :span="5"><span class="fz-lg">{{data1[0].label}}</span></el-col>-->
                <#--</el-row>-->
                <el-tree :data="data1"
                         <#--:props="defaultProps"-->
                         :expand-on-click-node="false"
                         default-expand-all
                         ref="dictTree">
                    <div slot-scope="{ node, data }" style="width: 100%">
                        <el-row type="flex" align="middle">
                            <el-col :span="5"><span>{{ data.label }}</span></el-col>
                            <el-col :span="5"><span> {{ data.key }}</span></el-col>
                            <el-col :span="10"><span> {{ data.label }} {{data.id}}</span></el-col>
                            <el-col :span="1">
                                <el-button type="text" icon="el-icon-plus" @click="openAddForm(data)"></el-button>
                            </el-col>
                            <el-col :span="1">
                                <el-button type="text" icon="el-icon-edit"></el-button>
                            </el-col>
                            <el-col :span="1">
                                <el-button type="text" icon="el-icon-delete"></el-button>
                            </el-col>
                        </el-row>
                    </div>
                </el-tree>
            </el-card>
        </div>

        <el-dialog :title="editForm.title" :visible.sync="editForm.visible" width="40%" :close-on-click-modal="false">
            <el-form :model="editForm" label-width="100px" label-suffix=":">
                <el-form-item label="父节点">
                    <span>{{editForm.parent}}</span>
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
                    <el-input v-model="editForm.remark"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" size="medium">确 定</el-button>
                <el-button size="medium" @click="editForm.visible = false">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</script>
<script>
    VueUtils.registerComponent({
        template: '#app-main-template',
        data: function () {
            return {
                data1: [{
                    id: 1,
                    label: '风险等级',
                    key: 'risk_level',
                    children: [
                        {
                            id: 2,
                            label: 'R1',
                            key: 'R1',
                            children: [
                                {
                                    id: 21,
                                    label: 'R1-1',
                                    key: 'R1-1'
                                }, {
                                    id: 22,
                                    label: 'R1-2',
                                    key: 'R1-2'
                                }
                            ]
                        }, {
                            id: 3,
                            label: 'R2',
                            key: 'R2',
                            children: [
                                {
                                    id: 31,
                                    label: 'R2-1',
                                    key: 'R2-1'
                                }, {
                                    id: 32,
                                    label: 'R2-2',
                                    key: 'R2-2'
                                }
                            ]
                        }, {
                            id: 4,
                            label: 'R3',
                            key: 'R3'
                        }, {
                            id: 5,
                            label: 'R4',
                            key: 'R4'
                        }, {
                            id: 6,
                            label: 'R5',
                            key: 'R5'
                        }
                    ]
                }],
                defaultProps: {
                    children: 'children',
                    label: 'label'
                },
                editForm: {
                    visible: false,
                    title: '',
                    dictKey: '',
                    dictValue: '',
                    remark: '',
                    orderNum: ''
                }
            }
        },
        methods: {
            openAddForm(parentNode) {
                this.editForm.title = '新增字典'
                this.editForm.parent = parentNode.label + ' -- ' + parentNode.key
                this.editForm.visible = true
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
<style>
    .bg-hover:hover {
        border-color: #0BB2D4;
    }
    .el-tree-node__content{
        height: 40px;
        border-bottom: 1px solid #ebeef5;
    }
</style>
</@Layout.adminLayout>