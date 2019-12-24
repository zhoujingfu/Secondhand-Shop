// pages/index/goods/goods.js

import { serviceUrl, get, put, post, del } from '../api/api.js'
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    tabbar: {},
    uid:'',
    goodsList: [],
    pageNum: '',
    pageSize: '',
    goodsName:'',
    totalPages:'',
    index:'',
    type:'',
    typeList: ['全部', '闲置数码', '家具日用', '图书音像', '鞋服配饰', '美妆洗护', '文体户外', '办公用品', '其他'],
    userId:'',
    followIndex:'',
    followList:['全部','已关注'],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    app.editTabbar();
    var that = this
    wx.getStorage({
      key: 'uid',
      success: function (res) {
        that.setData({
          uid: res.data
        })
      }
    })
    this.freshGoods();
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
    this.freshGoods();
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
        id: this.data.userId,
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
  freshGoods: function (params,loadingMore) {
    var that = this
    if (params == undefined) {
       params = {
        goodsName: this.data.goodsName,
        type: this.data.type,
        id: this.data.userId,
        pageNum: 0,
        pageSize: this.data.pageSize   
      }
    } 

    if (this.data.followIndex == 1) {
      get('/goods/getFollow', params).then((res) => {
        if (res.statusCode == '200') {
          that.setData({
            goodsList: res.data
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

    } else {

      get('/goods', params).then((res) => {
        if (res.statusCode == '200') {
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
           console.log(res.data.message)
        }
      });

    }

    


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

  goodsDetails: function(e) {
  wx.navigateTo({
    url: '../goodsDetails/goodsDetails?id=' + e.currentTarget.dataset.id
    })
  },
  bindPickerChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    if (e.detail.value == 0) {
      this.setData({
        index: e.detail.value,
        type: ''
      })
    } else {
      this.setData({
        index: e.detail.value,
        type: this.data.typeList[e.detail.value]
      })
    }
    
    this.freshGoods()
  },
  bindPickerChangeDemo: function(e) {
    var that = this;
    console.log('picker发送选择改变，携带值为', e.detail.value)
    if (e.detail.value == 0) {
      this.setData({
        followIndex: e.detail.value,
        userId: ''
      })
    } else {
      this.setData({
        followIndex: e.detail.value,
        userId: that.data.uid 
      })
    }

    this.freshGoods()
  },
  messege: function() {
    /**跳转 */
    wx.switchTab({
      url: '../message/message',
    })
  },
  adddetial:function () {
  wx.navigateTo({ url: '../publishGoods/publishGoods'})
 }
})