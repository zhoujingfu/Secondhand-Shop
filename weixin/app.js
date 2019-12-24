 //app.js
import { get } from './pages/index/api/api.js'
import wxValidate from './pages/index/utils/WxValidate.js'
App({
  onLaunch: function () {
    //隐藏系统tabbar
    //wx.hideTabBar();
    this.getSystemInfo();
    var count = this
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        var that = this;
        this.globalData.code = res.code;

        // 获取用户信息
        wx.getSetting({
          success: res => {
            if (res.authSetting['scope.userInfo']) {
              // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
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
                      //console.log("用户ID  " +res.data.ID )
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
          }
        })
      }
    });
  },
  //获取信息数目以红点显示
  getMessages: function(id) {
    var that = this
    setTimeout(function(){
      //console.log("1")
      get('/comments/unread/count/' + id, null).then((res) => {
        if (res.statusCode == '200' && res.data.toString() != '0') {
          wx.setTabBarBadge({
            index: 2,
            text: res.data.toString()
          })
         
        } 
        if (res.data.toString() == '0') {
          wx.removeTabBarBadge({
            index: 2,
          })
        }
        else {
          // wx.showToast({
          //   title: res.data.message,
          //   icon: "none",
          //   // image: '/pages/images/warning.png',
          //   duration: 2000
          // })
        }
      });
      that.getMessages(id);
    },2000)
  },

  getSystemInfo: function () {
    let t = this;
    wx.getSystemInfo({
      success: function (res) {
        t.globalData.systemInfo = res;
      }
    });
  },
  editTabbar: function () {
    let tabbar = this.globalData.tabBar;
    let currentPages = getCurrentPages();
    let _this = currentPages[currentPages.length - 1];
    let pagePath = _this.route;
    (pagePath.indexOf('/') != 0) && (pagePath = '/' + pagePath);
    for (let i in tabbar.list) {
      tabbar.list[i].selected = false;
      (tabbar.list[i].pagePath == pagePath) && (tabbar.list[i].selected = true);
    }
    _this.setData({
      tabbar: tabbar
    });
  },
  globalData: {
    systemInfo: null,
    userInfo: null,
    code: '',
    role:'',
    state:'',
    tabBar: {
      "backgroundColor": "#ffffff",
      "color": "#979795",
      "selectedColor": "#1c1c1b",
      "list": [
        {
          "pagePath": "/pages/index/index/index",
          "iconPath": "icon/portal.png",
          "selectedIconPath": "icon/portal-fill.png",
          "text": "首页"
        },
        {
          "pagePath": "/pages/index/goods/goods",
          "iconPath": "icon/find.png",
          "selectedIconPath": "icon/find-fill.png",
          "text": "发现"
        },
        {
          "pagePath": "/pages/index/publishGoods/publishGoods",
          "iconPath": "icon/input.png",
          "isSpecial": true,
          "text": "发布"
        },
        {
          "pagePath": "/pages/index/message/message",
          "iconPath": "icon/info.png",
          "selectedIconPath": "icon/info-fill.png",
          "text": "消息"
        },
        {
          "pagePath": "/pages/index/personal/personal",
          "iconPath": "icon/user.png",
          "selectedIconPath": "icon/user-fill.png",
          "text": "我的"
        }
      ]
    }
  },
  wxValidate: (rules, messages) => new wxValidate(rules, messages)
})
