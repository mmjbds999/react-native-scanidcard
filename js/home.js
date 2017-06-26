'use strict'

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TouchableHighlight
} from 'react-native';

import { StackNavigator } from 'react-navigation'; 
import ScanScreen from '../js/scan.js'

class HomeScreen extends Component {

  static navigationOptions = {
     title: 'Home',    //设置navigator的title
  }

  render() {
    const { navigate } = this.props.navigation;
    

    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          扫描身份证咯
        </Text>
        <TouchableHighlight style={styles.touchables} onPress={()=>navigate('Scan')}>
            <Text style={styles.touchablesText}>点我开始</Text>
         </TouchableHighlight>
      </View>
    );
  }
}

//生成路由关系
const SimpleApp = StackNavigator({
  Home: {
  //对应界面MyHomeScreen
    screen: HomeScreen,
  },
  Scan: {
    screen: ScanScreen,
  },
});


const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  touchablesText: {
      color: 'white',
      fontSize: 20,
      textAlign: 'center',
  },
  touchables: {
      margin: 10,
      backgroundColor: '#ff9999',
      width: 150,
      height: 50,
      borderRadius: 20,
      justifyContent: 'center',
  },
});

export default SimpleApp; 
