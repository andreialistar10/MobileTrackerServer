import React, {useEffect, useState} from 'react';
import {SafeAreaView, ScrollView, StyleSheet} from 'react-native';
import MobileTrackerPhoneHeader from '../common/MobileTrackerPhoneHeader';
import PairedDeviceScreen from './PairedDeviceScreen';
import {requestLocationPermission} from '../../core/intents';
import Geolocation from 'react-native-geolocation-service';

const PairedDeviceContainer = ({navigation}) => {
  const [loading, setLoading] = useState(true);
  const [location, setLocation] = useState({latitude: 46, longitude: 23});

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
            setLocation(currentCoords);
          },
          error => {
            console.log(error);
          },
          {enableHighAccuracy: true, timeout: 15000, maximumAge: 1000},
        );
      });
      setLoading(false);
    }
  }, [loading]);
  return (
    <SafeAreaView>
      <ScrollView style={styles.scrollView}>
        <MobileTrackerPhoneHeader />
        <PairedDeviceScreen
          navigation={navigation}
          location={location}
          loading={loading}
        />
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  scrollView: {
    height: '100%',
    width: '100%',
  },
});

export default PairedDeviceContainer;
