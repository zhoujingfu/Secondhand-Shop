// pages/index/register/register.js
import WxValidate from '../utils/WxValidate.js'
import { serviceUrl, get, put, post, del } from '../api/api.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userForm: {
      username: '',
      password: '',
      rePassword: '',
      qq: '',
      mobile: '',
      code:''
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this

    this.WxValidate = new WxValidate(
      {
        username: {
          required: true
        },
        password: {
          required: true
          // password: true,
          // maxlength: 200,
        }
      }
      , {
        username: {
          required: '请填写用户名'
        },
        password: {
          required: '请填写密码'
        }
      }
    )

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

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
  /**
   * 提交注册表单
   */
  formSubmit: function (e) {
    var that = this;
    var formData = {
      username: e.detail.value.username,
      password: e.detail.value.password,
    }
    //
    if (!this.WxValidate.checkForm(e)) {
      const error = this.WxValidate.errorList[0]
      // `${error.param} : ${error.msg} `
      wx.showToast({
        title: error.msg,
        icon: "none",
        // image: '/pages/images/warning.png',
        duration: 2000
      })
      return false
    }
    // formData.type = this.data.typeList[formData.type];
    post('/user/auth', formData).then((res) => {
      wx.showLoading({
        mask: true
      })
      if (res.statusCode == '200') {
        wx.hideLoading()
        wx.showToast({
          title: '登陆成功',
          icon: 'success',
          duration: 2000,
          mask: true,
          success: function () {
            that.setData({
              userForm: {
                username: '',
                password: ''
              }
            });
            /**跳转 */
            wx.switchTab({
              url: '/pages/index/index/index'
            })
          }
        })
        // }
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
  /**
   * 微信授权的登陆
   */
  weLogin: function() {
    var that = this;
    wx.showModal({
      title: '提示',
      content: '是否微信授权登陆？',
      success: function (res) {
        if (res.confirm) {
          // 登录
          wx.login({
            success: res => {
              // 发送 res.code 到后台换取 openId, sessionKey, unionId

              // that.data.globalData.code = res.code;
              that.setData({
                code: res.code
              })
              // 获取用户信息
              wx.getSetting({
                success: res => {
                  if (res.authSetting['scope.userInfo']) {
                    // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
                    /**跳转 */
                    wx.switchTab({
                      url: '/pages/index/index/index'
                    })
                  }
                }
              })
            }
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  }
})