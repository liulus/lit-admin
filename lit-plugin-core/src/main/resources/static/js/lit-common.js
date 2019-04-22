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

        var headers = new Headers()
        headers.append('Accept', 'application/json, text/plain, */*')

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
        Vue.component('app-main', componentConfig)
    }
};
Vue.prototype.$ELEMENT = {size: 'medium'};








