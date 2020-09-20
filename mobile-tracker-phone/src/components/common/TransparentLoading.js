import React from 'react';
import {StyleSheet, View, Modal, ActivityIndicator} from 'react-native';

const TransparentLoading = (props) => {
  const {loading, ...attributes} = props;

  return (
    <Modal
      transparent={true}
      animationType={'none'}
      visible={loading}
      {...attributes}
      onRequestClose={() => {
        console.log('close modal');
      }}>
      <View style={styles.modalBackground}>
        <ActivityIndicator
          animating={loading}
          size="large"
          color="rgb(112,128,144)"
          style={styles.activityIndication}
        />
      </View>
    </Modal>
  );
};

const styles = StyleSheet.create({
  modalBackground: {
    flex: 1,
    alignItems: 'center',
    flexDirection: 'column',
    justifyContent: 'space-around',
    backgroundColor: 'rgba(0, 0, 0, 0.75)',
  },
  activityIndication: {
    transform: [{scale: 2}],
  },
});

export default TransparentLoading;
