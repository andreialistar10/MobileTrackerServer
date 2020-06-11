import {StyleSheet} from 'react-native';

export const styles = StyleSheet.create({
  contentWrapper: {
    flex: 1,
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
  },
  content: {
    marginTop: 35,
    marginBottom: 35,
    backgroundColor: 'rgba(175,228,241, 1)',
    borderRadius: 10,
    width: '92%',
    minHeight: '45%',
    elevation: 12,
    overflow: 'hidden',
    alignItems: 'center',
    justifyContent: 'center',

    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 6,
    },
    shadowOpacity: 0.37,
    shadowRadius: 7.49,
  },
  map: {
    flex: 1,
    height: '100%',
    width: '100%',
    borderRadius: 10,
  },
  buttonText: {
    textAlign: 'center',
    fontFamily: 'Rubik-Regular',
    color: 'white',
    fontWeight: 'bold',
    fontSize: 18,
    textTransform: 'capitalize',
  },
  buttonWrapper: {
    width: '100%',
    marginTop: 40,
    marginBottom: 40,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
  },
  button: {
    borderRadius: 8,
    width: 190,
    // backgroundColor: '#4ddd4c',
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
  turnOnButton: {
    backgroundColor: 'rgb(5, 152, 230)',
  },
});
