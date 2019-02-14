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
            <div class="aui-page page-index">
                <main class="aui-page-main">
                    <div class="index-section">
                        <el-row :gutter="20">
                            <el-col :span="16">
                                <el-input v-model="input" placeholder="请输入内容"></el-input>
                            </el-col>
                            <el-col :span="8">
                                <el-button type="primary">主要按钮</el-button>
                            </el-col>
                        </el-row>
                    </div>

                    <!-- 管理助手 -->
                    <div class="index-section">
                        <div class="aui-panel index-assistant">
                            <div class="aui-panel__bd">
                                <el-row :gutter="20">
                                    <el-col :span="5">
                                        <div class="index-assistant__item">
                                        <#--<div class="index-assistant__item-hd">-->
                                        <#--<svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-robot"></use></svg>-->
                                        <#--</div>-->
                                            <div class="index-assistant__item-bd">
                                                <h4 class="index-assistant__item-title">key: riskLevel</h4>
                                                <p class="index-assistant__item-intro"></p>
                                                <h4 class="index-assistant__item-title">value: 风险等级</h4>
                                                <p class="index-assistant__item-intro"></p>
                                                <el-button-group >
                                                    <el-button type="primary" size="mini" icon="el-icon-edit"></el-button>
                                                    <el-button type="primary" size="mini" icon="el-icon-share"></el-button>
                                                    <el-button type="primary" size="mini" icon="el-icon-delete"></el-button>
                                                </el-button-group>
                                                <#--<el-button type="primary" plain size="mini">立即拼团</el-button>-->
                                            </div>
                                        </div>
                                    </el-col>

                                    <el-col :span="5">
                                        <div class="index-assistant__item">
                                            <div class="index-assistant__item-hd">
                                                <svg class="icon-svg" aria-hidden="true">
                                                    <use xlink:href="#icon-instagram"></use>
                                                </svg>
                                            </div>
                                            <div class="index-assistant__item-bd">
                                                <h4 class="index-assistant__item-title">2018广东云栖大会</h4>
                                                <p class="index-assistant__item-intro">11月22日广州南丰朗豪酒店，报名进行中</p>
                                                <el-button type="primary" plain size="mini">立即报名</el-button>
                                            </div>
                                        </div>
                                    </el-col>

                                    <el-col :span="5">
                                        <div class="index-assistant__item">
                                            <div class="index-assistant__item-hd">
                                                <svg class="icon-svg" aria-hidden="true">
                                                    <use xlink:href="#icon-apartment"></use>
                                                </svg>
                                            </div>
                                            <div class="index-assistant__item-bd">
                                                <h4 class="index-assistant__item-title">推荐好友送云服务器</h4>
                                                <p class="index-assistant__item-intro">邀请好友7.1折起购短信包，即可获赠云服务器</p>
                                                <el-button type="primary" plain size="mini">立即前往</el-button>
                                            </div>
                                        </div>
                                    </el-col>
                                </el-row>

                            </div>
                        </div>
                    </div>
                </main>
            </div>
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
</@Layout.adminLayout>