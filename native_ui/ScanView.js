'use strict';

import React, {
  Component
} from 'react';
import {
  requireNativeComponent,
  StyleSheet,
  View,
  Text,
} from 'react-native';
import PropTypes from 'prop-types';

import Viewfinder from './Viewfinder';

class ScanView extends Component {
  constructor(props) {
    super(props);

  }

  render() {
   
    return (
      <RCTScanView {...this.props} >
        
      </RCTScanView>
    );
  }
}

ScanView.propTypes = {
  ...View.propTypes,
  cameraType: PropTypes.string,
  torchMode: PropTypes.string,
  
};

ScanView.defaultProps = {
  showViewFinder: true,
};

var RCTScanView = requireNativeComponent('RCTScanView', ScanView, {
  nativeOnly: {onChange: true}
});

module.exports = ScanView;
