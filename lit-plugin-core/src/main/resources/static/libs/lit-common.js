(function (global, factory) {
    typeof exports === 'object' && typeof module !== 'undefined' ? module.exports = factory() :
        typeof define === 'function' && define.amd ? define(factory) :
            (global = global || self, global.Lit = factory());
}(this, function () {
    return {
        httpRequest: {
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
                return fetch(contextPath + url, requestConfig).then(res => res.json())
            }
        },
        getCurrentPathVariable: function () {
            var path = window.location.pathname
            return path.substr(path.lastIndexOf('/') + 1)
        },
        appendStyle: function (styleCode) {
            var styleId = 'auto-created'
            var styleElement = document.getElementById(styleId);

            if (!styleElement) {
                styleElement = document.createElement('style')
                styleElement.id = styleId
                document.getElementsByTagName("head")[0].appendChild(styleElement)
            }
            styleElement.replaceWith(document.createTextNode(styleCode))
        }
    }
}))







