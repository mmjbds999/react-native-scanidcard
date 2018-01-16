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
import {Dimensions} from 'react-native'

const w = Dimensions.get('window').width

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
    alert(e.name+":"+e.id);
    //this.setState({
    //  idcard : e.id,
    //  name : e.name,
    //  sex: e.sex,
    //  age: e.age
    //})
  }

  render() {
    return (
      <ScanView 
          style={{ flex: 1 }}
          torchMode={this.state.torchMode}
          cameraType={this.state.cameraType}>
      </ScanView>
    );
  }

}

export default ScanScreen;