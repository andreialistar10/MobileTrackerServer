import React, {useContext, useState} from 'react';
import {
  Button,
  Dimensions,
  SafeAreaView,
  ScrollView,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';
import {MobileTrackerPhoneContext} from '../../context';
import MobileTrackerPhoneHeader from '../common/MobileTrackerPhoneHeader';
import LinearGradient from 'react-native-linear-gradient';
import {styles} from '../style/registerDevice';
import {HEADER_HEIGHT} from '../style';

const dimensions = Dimensions.get('window');
const DeviceInformationScreen = ({navigation}) => {
  const [actualRootDimensions, setActualRootDimensions] = useState({
    width: dimensions.width,
    height: dimensions.height,
  });
  const {deviceInformation} = useContext(MobileTrackerPhoneContext);
  const getStyleForRoot = () => {
    let {height} = actualRootDimensions;
    height -= HEADER_HEIGHT;
    return {...styles.container, minHeight: height};
  };
  const resize = () => {
    const {width, height} = Dimensions.get('window');
    setActualRootDimensions({width, height});
  };
  const textStyle = {
    ...styles.text,
    color: 'rgba(210,0,0,1)',
    fontSize: 14,
    fontWeight: 'bold',
  };
  return (
    <SafeAreaView>
      <ScrollView style={styleScrollView.style}>
        <MobileTrackerPhoneHeader />
        <LinearGradient
          style={getStyleForRoot()}
          colors={['rgba(207, 241, 238,0.4)', 'rgba(175,228,241, 1)']}
          start={{x: 0, y: 0}}
          end={{x: 1, y: 0}}
          onLayout={resize}>
          <View style={styles.contentWrapper}>
            <View style={styles.content}>
              <Text style={{...styles.title, textAlign: 'center'}}>
                Device Information
              </Text>
              <Text style={[styles.text, styles.deviceInformationSubtitle]}>
                Device ID:
              </Text>
              <Text style={textStyle}>{deviceInformation.id}</Text>
              <Text style={[styles.text, styles.deviceInformationSubtitle]}>
                Device Name:
              </Text>
              <Text style={textStyle}>{deviceInformation.name}</Text>
              <Text style={[styles.text, styles.deviceInformationSubtitle]}>
                Password:
              </Text>
              <Text style={textStyle}>parola mea</Text>
              <View style={styles.buttonWrapper}>
                <TouchableOpacity
                  style={[styles.button, styles.turnOnButton]}
                  onPress={() => {
                    navigation.goBack();
                  }}>
                  <Text style={styles.buttonText}>GO BACK</Text>
                </TouchableOpacity>
              </View>
            </View>
          </View>
        </LinearGradient>
      </ScrollView>
    </SafeAreaView>
  );
};

const styleScrollView = StyleSheet.create({
  style: {
    height: '100%',
    width: '100%',
  },
});

export default DeviceInformationScreen;
