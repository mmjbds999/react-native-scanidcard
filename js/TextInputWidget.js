import React, { Component } from 'react';  
var {  
  StyleSheet,  
  View,  
  Text,  
  Image,  
  TextInput,  
  PixelRatio,  
  Dimensions  
} = require('react-native')  
  
  
var screenWidth = Dimensions.get('window').width;  
  
  
export default class TextImageWidget extends Component{  
  
    constructor(props) {  
      super(props);  
    }  
  
  _renderRow() {  
    return (  
      <View style={styles.rowContainer}  >  
        <View style={styles.row}>  
          <Text  
            numberOfLines={1}  
            style={styles.textInputTitle} >  
            {this.props.title}  ：{this.props.value}
          </Text>  
        </View>  
      </View>  
    );  
  
  }  
  
  render() {  
    return this._renderRow();  
  }  
}  
  
const styles = StyleSheet.create({  
    rowContainer: {  
      backgroundColor: '#FFF',  
      width:screenWidth,  
    },  
    row: {  
      flexDirection: 'row',  
      height: 44,  
      alignItems: 'center',  
      marginRight: 15,  
      marginLeft: 15,  
      //paddingTop:15,  
      borderBottomWidth: 0.5 / PixelRatio.get(),  
      borderColor:'gray',//需要标色  
  
    },  
    textInputTitle: {  
      fontSize: 13,  
      //color: '#333',  
      //backgroundColor: 'red',  
    },  
    textImage: {  
      flex: 1,  
      height: 44,// @todo should be changed if underlined  
      justifyContent:'flex-end',  
      flexDirection: 'row',  
  
    },  
    image:{  
      width:38,  
      height:38,  
      backgroundColor:'gray',  
      borderRadius: 19,  
    },  
    rightArrow:{  
      paddingLeft:10,  
      //backgroundColor:'red',  
  
    }  
  
  }); 