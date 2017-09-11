# KExtensions
[![](https://jitpack.io/v/zyyoona7/KExtensions.svg)](https://jitpack.io/#zyyoona7/KExtensions)

### 简介

 收集 Android 中常用的 Kotlin 扩展函数，代替 Java 工具类和部分基类代码。

### 依赖

Step 1. Add the JitPack repository to your build file
```gradle
allprojects {
    repositories {
    //...
    maven { url 'https://jitpack.io' }
    }
}
```
Step 2. Add the dependency
```gradle
dependencies {
    compile 'com.github.zyyoona7:KExtensions:v1.0.0'
}
```

### 特性 & 使用

- **appInfo.kt**：Application信息相关扩展函数

  作用范围：Context、Fragment

  ```kotlin
  //获取应用的版本名称
  getVersionName()
  //获取App版本码
  getVersionCode()
  //安装App
  installApp(params)
  //判断App是否安装
  isInstallApp(pkgName)
  //判断App是否处于前台
  isAppForeground()
  //是否是平板设备
  isTablet()
  ```


- **dates.kt**：日期转换相关的拓展函数

  ```kotlin
  //(作用范围：Any) 当前时间毫秒值                          
  currentTimeMills
  //(作用范围：Any) 当前时间格式化成指定格式的String类型       
  currentTimeString()
  //(作用范围：Any) 当前时间的Date类型          			   
  currentDate

  //(作用范围：Date) Date类型格式化成指定格式的String类型 
  format2String(params)
  //(作用范围：Long) Long类型格式化成指定格式的String类型的日期
  format2DateString()
  //(作用范围：Any) 解析String类型的日期为Long类型
  parseDateString2Mills(params)
  //(作用范围：Any) 解析String类型的日期为Date类型
  parseString2Date

  //(作用范围：Any，Calendar，Date，Long)
  //获取两个日期的时间差
  getTimeSpan(params)

  //作用范围：Any，Date，Long)
  //将时间戳转换成 xx小时前 的样式（同微博）
  formatAgoStyleForWeibo()
  //作用范围：Any，Date，Long)
  //将时间戳转换成 xx小时前 的样式（同微信）
  formatAgoStyleForWeChat()

  //作用范围：Date，Long)
  //判断日期是否在同一年
  isSameYear(params)

  //作用范围：Date) 日期是否在两个日期之间
  betweenDates(params)

  //作用范围：Calendar) 将日期时间设置为0点，00:00:00:0
  ofTimeZero()
  //(作用范围：Any，Calendar，Date，Long)
  //获取星期的下标
  dayOfWeek()/dayOfWeek
  ```

- **dimensions.kt**：尺寸、尺寸转换 扩展函数

  作用范围：Context、Fragment、View

  ```kotlin
  //屏幕宽度
  screenWidth
  //屏幕高度
  screenHeight
  //dp转换为px value类型为Int或Float
  dip2px(value)
  //sp转换为px value类型为Int或Float
  sp2px(value)
  //dimen资源转换为px
  dimen2px(R.dimen.value)
  //px转换为dp value类型为Int
  px2dip(value)
  //px转换为sp value类型为Int
  px2sp(value)
  ```

- **fileProviders.kt**：FileProvider相关扩展函数

  作用范围：Context、Fragment

  ```kotlin
  //自行配置FileProvider
  //获取对应的Uri 适配7.0+
  getUriFromFile(file,authority)
  //为Intent设置dataAndType 适配7.0+
  setIntentDataAndType(intent,type,file,authority,writeEnable)
  ```

- **files.kt**：文件相关的扩展函数

  ```kotlin
  //作用范围：Context、Fragment

  //获取应用文件目录
  fileDirPath
  //获取应用缓存目录
  cacheDirPath
  //获取应用外置文件目录
  externalFileDirPath
  //获取应用外置缓存目录
  externalCacheDirPath

  //作用范围：File or Any

  //获取公共下载文件夹路径
  publicDownloadDir
  //获取公共的照片文件夹路径
  publicDCIMDir
  //获取公共的图片文件夹路径
  publicPictureDir
  //获取公共的音乐文件夹路径
  publicMusicDir
  //获取公共的电影文件夹路径
  publicMovieDir
  //内存卡是否挂载
  isExternalStorageWritable

  //（Any）通过文件路径获取File对象
  getFileByPath(filePath)
  //（File、Any）判断文件是否存在
  isFileExists  isFileExists()
  //（File、Any）判断文件夹是否存在
  isDirExists  isDirExists()
  //（File、Any）判断目录是否存在，不存在则判断是否创建成功
  createOrExistsDir()
  //（File、Any）判断文件是否存在，不存在则判断是否创建成功
  createOrExistsFile
  //（File、Any）获取文件夹目录大小
  dirSize  getDirSize()
  //（File、Any）获取目录长度
  dirLength  getDirLength()
  //（File、Any）获取文件大小
  fileSize  getFileSize()
  //（File、Any）获取文件长度
  fileLength  getFileLength()
  //（File、Any）获取全路径中的最长目录
  dirName  getDirName(path)
  //（File、Any）获取全路径中的文件名
  fileName  getFileName(path)

  //读/写文件

  //（File、Any）将字符串写入文件
  writeStringAsFile(params)
  //（File、Any）将输入流写入文件
  writeISAsFile(params)
  //（File、Any）读取文件到字符串中
  readFileAsString(params)
  //（File、Any）读取文件到字符串列表中 
  //Do not use this function for huge files.
  readFileAsList(params)

  //  ---------- 文件操作：复制、移动、删除----------

  //（File、Any）复制或移动目录（默认为复制目录）
  copyOrMoveDir(params)
  //（File、Any）复制或移动文件（默认为复制文件）
  copyOrMoveFile(params)
  //（File、Any）删除文件夹
  deleteDir()
  //（File、Any）删除文件
  deleteFile()
  ```

- **intents.kt**：startActivity/startService 扩展函数  

  作用范围：Context、Fragment

  ```kotlin
  //startActivity
  startActivity<AnyActivity>()
  startActivity<AnyActivity>(bundle)
  startActivityForResult<AnyActivity>(requestCode)
  startActivityForResult<AnyActivity>(bundle,requestCode)

  //startService
  startService<AnyService>()
  startService<AnyService>(bundle)
  ```


- **keyboard.kt**：软键盘操作扩展函数

  作用范围：Context、Fragment、View

  ```kotlin
  showSoftInput(view)
  hideSoftInput(view)
  hideSoftInput(Activity)
  ```

- **logs.kt**：Log 打印扩展函数  

  作用范围：Any

  ```kotlin
  //全局控制是否输出log
  logEnabled(true)
  //定义全局的tag
  logGlobalTag("GlobalTag")
  //verbose级别
  logv("Hello")
  //debug级别，自定义某个log的tag
  logd("Hello",customTag = "customTag")
  //info级别
  logi("Hello")
  //warn级别
  logw("Hello")
  //error级别
  loge("Hello")
  //打印json
  val json = """         {'name':'zyyoona7','age':'25'}   """
  logJson(json)
  ```

  **效果图**

  ![log.png](https://github.com/zyyoona7/KExtensions/blob/master/images/log.png)


- **network.kt**：网络相关扩展函数



- 作用范围：Context、Fragment

  ```kotlin
  //活动网络信息
  networkInfo
  //网络是否连接
  isNetworkConnected
  //判断/设置wifi是否打开（var类型）
  isWifiEnable
  //是否是WiFi连接
  isWifiConnected
  //是否是移动数据连接
  isMobileConnected
  //获取网络运营商名称 如中国移动、中国联通、中国电信
  networkOperatorName
  //获取当前网络类型
  // NETWORK_WIFI,NETWORK_4G,NETWORK_3G,NETWORK_2G,NETWORK_UNKNOWN,NETWORK_NO
  networkType
  ```

- **permissions.kt**：动态权限相关扩展函数

  ```kotlin
  //作用范围：Context、Fragment 单个权限是否已经授权
  isPermissionGranted(permission)
  //作用范围：Context、Fragment 多个权限是否已经授权
  arePermissionGranted(permissions)
  //作用范围：Context、Fragment 单个权限是否被拒绝或不再提示
  isPermissionAlwaysDenied(permission)
  //作用范围：Context、Fragment 多个权限是否被拒绝或不再提示
  arePermissionAlwaysDenied(permission)
  //作用范围：Activity、Fragment 请求[permissions]授权
  requestPermission(permissionsArray,requestCode)
  //作用范围：Activity、Fragment 带解释的请求[permissions]授权
  requestPermissionWithRationale(permissionsArray,requestCode,rationaleLamb)

  //作用范围：Any 处理请求权限结果
  handlePermissionResult(params)
  ```

- **resources.kt**：资源相关扩展函数

  ```kotlin
  //作用范围：Context、Fragment、View
  loadColor(colorRes)
  loadDrawable(drawableRes)
  loadRaw(rawRes)
  loadAsset(filName)
  loadTypefaceFromAsset(fileName)
  //作用范围：Any
  loadTypefaceFromFile(filePath)
  ```

- **systemService.kt**：需要通过 getSystemService() 获取的各种 manager

  作用范围：Context

  ```kotlin
  activityManager
  alarmManager
  bluetoothManager
  connectivityManager
  inputMethodManager
  //...
  ```

- **toast.kt**：Toast 扩展函数

  作用范围：Context、Fragment、View

  ```kotlin
  //默认为short Toast
  toast("Hello")
  toast("Hello",Toast.LENGTH_LONG)
  ```

- **views.kt**：view 扩展函数

  ```kotlin
  //为view添加OnGlobalLayoutListener监听并在测量完成后自动取消监听同时执行[globalAction]函数
  afterMeasured(lambda)
  ```

- **更多**：持续更新中 ...
### 感谢

**[AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode)**

### License
```
Copyright 2017 zyyoona7

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
