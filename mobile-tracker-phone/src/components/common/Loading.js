import React from 'react';
import Modal from 'react-native-modal';
import {ActivityIndicator, StyleSheet} from 'react-native';

const Loading = ({isLoading}) => {
  return (
    <Modal isVisible={isLoading} style={styles.root}>
      <ActivityIndicator size="large" color={'rgb(33,128,27)'} />
    </Modal>
  );
};

const styles = StyleSheet.create({
  root: {
    backgroundColor: 'rgb(203,241,230)',
  },
});

export default Loading;
