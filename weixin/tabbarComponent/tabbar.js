// tabBarComponent/tabBar.js
const app = getApp();
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    tabbar: {
      type: Object,
      value: {
        "backgroundColor": "#ffffff",
        "color": "#979795",
        "selectedColor": "#1c1c1b",
        "list": [
          {
            "pagePath": "pages/index/index/index",
            "iconPath": "icon/portal.png",
            "selectedIconPath": "icon/portal-fill.png",
            "text": "首页"
          },
          {
            "pagePath": "pages/index/goods/goods",
            "iconPath": "icon/find.png",
            "selectedIconPath": "icon/find-fill.png",
            "text": "发现"
          },
          {
            "pagePath": "pages/index/publishGoods/publishGoods",
            "iconPath": "icon/input.png",
            "isSpecial": true,
            "text": "发布"
          },
          {
            "pagePath": "pages/index/message/message",
            "iconPath": "icon/info.png",
            "selectedIconPath": "icon/info-fill.png",
            "text": "消息"
          },
          {
            "pagePath": "pages/index/personal/personal",
            "iconPath": "icon/user.png",
            "selectedIconPath": "icon/user-fill.png",
            "text": "我的"
          }
        ]
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    isIphoneX: app.globalData.systemInfo.model == "iPhone X" ? true : false,
  },

  /**
   * 组件的方法列表
   */
  methods: {

  }
})
