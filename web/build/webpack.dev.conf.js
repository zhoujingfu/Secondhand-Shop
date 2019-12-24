var utils = require('./utils') //封装了一些方法的工具

var webpack = require('webpack') //使用 webpack

var config = require('../config') //使用 config/index.js

var merge = require('webpack-merge') //使用 webpack 配置合并插件

var baseWebpackConfig = require('./webpack.base.conf') // 加载 webpack.base.conf

var HtmlWebpackPlugin = require('html-webpack-plugin') // 使用 html-webpack-plugin 插件，这个插件可以帮我们自动生成 html 并且注入到 .html 文件中

//https://www.npmjs.com/package/friendly-errors-webpack-plugin,可以识别某些类别的Webpack错误并进行清理，聚合和优先排序
var FriendlyErrorsPlugin = require('friendly-errors-webpack-plugin')

// add hot-reload related code to entry chunks
//将 Hol-reload 相对路径添加到 webpack.base.conf 的 对应 entry 前
Object.keys(baseWebpackConfig.entry).forEach(function (name) {
    baseWebpackConfig.entry[name] = ['./build/dev-client'].concat(baseWebpackConfig.entry[name])
})

// 将我们 webpack.dev.conf.js 的配置和 webpack.base.conf.js 的配置合并
module.exports = merge(baseWebpackConfig, {
    module:{
        // 使用 styleLoaders
        rules: utils.styleLoaders({ sourceMap: config.dev.cssSourceMap })
    },

    // 使用 #cheap-module-eval-source-map 模式作为开发辅助调试工具
    // 具体配置请参考https://doc.webpack-china.org/configuration/devtool/
    devtool: '#cheap-module-eval-source-map',
    plugins: [
        // definePlugin 接收字符串插入到代码当中, 需要的话可以写上 JS 的字符串
        new webpack.DefinePlugin({
            'process.env': config.dev.env
        }),

        // https://github.com/glenjamin/webpack-hot-middleware#installation--usage
        // HotModule 插件在页面进行变更的时候只会重回对应的页面模块，不会重绘整个 html 文件
        new webpack.HotModuleReplacementPlugin(),
        //https://doc.webpack-china.org/plugins/no-emit-on-errors-plugin/
        //在编译出现错误时，使用 NoEmitOnErrorsPlugin 来跳过输出阶段。这样可以确保输出资源不会包含错误。
        new webpack.NoEmitOnErrorsPlugin(),

        // https://github.com/ampedandwired/html-webpack-plugin
        // 将 index.html 作为入口，注入 html 代码后生成 index.html文件
        //https://doc.webpack-china.org/plugins/html-webpack-plugin/ webpack插件列表(中文)
        new HtmlWebpackPlugin({
            filename: 'index.html',
            template: 'index.html',
            inject: true
        }),
        //看上面
        new FriendlyErrorsPlugin()
    ]
})