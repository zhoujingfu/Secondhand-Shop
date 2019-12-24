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
      qq: '',
      mobile: ''
    },
    uid: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    wx.getStorage({
      key: 'uid',
      success: function (res) {
        that.setData({
          uid: res.data
        })
        that.getUserInfo();
      }
    })
    
    /**
     * 管理员用
     */
    // this.setData({
    //   uid: options.id
    // })

    this.WxValidate = new WxValidate(
      {
        username: {
          required: true,
          maxlength: 15,
        },
        qq: {
          required: true,
          qq: true
        },
        mobile: {
          tel: true
        }
      }
      , {
        username: {
          required: '请填写用户名',
          maxlength: '用户名最多15个字符'
        },
        qq: {
          required: '请填写学号'
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
   * 获取用户信息
   */
  getUserInfo: function () {
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

  /**
   * 提交表单
   */
  formSubmit: function (e) {
    var that = this;
    var formData = {
      username: e.detail.value.username,
      qq: e.detail.value.qq,
      mobile: e.detail.value.mobile
    }
    console.log(formData);
    //提交错误描述
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
    post('/user/update/' + this.data.uid, formData).then((res) => {
      wx.showLoading({
        mask: true
      })
      if (res.statusCode == '200') {
        wx.hideLoading()
        // if(result == true) {
        wx.showToast({
          title: '修改成功',
          icon: 'success',
          duration: 2000,
          mask: true,
          success: function () {
            /**跳转 */
            // wx.switchTab({
            //   url: '/pages/index/index',
            // })
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
  }
})