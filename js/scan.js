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

import ScanView from '../native_ui/ScanView.js';
import TextInputWidget from './TextInputWidget.js';

class ScanScreen extends Component {
  constructor(props) {
    super(props);

    this.state = {
      torchMode: 'off',
      cameraType: 'back',
      idcard: '身份证号',
      name: '',
      sex: '',
      age: ''
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
    this.setState({
      idcard : e.id,
      name : e.name,
      sex: e.sex,
      age: e.age
    })
  }

  render() {
    return (
      <View>
        <ScanView 
          style={{ flex: 1 }}
          torchMode={this.state.torchMode}
          cameraType={this.state.cameraType}
        />

        <View style={{
          marginTop:260,
        }}>
          <TextInputWidget  
            title='身份证号'  
            value={this.state.idcard} />  

          <TextInputWidget  
            title='姓名'  
            value={this.state.name} /> 
    
          <TextInputWidget  
              title='性别' 
              value={this.state.sex}/>  
    
          <TextInputWidget  
            title='年龄'  
            value={this.state.age} />  
    
        </View>
      </View>
    );
  }
}

export default ScanScreen;