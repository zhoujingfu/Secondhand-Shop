// pages/index/statistics/statistics.js
import { serviceUrl, get, put, post, del } from '../api/api.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    uid: '',
    winWidth: 0,
    winHeight: 0,
    // tab切换  
    currentTab: 0,
    nickName: '',
    allUsers: [],
    state:''
    // isCare:1,//关注里面的是否关注

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    var that = this;
    wx.getStorage({
      key: 'uid',
      success: function (res) {
        that.setData({
          uid: res.data
        })
      }
    })
    /** 
     * 获取系统信息 
     */
    wx.getSystemInfo({

      success: function (res) {
        that.setData({
          winWidth: res.windowWidth,
          winHeight: res.windowHeight
        });
      }

    });
    
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
    * 滑动切换tab 
    */
  bindChange: function (e) {

    var that = this;
    that.setData({ currentTab: e.detail.current });

  },
  /** 
   * 点击tab切换 
   */
  swichNav: function (e) {

    var that = this;

    if (this.data.currentTab === e.target.dataset.current) {
      return false;
    } else {
      that.setData({
        currentTab: e.target.dataset.current
      })
    }
  },
  /**
   *查询
   */
  serachUsers: function (e) {
    this.setData({
      nickName: e.detail.value
    })
    this.getAllUsers();
  },
  /** 
   * 获取全部
   * 
   */
  getAllUsers: function () {
    var that = this
    var params = {
      nickName: this.data.nickName
    }
    get('/subscribe/getAllUsers/' + this.data.uid, params).then((res) => {
      if (res.statusCode == '200') {
        that.setData({
          allUsers: res.data
        })
      } else {
        wx.showToast({
          title: res.data.message,
          icon: "none",
          image: '/pages/images/warning.png',
          duration: 2000
        })
      }
    });

  },
  getAllUsers: function () {
    var that = this
    var params = {
      nickName: this.data.nickName
    }
    get('/subscribe/getAllUsers/' + this.data.uid, params).then((res) => {
      if (res.statusCode == '200') {
        that.setData({
          allUsers: res.data
        })
      } else {
        wx.showToast({
          title: res.data.message,
          icon: "none",
          image: '/pages/images/warning.png',
          duration: 2000
        })
      }
    });

  },
  goCancel: function () {
    wx: wx.navigateBack({
      delta: 1,
    });
  },

  /**
   * mannage
   */
  userAdmin: function (e) {
    var that = this;
    
    wx.showModal({
      title: '提示',
      content: '是否该用户改为' + e.currentTarget.dataset.message,
      success: function (res) {
        if (res.confirm) {
          post('/admin/personal/management/' + e.currentTarget.dataset.id + '/state/' + e.currentTarget.dataset.state, null).then((res) => {
            if (res.statusCode == '200') {
              // that.setData({
              //   state: res.data
              // })
              that.getAllUsers();
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