import React from 'react';
import {View, TouchableOpacity, Text} from 'react-native';
import TransparentLoading from '../common/TransparentLoading';
import {styles} from '../style/pairedDevice';

import MobileTrackerPhoneContainer from '../common/MobileTrackerPhoneContainer';
import MapView from 'react-native-maps';

export const PairedDeviceScreen = ({navigation, location, loading}) => {
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
                latitude: location.latitude,
                longitude: location.longitude,
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
