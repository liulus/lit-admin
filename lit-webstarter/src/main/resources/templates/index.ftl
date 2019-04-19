<#import "pages/layout1.ftl" as Layout>
<@Layout.adminLayout>

<script type="text/x-template" id="app-main-template">
    <div>
        <div class="aui-main__hd">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <svg class="icon-svg aui-aside__menu-icon" aria-hidden="true">
                        <use xlink:href="#icon-home"></use>
                    </svg>
                </el-breadcrumb-item>
                <el-breadcrumb-item>首页</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
    </div>
</script>

<script>
    VueUtils.registerComponent({
        template: '#app-main-template',
        data: function () {
            return {
                text: '21341231'
            }
        }
    })
</script>

</@Layout.adminLayout>