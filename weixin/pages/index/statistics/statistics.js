// pages/index/statistics/statistics.js
import { serviceUrl, get, put, post, del } from '../api/api.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    uid:'',
    gender:'',
    winWidth: 0,
    winHeight: 0,
    // tab切换  
    currentTab: 0,
    nickName:'',
    statistics:[],
    allUsers:[],
    isCare:1,
    isCare: 0//关注里面的是否关注
  
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
        that.getStatistics()
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
    // this.getStatistics();
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
  this.getStatistics();
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
  serachUsers: function(e) {
    this.setData({
      nickName: e.detail.value
    })
    if (this.data.currentTab == 0) {
      this.getStatistics();
    } else {
      this.getAllUsers();
    }
  },
  /** 
   * 获取关注
   */
  getStatistics: function() {
    var that = this
    var params = {
      nickName: this.data.nickName
    }
    get('/subscribe/getUsers/' + this.data.uid, params).then((res) => {
      if (res.statusCode == '200') {
        that.setData({
          statistics: res.data
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
  },
  /** 
   * 获取全部
   * 
   */
  getAllUsers: function() {
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
          // image: '/pages/images/warning.png',
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
   * 关注
   */
  care: function(e) {
    var that = this;
    var params = {
      userId: e.currentTarget.dataset.id
    }
    post('/subscribe/addUser/' + this.data.uid, params).then((res) => {
      if (res.statusCode == '200') {
        wx.showToast({
          title: "关注成功",
          icon: "none",
          // image: '/pages/images/warning.png',
          duration: 2000
        })
        // that.setData({
        //   isCare: 1
        // })
        that.getStatistics();
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

    

  },
  /**
   * 取消关注
   */
  cancelCare: function(e) {
    var that = this;
    var params = {
      passiveId: e.currentTarget.dataset.id
    }
    post('/subscribe/cancel/' + this.data.uid, params).then((res) => {
      if (res.statusCode == '200') {
        wx.showToast({
          title: "取消成功",
          icon: "none",
          // image: '/pages/images/warning.png',
          duration: 2000
        })
        // that.setData({
        //   isCare: 0
        // })
        that.getStatistics();
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

  }


})