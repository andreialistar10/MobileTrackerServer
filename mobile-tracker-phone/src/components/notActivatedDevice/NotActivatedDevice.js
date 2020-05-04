import React, {useState} from 'react';

import {
  Dimensions,
  Image,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';
import LinearGradient from 'react-native-linear-gradient';

const dimensions = Dimensions.get('window');
const imageHeight = 0.5 * Math.round((dimensions.height * 9) / 16);

const NotActivatedDevice = () => {
  const gif = require('../../assets/location_animation.gif');
  const [actualImageDimensions, setActualImageDimensions] = useState({
    width: dimensions.width,
    height: dimensions.height,
  });
  const [actualRootDimensions, setActualRootDimensions] = useState({
    width: dimensions.width,
    height: dimensions.height,
  });

  const resizeImage = (event) => {
    const dims = Dimensions.get('window');
    const width = dims.width;
    const height =
      0.5 *
      ((width * event.nativeEvent.layout.height) /
        event.nativeEvent.layout.width);
    setActualImageDimensions({width, height});
    setActualRootDimensions({width, height: dims.height});
  };

  const getStyleForImage = () => {
    let {width, height} = actualImageDimensions;
    width = width - 30;
    return {...styles.imageWrapper, width, height};
  };

  const getStyleForRoot = () => {
    let {width, height} = actualRootDimensions;
    height -= 110;
    return {...styles.container, minHeight: height};
  };
  return (
    <LinearGradient
      style={getStyleForRoot()}
      colors={['rgba(207, 241, 238,0.4)', 'rgba(175,228,241, 1)']}
      start={{x: 0, y: 0}}
      end={{x: 1, y: 0}}
      onLayout={resizeImage}>
      <View style={getStyleForImage()}>
        <Image source={gif} style={styles.image} />
      </View>
      <View style={styles.textWrapper}>
        <Text style={styles.title}>Your device is not registered!</Text>
        <Text style={styles.text}>
          You cannot use our features until you register your device by clicking
          '<Text style={{fontWeight: 'bold'}}>REGISTER DEVICE</Text>' button!
        </Text>
      </View>
      <View style={styles.buttonWrapper}>
        <TouchableOpacity style={styles.button}>
          <Text style={styles.buttonText}>REGISTER DEVICE</Text>
        </TouchableOpacity>
      </View>
    </LinearGradient>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  imageWrapper: {
    marginTop: 15,
    marginLeft: 15,
    marginRight: 15,
    overflow: 'hidden',
    width: dimensions.width - 30,
    height: imageHeight,
    borderRadius: 7,
    elevation: 12,

    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 6,
    },
    shadowOpacity: 0.37,
    shadowRadius: 7.49,
  },
  image: {
    width: '100%',
    height: '100%',
  },
  textWrapper: {
    backgroundColor: 'rgba(175,228,241, 1)',
    marginTop: 40,
    marginLeft: 20,
    marginRight: 20,
    padding: 15,
    borderRadius: 6,
    elevation: 12,

    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 6,
    },
    shadowOpacity: 0.37,
    shadowRadius: 7.49,
  },
  title: {
    fontFamily: 'Rubik-Regular',
    color: 'rgba(210,0,0,1)',
    fontWeight: 'bold',
    fontSize: 20,
    marginBottom: 15,
  },
  text: {
    fontFamily: 'Rubik-Regular',
    color: 'rgb(200,80,80)',
    fontSize: 18,
    textAlign: 'justify',
    fontWeight: '600',
  },
  buttonWrapper: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'center',
    alignContent: 'center',
  },
  button: {
    borderRadius: 8,
    width: 200,
    marginTop: 40,
    marginBottom: 40,
    backgroundColor: '#4ddd4c',
    textAlign: 'center',
    height: 55,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',

    elevation: 10,
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 5,
    },
    shadowOpacity: 0.34,
    shadowRadius: 6.27,
  },
  buttonText: {
    textAlign: 'center',
    fontFamily: 'Rubik-Regular',
    color: 'white',
    fontWeight: 'bold',
    fontSize: 18,
  },
});

export default NotActivatedDevice;
