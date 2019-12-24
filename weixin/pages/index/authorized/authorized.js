Page({
  data: {
    //判断小程序的API，回调，参数，组件等是否在当前版本可用。
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  onLoad: function () {
    // 查看是否授权
    var that = this;
    wx.getStorage({
      key: 'uid',
      success: function (res) {
        that.bindGetUserInfo();
      },
      fail: function () {
      }
    })
  },
  bindGetUserInfo: function (e) {
    wx.getUserInfo({
      success: res => {
        console.log(res)

        // 可以将 res 发送给后台解码出 unionId
        that.globalData.userInfo = res.userInfo
        let params = {
          code: that.globalData.code,
          nickName: that.globalData.userInfo.nickName,
          avatarUrl: that.globalData.userInfo.avatarUrl,
          gender: that.globalData.userInfo.gender
        }
        get('/weChat/getUserInfo', params).then((res) => {
          if (res.statusCode == '200') {
            console.log(res.data)
            wx.setStorage({
              key: "uid",
              data: res.data.id,
            });
            console.log("用户ID  " + res.data.ID)
            that.globalData.role = res.data.role
            if (res.data.state == 1) {
              wx.redirectTo({
                url: '../error/error'
              })
            }
            if (res.data.username == "" || res.data.username == null) {
              wx.redirectTo({
                url: '../register/register?id=' + res.data.id,
              })
            }
            count.getMessages(res.data.id)
          } else {
            // wx.showToast({
            //   title: res.data.message,
            //   icon: "none",
            //   // image: '/pages/images/warning.png',
            //   duration: 2000
            // })
            wx.navigateTo({
              url: '../authorized/authorized',
            })
          }
        });
        // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
        // 所以此处加入 callback 以防止这种情况
        if (this.userInfoReadyCallback) {
          this.userInfoReadyCallback(res)
        }
      }
    })
  }
})