 5.2.1 登录注册模块的设计与实现
    当用户首次使用本系统时，系统会跳转到注册页面。注册本系统后便成为有效用户，注册需要填写学号，并上传手持学生证正面照，以验证用户为本校学生，在验证通过后即可成为本系统的有效用户。 注册成功后，进入系统时默认以微信的方式登录。
    1.界面设计
    首次登录时注册界面如图5-4所示。
                   
                 图5-4 注册界面图
    2.功能实现
    登录功能的核心代码如下：
wx.login({ //获取code
      wx.getSetting({
        if(res.authSetting['scope.userInfo']) { //已授权，直接调用用户基本信息
          wx.getUserInfo({{// 发送 res.code 到后台换取 openId, sessionKey
              get('/weChat/getUserInfo', params).then((res) => { //调用接口
            wx.setStorage({ //缓存用户信息到本地
         key: "uid",
    data: res.data.id, 
     })
});
注册功能的核心代码如下：
    var formData = {
      username: e.detail.value.username,
      password: e.detail.value.password,
      studenid: e.detail.value.qq,
      mobile: e.detail.value.mobile,
    }
post('/user/register/' + this.data.uid, formData).then((res) => {
      url: serviceUrl + '/user/images/upload',//身份凭证上传的接口
            path: that.data.imagesList,//这里是选取的图片的地址数组 
) });}            
    5.2.2 闲置信息模块的设计与实现
    有效用户可以首页浏览闲置信息，在首页的闲置信息模块可以浏览系统中最新发布的闲置信息列表。有效用户可以在首页查看本系统的数据统计，包括有今日通告、总注册人数、总发布数量、总成交量。用户通过查看通告栏可以快速的了解校园动态。用户可以通过点击相应的列表进入闲置信息详情浏览闲置物品的信息。在物品的详情页中，如是用户本人发布的物品则显示“出示二维码”和“删除”按钮。
    1.界面设计
    首页的闲置信息列表界面如图5-5所示，发现页的闲置信息列表如5-6所示，闲置物品详情界面如图5-7所示。
     
图5-5 首页闲置信息界面图   图5-6 发现页闲置信息界面图   图5-7 闲置信息详情界面图
    2.功能实现
    闲置列表功能核心代码如下：
get('/goods', params).then((res) => {//发起网络请求
      if (res.statusCode == '200') {
          if (loadingMore == undefined) {
            that.setData({ //参数接受对象，以key的形式表示
              goodsList: res.data.content,
              pageNum: res.data.number,
              pageSize: res.data.size,
              totalPages: res.data.totalPages
            })
          } 
        } 
      });
  数据统计功能核心代码如下：
get('/statistics/userAndGoodsCount', null).then((res) => {
      if (res.statusCode == '200') {
        that.setData({
          user: res.data
        })
console.log(res.data)
      } 
    });
get ('/notice/findAll', null).then((res) => {
      if (res.statusCode == '200') {
        that.setData({
          notice: res.data[0]
        })  
console.log(res.data)
    });
    5.2.3 评论模块的设计与实现
    有效用户在浏览闲置物品的详情时可以对商品进行评论与卖主沟通，用户可以在商品详情页回复别人的评论以及删除个人评论。用户可以在消息页中查看未读评论、全部评论和回复评论。 
    1.界面设计
    商品的评论信息列表界面如图5-8所示，消息页查看和回复评论如图5-9所示。
          
图5-8 商品评论列表界面图             图5-9信息页评论界面图        

    2.功能实现
    评论功能核心功能如下：
get('/comments/user/' + this.data.uid, null).then((res) => {
      if (res.statusCode == '200') {
        that.setData({
          goodsAllComments: res.data  }) }
    });
    var params = {
      content: event.detail.value.content,
      goodsId: this.data.goodsId,
      replyCommentId: this.data.commentId  }
    post('/comment/add/' + this.data.uid + '/reply/' + this.data.replyId, params).then((res) => {
      if (res.statusCode == '200') {
        wx.showToast({
          title: '评论成功',
        })} 
    });
    5.2.4 发布商品模块的设计与实现
   有效用户可以在系统的首页点击“发布闲置”或者在发现点击绿色的“+”号进行发布商品。用户填写商品信息时采用图文混排的方式，图片的数量的上限为9张，从而能更加清晰的描述商品。有效用户还可以在“我的”中查看发布历史。
1.	界面设计
发布商品界面如图5-10所示。
         
图5-10 发布商品界面图
    2.功能实现
    用户发布闲置功能核心代码如下：
     formData.type = this.data.typeList[formData.type];
    post('/goods/publish/'+ this.data.uid, formData).then((res) => {
      wx.showLoading({
        mask: true
      })
      if (res.statusCode == '200'){
        if (that.data.imagesList.length != 0) {
          let result = uploadimg.uploadimg({
            url: serviceUrl + '/goods/images/upload',//图片上传的接口
            path: that.data.imagesList,//选取的图片的地址数组
            goods: res.data
          });
        }
       }
    });
    5.2.5关注模块的设计与实现
    有效用户可以在查看已关注用户，搜索关注并关注该用户。关注该用户后，可以在商品的发现页筛选该用户的商品，实时了解该用户的动态。
1.	界面设计
已关注界面如图5-11所示，发现用户并关注如图5-12所示。
   
           图5-11已关注计界面图            图5-12发现用户界面图
2.关注功能的核心代码
获取已关注列表核心代码：
    get('/subscribe/getUsers/' + this.data.uid, params).then((res) => {
      if (res.statusCode == '200') {
        that.setData({
          statistics: res.data
        })
      } 
    });
搜索并关注用户核心代码：
serachUsers: function(e) {
    this.setData({
      nickName: e.detail.value
    })
    if (this.data.currentTab == 0) {
      this.getStatistics();
    }
  },
   post('/subscribe/addUser/' + this.data.uid, params).then((res) => {
      if (res.statusCode == '200') 
         that.getAllUsers();
      }
}
5.2.6个人中心模块的设计与实现
    个人中心的界面设计采用微信原生风格，有效用户可以在个人中心快捷的查看个人已发布的物品、已关注的用户。也可以对个人的密码和基本信息进行修改。
1.界面设计
    个人中心界面如图5-13所示，修改密码界面如图5-14所示，修改密码界面如图5-15所示。
     图5-13个人中心界面图        图5-14密码修改界面图          图5-15信息修改界面图
2.个人中心的核心代码
密码修改核心代码：
    post('/user/update/' + this.data.uid, formData).then((res) => {
      if (res.statusCode == '200') {
        wx.hideLoading()
             wx.showToast({
          title: '修改成功',
          icon: 'success',
          duration: 2000,
          mask: true,
          success: function () {
            that.setData({
              userForm: {
                password: '',
                rePassword: ''
              }
            }); 
          }
        }) 
      }
信息修改核心代码：
       post('/user/update/' + this.data.uid, formData).then((res) => {
      wx.showLoading({
        mask: true
      })
      if (res.statusCode == '200') {
        wx.hideLoading()
        wx.showToast({
          title: '修改成功',
          icon: 'success',
          duration: 2000,
          mask: true,
      }
      });
     }
 

