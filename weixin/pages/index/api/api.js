//ar devUrl = 'http://127.0.0.1:8081'
var devUrl = 'https://www.xiaoyuancun.store'
  
//export const serviceUrl = 'http://127.0.0.1:8081/'
export const serviceUrl = 'https://www.xiaoyuancun.store'
export const get = (url, params) => {
  var promise = new Promise(function (resolve, reject) {
    wx.request({
      url: devUrl + url,
      data: params,
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: resolve,
      fail: reject
    })
  });
  return promise;
}

export const put = (url, params) => {
  var promise = new Promise(function (resolve, reject) {
    wx.request({
      url: devUrl + url,
      data: params,
      method: 'PUT',
      header: {
        'content-type': 'application/json'
      },
      success: resolve,
      fail: reject
    })
  });
  return promise;
}

export const post = (url, params) => {
  var promise = new Promise(function (resolve, reject) {
    wx.request({
      url: devUrl + url,
      data: params,
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      success: resolve,
      fail: reject
    })
  });
  return promise;
}

export const del = (url, params) => {
  var promise = new Promise(function (resolve, reject) {
    wx.request({
      url: devUrl + url,
      data: params,
      method: 'DELETE',
      header: {
        'content-type': 'application/json'
      },
      success: resolve,
      fail: reject
    })
  });
  return promise;
}






