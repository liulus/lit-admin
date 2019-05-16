<#import "/pages/layout-main.ftl" as Layout>
<@Layout.adminLayout title='首页'>

<script type="text/x-template" id="app-main-template">
    <main class="aui-main">
        <app-breadcrumb title="首页"></app-breadcrumb>
        <div class="aui-main__bd">
            <h3>欢迎使用lit-admin</h3>
        </div>
    </main>
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