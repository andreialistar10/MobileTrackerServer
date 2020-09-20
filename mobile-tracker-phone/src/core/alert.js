import {AlertIOS, Platform, ToastAndroid} from 'react-native';

export const notifyError = (errorMessage) => {
  if (Platform.OS === 'android') {
    ToastAndroid.showWithGravityAndOffset(
      errorMessage,
      ToastAndroid.LONG,
      ToastAndroid.TOP,
      25,
      50,
    );
  } else {
    AlertIOS.alert(errorMessage);
  }
};
