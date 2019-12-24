// pages/index/personal/personal.js
import WxValidate from '../utils/WxValidate.js'
import { serviceUrl, get, put, post, del } from '../api/api.js'
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    tabbar: {},
    uid: '',
    username: '',
    userInfo: '',
    role:'',
    userForm: {
      username: '',
      qq: '',
      mobile: ''
    },
    list: [
      {
        list_tool: [
          {
            // img: "../../images/personal-publish.png",
            img: "../../images/22.png",
            name: "我的发布",
            url: "../personalGoods/personalGoods"
          },
          {
            //img: "../../images/personal-like.png",
            img: "../../images/33-1.png",
            name: "我的关注",
            url: "../statistics/statistics"
          }
        ]
      },
      {
        list_tool: [
          {
            //img: "../../images/personal-password.png",
            img: "../../images/44.png",
            name: "密码管理",
            url: "../passwordManage/passwordManage"
          },
          {
            //img: "../../images/personal-info.png",
            img: "../../images/55.png",
            name: "信息管理",
            url: "../userInfoManage/userInfoManage",
          }
        ]
      },
      {
        list_tool: [
          {
            //img: "../../images/exit.png",
            img: "../../images/66.png",
            name: "退出登录",
            url: "../login/login"
          }
        ]
      },
    ]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // wx.showNavigationBarLoading();
    var that = this
    //调用应用实例的方法获取全局数据
    wx.getStorage({
      key: 'uid',
      success: function (res) {
        that.setData({
          uid: res.data
        })
        that.getuserForm();
      },
      fail: function (err) {
        wx.redirectTo({
          url: '../authorized/authorized'
        })
      },
      
    })

    wx.getStorage({
      key: 'username',
      success: function (res) {
        that.setData({
          username: res.data
        })
      },
    })
    console.log(app.globalData.userInfo)
    //console.log("用户角色 " + app.globalData.role)
    that.setData({
      userInfo: app.globalData.userInfo,
      role: app.globalData.role
    })
    
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.getuserForm();
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  onGotUserInfo: function (e) {
    console.log(e.detail.errMsg)
    console.log(e.detail.userInfo)
    console.log(e.detail.rawData)
    this.setData({
      userInfo: e.detail.userInfo
    });
  },
  /**
   * 跳转   #F4CE73     #79CCBF
   */
  goPage: function (event) {
    var that = this;
    if (event.currentTarget.dataset.url == '../login/login') {
      console.log(event.currentTarget.dataset.url)
      wx.redirectTo({
        url: event.currentTarget.dataset.url
      })
    } else {
      wx.navigateTo({
        url: event.currentTarget.dataset.url + '?id=' + this.data.uid
      })
    }
  },
  goPageAdmin: function() {
    console.log("1")
    wx.navigateTo({
      url: '../userInfoManage/userInfoManage'
    })
  },
  getuserForm: function () {
    var that = this
    get('/user/' + this.data.uid, null).then((res) => {
      wx.showLoading({
        mask: true
      })
      if (res.statusCode == '200') {
        wx.hideLoading()
        that.setData({
          userForm: {
            username: res.data.username,
            qq: res.data.qq,
            mobile: res.data.mobile
          }
        });

      } else {
        wx.hideLoading()
        console.log(res.data.message)
        wx.showToast({
          title: res.data.message,
          icon: "none",
          // image: '/pages/images/warning.png',
          duration: 2000
        })
      }
    });
  },
})