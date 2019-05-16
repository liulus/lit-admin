let HttpRequest = {
    get(url, params) {
        return this.request('get', url, params)
    },

    post(url, params) {
        return this.request('post', url, params)
    },

    put(url, params) {
        return this.request('put', url, params)
    },

    delete(url, params) {
        return this.request('delete', url, params)
    },

    request(method, url, params) {

        if (url.indexOf('/') !== 0) {
            url = '/' + url
        }

        let headers = new Headers();
        headers.append('Accept', 'application/json, text/plain, */*')
        if (url.startsWith('/oauth/token')) {
            headers.append('Authorization', 'Basic ' + window.btoa('lit:lit'))
        } else {
            let accessToken = localStorage.getItem('access_token');
            if (accessToken) {
                headers.append('Authorization', 'bearer ' + accessToken)
            }
        }

        let requestConfig = {
            method: method,
            headers: headers
        }
        if (params) {
            if (method === 'get') {
                let paramArray = [];
                for (let key in params) {
                    if (params[key]) {
                        paramArray.push(key + '=' + params[key]);
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
}

let VueUtils = {
    registerComponent: function (componentConfig) {
        componentConfig = componentConfig || {};
        if(!componentConfig.template) {
            componentConfig.template = '#app-main-template'
        }
        Vue.component('app-main', componentConfig)
    }
};

function getCurrentPathVariable() {
    let path = window.location.pathname;
    return path.substr(path.lastIndexOf('/') + 1)
}

function appendStyle(sylteCode, id) {
    let style = document.createElement('style');
    style.id = id + '-style'
    style.appendChild(document.createTextNode(sylteCode))
    document.getElementsByTagName("head")[0].appendChild(style);
}

function removeStyle(id) {
    var styleEle = document.getElementById(id + '-style');
    styleEle.remove()
}







