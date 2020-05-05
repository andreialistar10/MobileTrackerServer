import React, {useContext, useEffect} from 'react';
import Loading from './common/Loading';
import {MobileTrackerPhoneContext} from '../context';

const AuthLoading = ({navigation}) => {
  const {initStore} = useContext(MobileTrackerPhoneContext);
  useEffect(() => {
    initStore().then(({deviceInformation}) => {
      navigation.navigate(
        deviceInformation.id !== null
          ? 'RegisteredDevice'
          : 'UnregisteredDevice',
      );
    });
  }, []);

  return <Loading isLoading={true} />;
};

export default AuthLoading;
