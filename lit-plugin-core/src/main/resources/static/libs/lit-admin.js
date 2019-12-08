(function (global, factory) {
    typeof exports === 'object' && typeof module !== 'undefined' ? module.exports = factory() :
        typeof define === 'function' && define.amd ? define(['text!/views/lit-layout.html'], factory) :
            (global = global || self, global.Lit = factory());
}(this, function (litLayout) {

    const httpRequest = {
        get: function (url, params) {
            return this.request('get', url, params)
        },
        post: function (url, params) {
            return this.request('post', url, params)
        },
        put: function (url, params) {
            return this.request('put', url, params)
        },
        delete: function (url, params) {
            return this.request('delete', url, params)
        },
        request: function (method, url, params) {

            if (url.indexOf('/') !== 0) {
                url = '/' + url
            }

            var headers = new Headers();
            headers.append('Accept', 'application/json, text/plain, */*')
            if (url.startsWith('/oauth/token')) {
                headers.append('Authorization', 'Basic ' + window.btoa('lit:lit'))
            } else {
                var accessToken = localStorage.getItem('access_token');
                if (accessToken) {
                    headers.append('Authorization', 'bearer ' + accessToken)
                }
            }

            var requestConfig = {
                method: method,
                credentials: 'omit',
                headers: headers
            }
            if (params) {
                if (method === 'get') {
                    var paramArray = [];
                    for (var key in params) {
                        if (params[key]) {
                            paramArray.push(key + '=' + params[key])
                        }
                    }
                    url = url + '?' + paramArray.join('&')
                } else {
                    headers.append('Content-Type', 'application/json;charset=UTF-8')
                    requestConfig.body = JSON.stringify(params)
                }
            }
            return fetch(window.APP_CONFIG.contextPath + url, requestConfig).then(res => res.json())
        }
    }

    // 异步加载页面
    function _import(dep) {
        return function () {
            return new Promise(function (resolve, reject) {
                require(Array.isArray(dep) ? dep : [dep], function (res) {
                    resolve(res)
                })
            })
        }
    }

    // 单页应用和多页应用跳转链接方式
    function _redirect(url) {
        if (!url) {
            return
        }
        if (window.APP_CONFIG.isSpa) {
            window.vm.$router.push(url)
        } else {
            window.location.href = window.APP_CONFIG.contextPath + url
        }
    }

    function initComponent(vue) {
        vue.component('lit-admin', {
            template: litLayout,
            data: function () {
                return {
                    contextPath: window.APP_CONFIG.contextPath,
                    asideFloat: false,
                    asideHide: false,
                    asideFixed: false,
                    menuTop: true,
                    menuList: [],
                    activeMenuId: 0
                }
            },
            created: function () {
                let route = this.$route
                this.initMenu(route.path)
                console.log(route)
                // this.shouldExpendMenu(route.path)
            },

            methods: {
                initMenu(activeUrl) {
                    let myMenu = '' && sessionStorage.getItem('myMenu');
                    if (myMenu) {
                        this.menuList = JSON.parse(myMenu)
                    } else {
                        httpRequest.get('/api/my/menu').then(res => {
                            if (res.success) {
                                let menuList = res.result || []
                                this.shouldExpendMenu(menuList, activeUrl)
                                this.menuList = menuList
                                sessionStorage.setItem('myMenu', JSON.stringify(this.menuList))
                            }
                        })
                    }
                },
                handleMenuClick: function (index, childIndex) {
                    var menu = this.menuList[index];
                    var childMenu = menu.children && menu.children[childIndex];
                    menu.expand = childMenu ? menu.expand : !menu.expand
                    this.activeMenuId = childMenu ? childMenu.id : menu.url ? menu.id : this.activeMenuId
                    this.$redirect(childMenu ? childMenu.url : menu.url)
                    this.menuList.splice(index, 1, menu)
                },
                shouldExpendMenu: function (menuList, activeUrl) {
                    var length = menuList.length;
                    for (let i = 0; i < length; i++) {
                        var pMenu = menuList[i];
                        if (pMenu.url === activeUrl) {
                            this.activeMenuId = pMenu.id
                            break
                        }
                        let childMenuList = pMenu.children || []
                        for (let j = 0; j < childMenuList.length; j++) {
                            var cMenu = pMenu.children[j];
                            if (cMenu.url === activeUrl) {
                                pMenu.expand = true
                                this.activeMenuId = cMenu.id
                                break
                            }
                        }
                    }
                }
            }
        })
    }


    return function (vue) {
        console.log('install lit-admin plugin...')
        vue.prototype._import = _import
        vue.prototype.$redirect = _redirect
        vue.prototype.$http = httpRequest
        initComponent(vue)
    }
}))







