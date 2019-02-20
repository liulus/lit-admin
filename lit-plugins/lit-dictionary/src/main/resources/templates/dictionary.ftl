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
                        <el-col :span="4"><el-button type="primary" plain icon="el-icon-plus" size="small"></el-button></el-col>
                        <el-col :span="12" :offset="2">
                            <el-input placeholder="请输入搜索内容">
                                <el-button slot="append" type="primary" icon="el-icon-search"></el-button>
                            </el-input>
                        </el-col>
                    </el-row>
                </div>

                <el-row :gutter="15" class="mg-b-15">
                    <template v-for="(item, index) in 4">
                        <el-col :span="6" >
                            <el-card shadow="hover" class="mouse-pointer" :body-style="{'padding': '5px', 'background-color': '#F2F6FC'}">
                                <div slot="header" class="t-center">
                                    <span class="fz-lg">风险等级</span><p></p>
                                    <span class="fz-lg">risk_level</span>
                                </div>
                                <el-row >
                                    <el-col :span="8" class="t-center">
                                        <el-button type="text" icon="el-icon-edit"></el-button>
                                    </el-col>
                                    <el-col :span="8" class="t-center">
                                        <el-button type="text" icon="el-icon-search"></el-button>
                                    </el-col>
                                    <el-col :span="8" class="t-center">
                                        <el-button type="text" icon="el-icon-delete"></el-button>
                                    </el-col>
                                </el-row>
                            </el-card>
                        </el-col>
                    </template>
                </el-row>
                <el-row :gutter="15" class="mg-b-15">
                    <template v-for="(item, index) in 4">
                        <el-col :span="6" >
                            <el-card shadow="hover" class="mouse-pointer" :body-style="{'padding': '5px', 'background-color': '#F2F6FC'}">
                                <div slot="header" class="t-center">
                                    <span class="fz-lg">风险等级</span><p></p>
                                    <span class="fz-lg">risk_level</span>
                                </div>
                                <el-row >
                                    <el-col :span="8" class="t-center">
                                        <el-button type="text" icon="el-icon-edit"></el-button>
                                    </el-col>
                                    <el-col :span="8" class="t-center">
                                        <el-button type="text" icon="el-icon-search"></el-button>
                                    </el-col>
                                    <el-col :span="8" class="t-center">
                                        <el-button type="text" icon="el-icon-delete"></el-button>
                                    </el-col>
                                </el-row>
                            </el-card>
                        </el-col>
                    </template>
                </el-row>
                <el-row :gutter="15" class="mg-b-15">
                    <template v-for="(item, index) in 4">
                        <el-col :span="6" >
                            <el-card shadow="hover" class="mouse-pointer" :body-style="{'padding': '5px', 'background-color': '#F2F6FC'}">
                                <div slot="header" class="t-center">
                                    <span class="fz-lg">风险等级</span><p></p>
                                    <span class="fz-lg">risk_level</span>
                                </div>
                                <el-row >
                                    <el-col :span="8" class="t-center">
                                        <el-button type="text" icon="el-icon-edit"></el-button>
                                    </el-col>
                                    <el-col :span="8" class="t-center">
                                        <el-button type="text" icon="el-icon-search"></el-button>
                                    </el-col>
                                    <el-col :span="8" class="t-center">
                                        <el-button type="text" icon="el-icon-delete"></el-button>
                                    </el-col>
                                </el-row>
                            </el-card>
                        </el-col>
                    </template>
                </el-row>
                <el-pagination
                        background
                        layout="prev, pager, next, total"
                        :current-page="3"
                        :page-size="12"
                        :total="1000">
                </el-pagination>
            </el-card>
        </div>
    </div>
</script>
<script>
    VueUtils.registerComponent({
        template: '#app-main-template',
        data: function () {
            return {
                input: ''
            }
        }
    })
</script>
<style>
    .bg-hover:hover {
        border-color: #0BB2D4;
    }
    /*.el-row {*/
        /*padding-top: 20px;*/
    /*}*/
</style>
</@Layout.adminLayout>