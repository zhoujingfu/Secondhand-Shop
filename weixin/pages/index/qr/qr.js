// pages/index/qr/qr.js
import { createQrCodeImg } from '../qrcode/wxqrcode.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    qrcode: '',
    goodsId:'',
    goodsName:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options.id)
    this.setData({
      goodsId: options.id,
      goodsName: options.goodsName 
    })
    this.createQrCodeImg(options.id)
    
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
   * 生成二维码
   */
  createQrCodeImg: function (e) {
    var that = this
    let img = createQrCodeImg(e, { 'size': 300 })
    that.setData({
      qrcode: img
    });

  }
})