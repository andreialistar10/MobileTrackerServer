import React, {useState, useEffect} from 'react';
import {
  View,
  PermissionsAndroid,
  TouchableOpacity,
  Text,
} from 'react-native';
import TransparentLoading from '../common/TransparentLoading';
import {styles} from '../style/pairedDevice';

import MobileTrackerPhoneContainer from '../common/MobileTrackerPhoneContainer';
import MapView from 'react-native-maps';
import Geolocation from 'react-native-geolocation-service';

export const PairedDeviceScreen = ({navigation}) => {
  const [loading, setLoading] = useState(true);
  const [coords, setCoords] = useState({latitude: 46, longitude: 23});

  const requestLocationPermission = React.useCallback(async () => {
    const granted = await PermissionsAndroid.request(
      PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,
      {
        title: 'Mobile-Tracker',
        message: 'Mobile-Tracker App needs access to your location ',
      },
    );
    return granted === PermissionsAndroid.RESULTS.GRANTED;
  }, []);
  useEffect(() => {
    if (loading === true) {
      requestLocationPermission().then(() => {
        Geolocation.getCurrentPosition(
          ({coords: coordinates}) => {
            const currentCoords = {
              latitude: coordinates.latitude,
              longitude: coordinates.longitude,
            };
            console.log(currentCoords);
            setCoords(currentCoords);
          },
          (error) => {
            console.log(error);
          },
          {enableHighAccuracy: true, timeout: 15000, maximumAge: 1000},
        );
      });
      setLoading(false);
    }
  }, [loading, requestLocationPermission]);

  return (
    <MobileTrackerPhoneContainer>
      <TransparentLoading loading={loading} />
      <View style={styles.contentWrapper}>
        <View style={styles.content}>
          {!loading && (
            <MapView
              style={styles.map}
              initialRegion={{
                latitude: 40,
                longitude: 20,
                latitudeDelta: 0.04,
                longitudeDelta: 0.09,
              }}
              region={{
                latitude: coords.latitude,
                longitude: coords.longitude,
                latitudeDelta: 0.004,
                longitudeDelta: 0.009,
              }}
              showsUserLocation={true}
            />
          )}
        </View>
        <View style={styles.buttonWrapper}>
          <TouchableOpacity
            style={[styles.button, styles.turnOnButton]}
            onPress={() => {
              navigation.navigate('DeviceInformation');
            }}>
            <Text style={styles.buttonText}>See device details</Text>
          </TouchableOpacity>
        </View>
      </View>
    </MobileTrackerPhoneContainer>
  );
};

export default PairedDeviceScreen;
