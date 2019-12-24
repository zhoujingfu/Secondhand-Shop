// pages/index/register/register.js
import WxValidate from '../utils/WxValidate.js'
var uploadimg = require('../utils/uploadImages.js')
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
      proof:''
    },
    imagesList: [],
    tempFiles: [],
    pictureNumber: 1,
    flag: false,
    uid:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    this.setData({
      uid: options.id
    })
    
    this.WxValidate = new WxValidate(
      {
        username: {
          required: true,
          maxlength: 15,
        },
        password: {
          required: true,
          password:true,
          // maxlength: 200,
        },
        rePassword: {
          required: true,
          equalTo: 'password'
        },
        qq: {
          required: true,
          qq: true
        },
        mobile: {
          tel: true
        },
        proof: {
          tel: true
        }
      }
      , {
        username: {
          required: '请填写用户名',
          maxlength: '用户名最多15个字符'
        },
        password: {
          required: '请填写密码',
        },
        rePassword: {
          required: '请再次输入密码',
          equalTo: '两次密码输入不一致'
        },
        qq: {
          required: '请填写qq'
        },
        proof: {
          required: '请上传凭证'
        },
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
  chooseImage: function () {
    var that = this
    wx.chooseImage({
      count: that.data.pictureNumber, // 默认9
      sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        // console.log(res)
        var tempFilePaths = res.tempFilePaths
        var list = that.data.imagesList.concat(tempFilePaths)
        // console.log(list);
        that.setData({
          imagesList: that.data.imagesList.concat(tempFilePaths),
          pictureNumber: 1 - list.length,
          tempFiles: res.tempFiles
        })
      },
      complete: function () {
        if (that.data.pictureNumber < 1) {
          that.setData({
            flag: true
          })
        }
      }
    })
  },
  previewImage: function (e) {
    var current = e.currentTarget.dataset.src;
    // console.log(e)
    // var index = e.currentTarget.dataset.index;
    // console.log(current)
    wx.previewImage({
      current: current, // 当前显示图片的http链接  
      urls: this.data.imagesList // 需要预览的图片http链接列表  
    })
  },
  /**
   * 提交注册表单
   */
  formSubmit: function (e) {
    var that = this;
    var formData = {
      username: e.detail.value.username,
      password: e.detail.value.password,
      qq: e.detail.value.qq,
      mobile: e.detail.value.mobile,
      proof: e.detail.value.proof
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
    post('/user/register/' + this.data.uid, formData).then((res) => {
      wx.showLoading({
        mask: true
      })
      if (res.statusCode == '200') {
        if (that.data.imagesList.length != 0) {
          let result = uploadimg.uploadimg({
            url: serviceUrl + '/user/images/upload',//图片上传的接口
            path: that.data.imagesList,//这里是选取的图片的地址数组
            user: res.data
          });

        }
        wx.hideLoading()
        // if(result == true) {
        wx.showToast({
          title: '成功',
          icon: 'success',
          duration: 2000,
          mask: true,
          success: function () {
            that.setData({
              userForm: {
                username: '',
                password: '',
                rePassword: '',
                qq: '',
                mobile: '',
                proof:'',
              }
            });
            /**跳转 */
            wx.switchTab({
              url: '/pages/index/index/index',
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
  }
})