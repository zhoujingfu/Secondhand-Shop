// pages/index/goods/goods.js
import { serviceUrl, get, put, post, del } from '../api/api.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    uid: '',
    goodsList: [],
    pageNum: '',
    pageSize: '',
    goodsName: '',
    totalPages: '',
    type: ''
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
        that.freshGoods();
      }
    })
    

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
  
  goCancel: function () {
    wx: wx.navigateBack({
      delta: 1,
    });
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    this.freshGoods();
    wx.stopPullDownRefresh()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    if (this.data.pageNum < this.data.totalPages) {
      wx.showLoading({
        mask: true,
        title: '玩命加载中'
      })
      var params = {
        goodsName: this.data.goodsName,
        type: this.data.type,
        id: this.data.uid,
        pageNum: this.data.pageNum + 1
      }
      this.freshGoods(params, true);
      wx.hideLoading()
    } else {
      wx.showToast({
        title: '没有咯',
        icon: 'none',
        duration: 2000,
        mask: true
      })
    }

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  /**
   * 刷新商品列表
   */
  freshGoods: function (params, loadingMore) {
    var that = this
    if (params == undefined) {
      var params = {
        goodsName: this.data.goodsName,
        id: this.data.uid,
        type: this.data.type,
        pageNum: 0
        //  pageSize: this.data.pageSize   
      }
    }
    get('/goods', params).then((res) => {
      if (res.statusCode == '200') {
        console.log(res.data)
        if (loadingMore == undefined) {
          that.setData({
            goodsList: res.data.content,
            pageNum: res.data.number,
            pageSize: res.data.size,
            totalPages: res.data.totalPages
          })
        } else {
          that.setData({
            goodsList: that.data.goodsList.concat(res.data.content),
            pageNum: res.data.number,
            pageSize: res.data.size,
            totalPages: res.data.totalPages
          })
        }


      } else {
        wx.showToast({
          title: res.data.message,
          icon: "none",
          // image: '/pages/images/warning.png',
          duration: 2000
        })
        // console.log(res.data.message)
      }
    });
  },

  /**
   * 查询goods
   */
  serachGoods: function (e) {
    this.setData({
      goodsName: e.detail.value
    })
    this.freshGoods();
  },
  /**
   * 扫码
   */
  scanQr: function () {
    var that = this
    wx.scanCode({
      // onlyFromCamera: true,
      success: (res) => {
        // console.log(res.result)
        let params = {
          goodsId: res.result,
          customerId: that.data.uid
        }

        wx.showModal({
          title: '提示',
          content: '是否确认验货成功？',
          success: function (res) {
            if (res.confirm) {
              post('/goods/goodsDeal', params).then((res) => {
                if (res.statusCode == '200') {
                  wx.showToast({
                    title: "验货收货成功",
                    icon: "success",
                    // image: '/pages/images/warning.png',
                    duration: 2000
                  })
                } else {
                  wx.showToast({
                    title: res.data.message,
                    icon: "none",
                    // image: '/pages/images/warning.png',
                    duration: 2000
                  })
                }
              });

            } else if (res.cancel) {
              console.log('用户点击取消')
            }
          }
        })

      }
    })
  },

  goodsDetails: function (e) {
    wx.navigateTo({
      url: '../goodsDetails/goodsDetails?id=' + e.currentTarget.dataset.id
    })
  }
})