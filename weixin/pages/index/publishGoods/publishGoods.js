  // pages/index/publishGoods/publishGoods.js
import { serviceUrl,get, put, post, del } from '../api/api.js'
var uploadimg = require('../utils/uploadImages.js')
import WxValidate from '../utils/WxValidate.js'
// const appInstance = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    uid:'',
    imagesList: [],
    tempFiles:[],
    pictureNumber:9,
    flag: false,
    goodsForm: {
      goodsName:'',
      spec:'',
      price:'',
      originalPrice:'',
      index:''
    },
    index:'',
    typeList: ['闲置数码', '家具日用', '图书音像', '鞋服配饰', '美妆洗护', '文体户外','办公用品','其他'],
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
      }
    })

    this.WxValidate = new WxValidate(
      {
        goodsName: {
          required: true,
          maxlength: 15,
        },
        spec: {
          required: true,
          minlength: 5
          // maxlength: 200,
        },
        price: {
          required: true,
          number: true
        },
        originalPrice: {
          number: true
        },
        type: {
          required: true
        }
      }
      , {
        goodsName: {
          required: '请填写商品名称',
          maxlength:'名称最多15个字'
        },
        spec: {
          required: '请填写商品描述',
          minlength: '描述至少5个字'
        },
        price: {
          required: '请输入价格',
          number: '价格格式不正确'
        },
        originalPrice: {
          number: '价格格式不正确'
        },
        type: {
          required: '请选择商品分类',
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
          pictureNumber: 9 - list.length,
          tempFiles: res.tempFiles
        })
      },
      complete: function(){
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
  bindPickerChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
        index: e.detail.value
    })
  },
  formSubmit: function (e) {
    var that = this;
    var formData = e.detail.value;
    //提交错误描述
    if (!this.WxValidate.checkForm(e)) {
      const error = this.WxValidate.errorList[0]
      // `${error.param} : ${error.msg} `
      wx.showToast({
        title: error.msg,
        image: '/pages/images/warning.png',
        duration: 2000
      })
      return false;
     
    }
    if (!this.WxValidate.type == null) {
      this.bindPickerChange()
    }
    formData.type = this.data.typeList[formData.type];
    post('/goods/publish/'+ this.data.uid, formData).then((res) => {
      wx.showLoading({
        mask: true
      })
      if (res.statusCode == '200'){
        if (that.data.imagesList.length != 0) {
          let result = uploadimg.uploadimg({
            url: serviceUrl + '/goods/images/upload',//这里是你图片上传的接口
            path: that.data.imagesList,//这里是选取的图片的地址数组
            goods: res.data
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
                goodsForm: {
                  goodsName: '',
                  spec: '',
                  price: '',
                  originalPrice: '',
                  index: -1
                },
                index: -1,
                imagesList:[],
                flag: true
              });
              // wx.switchTab({
              //   url: '/pages/index/goods/goods'
              // })  
            }
          })
        // }
      } else {
        wx.hideLoading()
        console.log(res.data.message)
      }
    });
  }

})