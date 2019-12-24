系统介绍
===========================
系统的客户端为微信小程序，整体的技术架构比较清晰--weixn
本系统的管理端是VUE的渐进式框架--web
服务端是以Spring Boot+JPA为框架来开发实现的-server

## 系统主要功能
https://github.com/zhoujingfu/Secondhand-Shop/weixin/img/0.png
### 1.登录注册模块的设计与实现
    当用户首次使用本系统时，系统会跳转到注册页面。注册本系统后便成为有效用户，注册需要填写学号，并上传手持学生证正面照，以验证用户为本校学生，在验证通过后即可成为本系统的有效用户。 注册成功后，进入系统时默认以微信的方式登录。
    首次登录时注册界面如图5-4所示。
    https://github.com/zhoujingfu/Secondhand-Shop/weixin/img/1.png
#### 登录功能的核心代码:
```javascript
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

```
#### 注册功能的核心代码如下:
```javascript
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
```

### 2.闲置信息模块的设计与实现
    有效用户可以首页浏览闲置信息，在首页的闲置信息模块可以浏览系统中最新发布的闲置信息列表。有效用户可以在首页查看本系统的数据统计，包括有今日通告、总注册人数、总发布数量、总成交量。用户通过查看通告栏可以快速的了解校园动态。用户可以通过点击相应的列表进入闲置信息详情浏览闲置物品的信息。在物品的详情页中，如是用户本人发布的物品则显示“出示二维码”和“删除”按钮。
    首页的闲置信息列表界面
    https://github.com/zhoujingfu/Secondhand-Shop/weixin/img/2.png
    发现页的闲置信息页面
    https://github.com/zhoujingfu/Secondhand-Shop/weixin/img/3.png
    闲置物品详情界面
    https://github.com/zhoujingfu/Secondhand-Shop/weixin/img/4.png
    
#### 闲置列表功能核心代码:
```javascript
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
```
#### 数据统计功能核心代码:
```javascript
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
```
### 3.评论模块的设计与实现
    有效用户在浏览闲置物品的详情时可以对商品进行评论与卖主沟通，用户可以在商品详情页回复别人的评论以及删除个人评论。用户可以在消息页中查看未读评论、全部评论和回复评论。
    商品的评论信息列表界面
    https://github.com/zhoujingfu/Secondhand-Shop/weixin/img/5.png
    消息页查看和回复评论界面
    https://github.com/zhoujingfu/Secondhand-Shop/weixin/img/6.png
#### 登录功能的核心代码:
```javascript
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

```
#### 评论功能核心代码:
```javascript
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
```
### 4.发布商品模块的设计与实现
    有效用户可以在系统的首页点击“发布闲置”或者在发现点击绿色的“+”号进行发布商品。用户填写商品信息时采用图文混排的方式，图片的数量的上限为9张，从而能更加清晰的描述商品。有效用户还可以在“我的”中查看发布历史。
    发布商品界面
    https://github.com/zhoujingfu/Secondhand-Shop/weixin/img/7.png
#### 用户发布闲置功能核心代码:
```javascript
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

```
### 5.关注模块的设计与实现
    有效用户可以在查看已关注用户，搜索关注并关注该用户。关注该用户后，可以在商品的发现页筛选该用户的商品，实时了解该用户的动态。
    已关注界面如图
    https://github.com/zhoujingfu/Secondhand-Shop/weixin/img/8.png
    发现用户并关注
    https://github.com/zhoujingfu/Secondhand-Shop/weixin/img/9.png
#### 获取已关注列表核心代码：:
```javascript
 get('/subscribe/getUsers/' + this.data.uid, params).then((res) => {
      if (res.statusCode == '200') {
        that.setData({
          statistics: res.data
        })
      } 
    });
```
#### 搜索并关注用户核心代码:
```javascript
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
```
### 6.个人中心模块的设计与实现
    个人中心的界面设计采用微信原生风格，有效用户可以在个人中心快捷的查看个人已发布的物品、已关注的用户。也可以对个人的密码和基本信息进行修改。
    个人中心界面如图
    https://github.com/zhoujingfu/Secondhand-Shop/weixin/img/10.png
    修改密码界面如图
    https://github.com/zhoujingfu/Secondhand-Shop/weixin/img/11.png
    修改密码界面
    https://github.com/zhoujingfu/Secondhand-Shop/weixin/img/12.png
#### 密码修改核心代码:
```javascript
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
```
#### 信息修改核心代码:
```javascript
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
```
