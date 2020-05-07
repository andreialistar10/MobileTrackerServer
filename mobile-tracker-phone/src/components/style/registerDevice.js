import {StyleSheet} from 'react-native';

export const styles = StyleSheet.create({
  root: {
    flex: 1,
    padding: 0,
    margin: 0,
  },
  contentWrapper: {
    flex: 1,
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
  },
  content: {
    marginTop: 35,
    marginBottom: 35,
    paddingLeft: 15,
    paddingTop: 15,
    paddingRight: 15,
    backgroundColor: 'rgba(175,228,241, 1)',
    borderRadius: 6,
    width: '90%',
    minHeight: '40%',
    elevation: 12,

    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 6,
    },
    shadowOpacity: 0.37,
    shadowRadius: 7.49,
  },
  titleWrapper: {
    marginBottom: 15,
  },
  title: {
    fontFamily: 'Rubik-Regular',
    // color: 'rgba(210,0,0,1)',
    color: 'rgba(15, 10, 190, 1)',
    fontWeight: 'bold',
    fontSize: 20,
  },
  text: {
    fontFamily: 'Rubik-Regular',
    color: 'rgb(5, 152, 230)',
    fontSize: 18,
    textAlign: 'justify',
    fontWeight: '600',
  },
  nonTerminalParagraph: {
    marginBottom: 15,
  },
  button: {
    borderRadius: 8,
    width: 170,
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
  turnOffButton: {
    backgroundColor: 'rgba(210,0,0,1)',
  },
  buttonText: {
    textAlign: 'center',
    fontFamily: 'Rubik-Regular',
    color: 'white',
    fontWeight: 'bold',
    fontSize: 18,
  },
  buttonWrapper: {
    width: '100%',
    marginTop: 40,
    marginBottom: 40,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
  },
  deviceInformationSubtitle: {
    textDecorationLine: 'underline',
    marginTop: 25,
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 4,
  },
});
