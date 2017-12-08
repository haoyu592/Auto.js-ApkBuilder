# Auto.js-ApkBuilder

一个把Auto.js的js文件"打包"成apk文件的项目。

## 原理

该项目中的"打包"并不是真正的打包，而是用一个事先编译好的apk文件，通过替换里面的js文件、修改应用包名、名称、版本等信息再重新签名实现。

* 版本(versionName), 版本号(versionCode), 名称(label): 通过魔改[pxb axml](https://github.com/wtchoi/axml)项目实现AXML的解析、修改、重新编译
* 包名(packageName): 包名需要修改resources.arsc的包名([ArscEditor](https://github.com/seaase/ArscEditor))和AndroidManifest中的包名
* 签名: [TinySign](https://code.google.com/archive/p/tiny-sign/downloads)

## 感谢

感谢[Apk编辑器](https://www.coolapk.com/apk/zhao.apkmodifier)的作者[seaase](https://github.com/seaase)的支持和帮助！！