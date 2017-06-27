# react-native-scanidcard
react-native 身份证扫描（Android版）

IOS版本将在稍后补上

实现原理为Camera自动对焦成功后获取当前帧进行针对身份证的图像识别，识别后验证身份证，验证通过则返回，不通过则进行2次对焦并识别，直到通过为止

返回的字段包含【姓名】【身份证号】【性别】【年龄】

地址如需返回的，可修改源码的ScanView.class

# 安装
```javascript 
npm install react-native-scanidcard
```

在android目录下的settings.gradle中加入
```javascript
include ':react-native-scanidcard'
project(':react-native-scanidcard').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-scanidcard/android/app')
include ':idcard-library'
project(':idcard-library').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-scanidcard/android/idcard-library')
```

在android/app中的build.gradle中加入
```javascript
    compile project(':react-native-scanidcard')
    compile project(':idcard-library')
```

# 调用demo
```javascript
import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  Vibration,
  View,
  TextInput,
  DeviceEventEmitter
} from 'react-native';

import ScanView from 'react-native-scanidcard';

class ScanScreen extends Component {
  constructor(props) {
    super(props);

    this.state = {
      torchMode: 'off',
      cameraType: 'back'
    };
  }

  componentWillMount() {
    this.listener = DeviceEventEmitter.addListener('scanCallBack', this.scanCallBack.bind(this)); //对应了原生端的名字
  }

  componentWillUnmount() {
    this.listener && this.listener.remove(); //记得remove哦
    this.listener = null;
  }

  scanCallBack(e) {
    console.info(e)
  }

  render() {
    return (
      <View>
        <ScanView 
          style={{ flex: 1 }}
          torchMode={this.state.torchMode}
          cameraType={this.state.cameraType}
        />
      </View>
    );
  }
}

export default ScanScreen;
```
# The End
感谢dreamkid提供的library
