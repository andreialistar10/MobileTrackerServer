import React, {useState} from 'react';
import LinearGradient from 'react-native-linear-gradient';
import {Dimensions} from 'react-native';
import {HEADER_HEIGHT} from '../style';

const dimensions = Dimensions.get('window');

const MobileTrackerPhoneContainer = ({children}) => {
  const [actualRootDimensions, setActualRootDimensions] = useState({
    width: dimensions.width,
    height: dimensions.height,
  });
  const getStyleForRoot = () => {
    let {height} = actualRootDimensions;
    height -= HEADER_HEIGHT;
    return {minHeight: height};
  };
  const resize = () => {
    const {width, height} = Dimensions.get('window');
    setActualRootDimensions({width, height});
  };
  return (
    <LinearGradient
      style={getStyleForRoot()}
      colors={['rgba(207, 241, 238,0.4)', 'rgba(175,228,241, 1)']}
      start={{x: 0, y: 0}}
      end={{x: 1, y: 0}}
      onLayout={resize}>
      {children}
    </LinearGradient>
  );
};

export default MobileTrackerPhoneContainer;
