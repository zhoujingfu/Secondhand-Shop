// pages/index/goodsDetails/goodsDetails.js
const app = getApp()
import { serviceUrl, get, put, post, del } from '../api/api.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    uid: '',
    goodsId:'',
    goodsDetails: '',
    goodsComments: '',
    content:'',
    replyId:'',
    commentId:'',
    releaseName: '',
    releaseFocus: false,
    role: ''

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // console.log(options.id)
    var that = this
    wx.getStorage({
      key: 'uid',
      success: function (res) {
        that.setData({
          uid: res.data,
          goodsId: options.id
        })
      }
    })
    this.setData({
      role: app.globalData.role 
    })
    this.getGoodsDetails(options.id)
    this.getGoodsComment(options.id)
    console.log("用户角色    " + this.data.role)
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

  getGoodsDetails: function (id) {
    var that = this
    get('/goods/' + id, null).then((res) => {
      if (res.statusCode == '200') {
        that.setData({
          goodsDetails: res.data
        })
      } else {
        wx.showToast({
          title: res.data.message,
          icon: "none",
          // image: '/pages/images/warning.png',
          duration: 2000
        })
        setTimeout(function(){
          wx.navigateBack({
            delta: 1
          })
        },500)
        
      }
    });
  },

  getGoodsComment : function(id) {
    var that = this
    get('/comments/' + id, null).then((res) => {
      if (res.statusCode == '200') {
        that.setData({
          goodsComments: res.data
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
  replyCommentHandler: function (event) {
    // console.log(event.detail.value.content);
    // console.log(event.currentTarget.dataset);
    var that = this;
    console.log(event.detail.value)
    if (event.detail.value.content == "") {
      wx.showToast({
        title: "评论不能为空",
        icon: "none",
        // image: '/pages/images/warning.png',
        duration: 2000
      })
      return false;
    }
    var params = {
      content: event.detail.value.content,
      goodsId: this.data.goodsId,
      replyCommentId: this.data.commentId
    }
    post('/comment/add/' + this.data.uid + '/reply/' + this.data.replyId, params).then((res) =>     {
      if (res.statusCode == '200') {
        wx.showToast({
          title: '评论成功',
          icon: 'success',
          duration: 2000,
          mask: true,
          success: function () {
            that.setData({
              content:''
            });
            that.getGoodsComment(that.data.goodsId);
          }
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
* 点击回复
*/
  bindReply: function (e) {
    this.setData({
      releaseFocus: true,
      replyId: e.currentTarget.dataset.replyid ,
      commentId: e.currentTarget.dataset.commentid,
      releaseName: e.currentTarget.dataset.nickname 
    })
  },
  /**
   * 长按删除
   */
  deleteReply: function(e){
    var that = this
    if (this.data.uid == e.currentTarget.dataset.replyid) {

      wx.showModal({
        title: '提示',
        content: '是否删除该评论？',
        success: function (res) {
          if (res.confirm) {
            post('/comment/delete/' + e.currentTarget.dataset.commentid, null).then((res) => {
              if (res.statusCode == '200') {
                wx.showToast({
                  title: "删除成功",
                  icon: "success",
                  duration: 2000
                })
                that.getGoodsComment(that.data.goodsId);
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
  },



//  删除
  deleteOperation: function () {
    var that = this;
      wx.showModal({
            title: '提示',
            content: '是否删除该商品？',
            success: function (res) {
              if (res.confirm) {
                post('/goods/delete/' + that.data.goodsDetails.id, null).then((res) => {
                  if (res.statusCode == '200') {
                    wx.showToast({
                      title: "删除成功",
                      icon: "success",
                      duration: 2000
                    })
                    wx.navigateBack({
                      delta: 1
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
               },
      fail: function (res) {
        console.log(res.errMsg)
      }
    })
},
// 生成二维码
moreOperation: function () {
    var that = this;
     wx.navigateTo({
         url: '../qr/qr?id=' + that.data.goodsDetails.id + '&goodsName=' + that.data.goodsDetails.goodsName
       })
  },
  home:function(){
    wx.switchTab({
      url: '../index/index',
    })
  }
})