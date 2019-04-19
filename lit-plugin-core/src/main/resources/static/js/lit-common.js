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
        let requestConfig = {
            method: method,
            headers: {
                'Accept': 'application/json, text/plain, */*',
            }
        }
        if (params) {
            if (method === 'get') {
                let paramArray = [];
                for (let k in params) {
                    paramArray.push(k + '=' + params[k]);
                }
                url = url + '?' + paramArray.join('&')
            } else {
                requestConfig.headers['Content-Type'] = 'application/json;charset=UTF-8'
                requestConfig.body = JSON.stringify(params)
            }
        }
        return fetch(contextPath + url, requestConfig).then(res => res.json())
    }
}

function convertUrlParams(parmas) {
    if (parmas) {
        var paramArray = [];
        for (let k in params) {
            paramArray.push(k + '=' + param[k]);
        }
        return paramArray.join('&')
    }
    return ""
}






