import React, {useState} from 'react';
import {Dimensions, Text, View, TouchableOpacity} from 'react-native';
import LinearGradient from 'react-native-linear-gradient';
import {HEADER_HEIGHT} from '../style';
import {styles} from '../style/registerDevice';
import TransparentLoading from '../common/TransparentLoading';

const dimensions = Dimensions.get('window');

const ContentOnNonPairing = ({onPress}) => {
  return (
    <>
      <View style={styles.titleWrapper}>
        <Text style={styles.title}>Your device is not paired!</Text>
      </View>
      <Text style={[styles.text, styles.nonTerminalParagraph]}>
        Your device cannot be tracked until you pair it with a parent account!
      </Text>
      <Text style={styles.text}>
        Touch on the '<Text style={{fontWeight: 'bold'}}>TURN ON</Text>' button
        to make your phone visible, then swipe left to see your device
        credentials required for pairing.
      </Text>
      <View style={styles.buttonWrapper}>
        <TouchableOpacity
          style={[styles.button, styles.turnOnButton]}
          onPress={onPress}>
          <Text style={styles.buttonText}>TURN ON</Text>
        </TouchableOpacity>
      </View>
    </>
  );
};

const ContentOnPairing = ({onPress}) => {
  return (
    <>
      <View style={styles.titleWrapper}>
        <Text style={styles.title}>Your device is visible!</Text>
      </View>
      <Text style={[styles.text, styles.nonTerminalParagraph]}>
        Your device is visible and can be paired using a parent account.Please
        make sure the app is running in foreground when try to pairing your
        device!
      </Text>
      <Text
        style={{
          ...styles.text,
          ...styles.nonTerminalParagraph,
          fontWeight: 'bold',
        }}>
        Swipe left to see your device credentials required for pairing.
      </Text>
      <Text style={styles.text}>
        If you want the phone to no longer be visible for pairing, please touch
        the '<Text style={{fontWeight: 'bold'}}>TURN OFF</Text>' button.
      </Text>
      <View style={styles.buttonWrapper}>
        <TouchableOpacity
          style={[styles.button, styles.turnOffButton]}
          onPress={onPress}>
          <Text style={styles.buttonText}>TURN OFF</Text>
        </TouchableOpacity>
      </View>
    </>
  );
};

const RegisteredDeviceScreen = ({
  onTurnOnPress,
  onTurnOffPress,
  pairing,
  loading,
}) => {
  const [actualRootDimensions, setActualRootDimensions] = useState({
    width: dimensions.width,
    height: dimensions.height,
  });
  const getStyleForRoot = () => {
    let {height} = actualRootDimensions;
    height -= HEADER_HEIGHT;
    return {...styles.container, minHeight: height};
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
      <TransparentLoading loading={loading} />
      <View style={styles.contentWrapper}>
        <View style={styles.content}>
          {pairing ? (
            <ContentOnPairing onPress={onTurnOffPress} />
          ) : (
            <ContentOnNonPairing onPress={onTurnOnPress} />
          )}
        </View>
      </View>
    </LinearGradient>
  );
};

export default RegisteredDeviceScreen;
