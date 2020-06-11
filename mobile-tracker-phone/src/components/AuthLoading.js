import React, {useContext, useEffect} from 'react';
import Loading from './common/Loading';
import {MobileTrackerPhoneContext} from '../context';
// import {clearLocalStorage} from '../core/localStorage';

const AuthLoading = ({navigation}) => {
  const {initStore} = useContext(MobileTrackerPhoneContext);
  // clearLocalStorage();
  useEffect(() => {
    initStore().then(({deviceInformation, authorization}) => {
      const {refreshToken} = authorization;
      if (refreshToken !== null) {
        navigation.navigate('PairedDevice');
      } else {
        navigation.navigate(
          deviceInformation.id !== null
            ? 'RegisteredDevice'
            : 'UnregisteredDevice',
        );
      }
    });
  }, [initStore, navigation]);

  return <Loading isLoading={true} />;
};

export default AuthLoading;
