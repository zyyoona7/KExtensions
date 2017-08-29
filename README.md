# KExtensions
### 简介

 收集 Android 中常用的 Kotlin 扩展函数，代替 Java 工具类和部分基类代码。

### 特性

- 用法简单，命名通俗易懂
- 没有强制依赖，可以自由引入

### 使用

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


- **network.kt**：网络相关扩展函数

  作用范围：Context、Fragment

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

- **toast.kt**：Toast 扩展函数

  作用范围：Context、Fragment、View

  ```kotlin
  //默认为short Toast
  toast("Hello")
  toast("Hello",Toast.LENGTH_LONG)
  ```

- **keyboard.kt**：软键盘操作扩展函数

  作用范围：Context、Fragment、View

  ```kotlin
  showSoftInput(view)
  hideSoftInput(view)
  hideSoftInput(Activity)
  ```

- **resources.kt**：资源相关的扩展函数

  ```kotlin
  loadColor(colorRes)
  loadDrawable(drawableRes)
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

- **更多**：持续更新中 ...


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
