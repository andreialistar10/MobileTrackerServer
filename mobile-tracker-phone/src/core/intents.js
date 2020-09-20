import {PermissionsAndroid} from 'react-native';

export const requestLocationPermission = async () => {
  const granted = await PermissionsAndroid.request(
    PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,
    {
      title: 'Mobile-Tracker',
      message: 'Mobile-Tracker App needs access to your location!',
    },
  );
  return granted === PermissionsAndroid.RESULTS.GRANTED;
};
