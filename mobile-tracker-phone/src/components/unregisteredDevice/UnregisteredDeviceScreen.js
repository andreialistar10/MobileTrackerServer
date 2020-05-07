import React, {useState} from 'react';

import {
  Dimensions,
  Image,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
  TextInput,
} from 'react-native';
import LinearGradient from 'react-native-linear-gradient';
import Modal from 'react-native-modal';
import {HEADER_HEIGHT} from '../style';

const dimensions = Dimensions.get('window');
const imageHeight = 0.5 * Math.round((dimensions.height * 9) / 16);

const UnregisteredDeviceScreen = ({onRegisterDevice, deviceNameValidator}) => {
  const gif = require('../../assets/location_animation.gif');

  const [actualImageDimensions, setActualImageDimensions] = useState({
    width: dimensions.width,
    height: dimensions.height,
  });

  const [actualRootDimensions, setActualRootDimensions] = useState({
    width: dimensions.width,
    height: dimensions.height,
  });

  const [modalIsVisible, setModalIsVisible] = useState(false);
  const [deviceName, setDeviceName] = useState('');
  const [error, setError] = useState('');

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
    let {height} = actualRootDimensions;
    height -= HEADER_HEIGHT;
    return {...styles.container, minHeight: height};
  };

  const getStyleForModal = () => {
    let {height} = actualRootDimensions;
    height -= HEADER_HEIGHT - 30;
    if (height < 600) {
      height *= 0.8;
    } else if (height < 700) {
      height *= 0.6;
    } else {
      height *= 0.35;
    }
    return {...styles.modalContent, minHeight: height};
  };

  const handleRegisterDevice = () => {
    if (deviceNameValidator(deviceName)) {
      setModalIsVisible(false);
      onRegisterDevice(deviceName);
    } else {
      setError('Device name could not be blank!');
    }
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
          the '<Text style={{fontWeight: 'bold'}}>REGISTER DEVICE</Text>'
          button!
        </Text>
      </View>
      <View style={styles.buttonWrapper}>
        <TouchableOpacity
          style={styles.button}
          onPress={() => {
            setModalIsVisible(true);
            setDeviceName('');
            setError('');
          }}>
          <Text style={styles.buttonText}>REGISTER DEVICE</Text>
        </TouchableOpacity>
      </View>
      <Modal
        isVisible={modalIsVisible}
        onBackdropPress={() => setModalIsVisible(false)}>
        <View style={getStyleForModal()}>
          <Text style={styles.modalTitle}>Device Information</Text>
          <View style={styles.modalBottom}>
            <TextInput
              style={styles.textInput}
              value={deviceName}
              onChangeText={(text) => setDeviceName(text)}
              maxLength={50}
              placeholder="Please tap device name"
              placeholderTextColor="gray"
              autoCapitalize="none"
            />
            {error !== '' && <Text style={styles.modalError}>{error}</Text>}
            <View style={styles.modalButtonsContainer}>
              <TouchableOpacity
                style={[styles.modalButton, styles.cancelButton]}
                onPress={() => setModalIsVisible(false)}>
                <Text style={styles.modalText}>CANCEL</Text>
              </TouchableOpacity>
              <TouchableOpacity
                style={[styles.modalButton, styles.registerButton]}
                onPress={handleRegisterDevice}>
                <Text style={styles.modalText}>REGISTER</Text>
              </TouchableOpacity>
            </View>
          </View>
        </View>
      </Modal>
    </LinearGradient>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  imageWrapper: {
    marginTop: 25,
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
  modalContent: {
    backgroundColor: 'rgba(216,241,239, 0.9)',
    borderRadius: 8,
    overflow: 'hidden',
    flexDirection: 'column',
    alignItems: 'center',
    elevation: 10,
  },
  modalTitle: {
    fontWeight: 'bold',
    fontFamily: 'Rubik-Regular',
    fontSize: 21,
    marginTop: 30,
    marginBottom: 30,
  },
  modalBottom: {
    flex: 1,
    justifyContent: 'flex-end',
    alignItems: 'center',
  },
  modalButtonsContainer: {
    flexDirection: 'row',
    justifyContent: 'center',
    padding: 30,
  },
  modalButton: {
    width: 125,
    height: 50,
    borderRadius: 8,
    flexDirection: 'row',
    textAlign: 'center',
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
  modalText: {
    color: '#fff',
    fontFamily: 'Rubik-Regular',
    fontWeight: 'bold',
    fontSize: 18,
  },
  cancelButton: {
    backgroundColor: 'rgba(210,0,0,1)',
    marginRight: 40,
  },
  registerButton: {
    backgroundColor: 'rgba(0,185,0,1)',
  },
  textInput: {
    fontFamily: 'Rubik-Regular',
    fontSize: 18,
    borderBottomWidth: 1,
    borderColor: 'gray',
    width: 290,
  },

  modalError: {
    fontFamily: 'Rubik-Regular',
    fontSize: 14,
    fontWeight: 'bold',
    color: 'rgba(210,0,0,1)',
    width: 290,
    marginTop: 3,
  },
});

export default UnregisteredDeviceScreen;
