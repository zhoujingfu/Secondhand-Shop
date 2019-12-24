//index.js
//获取应用实例
const app = getApp()
import { get } from '../api/api.js'
import { createQrCodeImg } from '../qrcode/wxqrcode.js'
import * as echarts from '../../../ec-canvas/echarts.js';

Page({
  data: {
    tabbar: {},
    uid: '',
    goodsList: [],
    pageNum: '',
    pageSize: '',
    goodsName: '',
    totalPages: '',
    index: '',
    type: '',
    typeList: ['全部', '闲置数码', '家具日用', '图书音像', '鞋服配饰', '美妆洗护', '文体户外', '办公用品', '其他'],
    userId: '',
    followIndex: '',
    followList: ['全部', '已关注'],
    tabbar: {},
    imgUrls: [
      '../../images/1.jpg',
      '../../images/2.jpg',
  '../../images/3.jpg'
    ],
    indicatorDots: true,
    autoplay: true,
    interval1: 5000,
    duration: 1000,
    windowWidth: 320,
    noticeIdx: 0,
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    user:[],
    notice:[],
    text: "小明同学，有人捡到了你的饭卡，请速到饭卡充值处领取你的饭卡！请快点",
    marqueePace: 0.5,//滚动速度
    marqueeDistance: 0,//初始滚动距离
    marquee_margin: 0,
    size: 14,
    interval: 20 // 时间间隔

  },
  //事件处理函数
  bindViewTap: function () {
    wx.navigateTo({
      url: '../publishGoods/publishGoods'
    })
  },
  onLoad: function (e ) {
   
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
    this.getNotice();
  },
  onShow: function () {
    var that = this;
    var length = that.data.text.length * that.data.size;//文字长度
    var windowWidth = wx.getSystemInfoSync().windowWidth;// 屏幕宽度
    //console.log(length,windowWidth);
    that.setData({
      length: length,
      windowWidth: windowWidth
    });
    that.scrolltxt();// 第一个字消失后立即从右边出现

    this.freshGoods();
  
  },
  scrolltxt: function () {
    var that = this;
    var length = that.data.length;//滚动文字的宽度
    var windowWidth = that.data.windowWidth;//屏幕宽度
    if (length > windowWidth) {
      var interval = setInterval(function () {
        var maxscrollwidth = length + that.data.marquee_margin;//滚动的最大宽度，文字宽度+间距，如果需要一行文字滚完后再显示第二行可以修改marquee_margin值等于windowWidth即可
        var crentleft = that.data.marqueeDistance;
        if (crentleft < maxscrollwidth) {//判断是否滚动到最大宽度
          that.setData({
            marqueeDistance: crentleft + that.data.marqueePace
          })
        }
        else {
          //console.log("替换");
          that.setData({
            marqueeDistance: 0 // 直接重新滚动
          });
          clearInterval(interval);
          that.scrolltxt();
        }
      }, that.data.interval);
    }
    else {
      that.setData({ marquee_margin: "1000" });//只显示一条不滚动右边间距加大，防止重复显示
    }
  },
  onReady() {
  this.getGoodsCountByType()
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
        title: '没有更多了',
        icon: 'none',
        duration: 2000,
        mask: true
      })
    }
    this.freshGoods();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  // 获取系统数据
  getGoodsCountByType: function () {
    var that = this
    get('/statistics/userAndGoodsCount', null).then((res) => {
      if (res.statusCode == '200') {
        that.setData({
          user: res.data
        })
        console.log(res.data)
      } else {
        wx.showToast({
          title: res.data.message,
          icon: "none",
          // image: '/pages/images/warning.png',
          duration: 2000
        })
      }
    });
  },
  getNotice: function () {
    var that = this
    get ('/notice/findAll', null).then((res) => {
      if (res.statusCode == '200') {
        that.setData({
          notice: res.data[0]
        })
        console.log(res.data)
      } else {
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
   * 刷新商品列表
   */
  freshGoods: function (params, loadingMore) {
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
          //wx.showToast({
            //title: res.data.message,
           // icon: "none",
            // image: '/pages/images/warning.png',
           // duration: 2000
          //})
        }
      });

    } 
    else {

      get('/goods', params).then((res) => {
        if (res.statusCode == '200') {
          if (loadingMore == undefined) {
            that.setData({
              goodsList: res.data.content,
              pageNum: res.data.number,
              pageSize: res.data.size,
              totalPages: res.data.totalPages
            })
          }
          else{
            // that.setData({
            //   goodsList: that.data.goodsList.concat(res.data.content),
            //   pageNum: res.data.number,
            //   pageSize: res.data.size,
            //   totalPages: res.data.totalPages
            // })
            console.log(res.data.message)
          }


        } else
         {
          // wx.showToast({
          // title: res.data.message,
          //   icon: "none",
          //   // image: '/pages/images/warning.png',
          //   duration: 2000
          // })
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

  goodsDetails: function (e) {
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
  bindPickerChangeDemo: function (e) {
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
  messege: function () {
    /**跳转 */
    wx.switchTab({
      url: '../message/message',
    })
  },
  adddetial: function () {
    wx.navigateTo({ url: '../publishGoods/publishGoods' })
  },
  goods: function () {
    wx.switchTab({
      url: '../goods/goods'
    })
  },
  addgoods: function () {
    wx.navigateTo({
      url: '../publishGoods/publishGoods',
    })
  },
  statistics: function () {
    wx.navigateTo({
      url: '../statistics/statistics'
    })
  },
  personalGoods: function () {
    wx.navigateTo({
      url: '../personalGoods/personalGoods'
    })
  },
  question: function () {
    wx.navigateTo({
      url: '../activity/activity'
    })

  },

})


