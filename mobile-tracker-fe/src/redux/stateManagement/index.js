const STORE_KEY = "MOBILE_TRACKER_REDUX_STORE";

export const loadState = () => {
  try {
    const serializedState = localStorage.getItem(STORE_KEY);
    if (!serializedState) {
      return defaultState;
    }
    return JSON.parse(serializedState);
  } catch (error) {
    return defaultState;
  }
};

export const saveState = (state) => {
  try {
    const serializedState = JSON.stringify(state);
    localStorage.setItem(STORE_KEY, serializedState);
  } catch (_) {
    //empty
  }
};

export const defaultState = {
  authorization: {
    jwt: null,
    refreshToken: null,
    username: null,
    role: null,
  },
  user: {},
};
