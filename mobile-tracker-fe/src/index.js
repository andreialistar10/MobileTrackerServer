import React from "react";
import { render } from "react-dom";
import { BrowserRouter as Router } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import App from "./components/App";
import "./index.css";
import configureStore from "./redux/configureStore";
import { Provider as ReduxProvider } from "react-redux";
import * as stateLoader from "./redux/stateManagement";
import * as authorizationSelectors from "./redux/selectors/authorizationSelectors";
import * as deviceSelectors from "./redux/selectors/deviceSelectors";
import { NOT_ACTIVATED_ACCOUNT } from "./utils/auth/roles";

const store = configureStore(stateLoader.loadState());
store.subscribe(() => {
  const role = store.getState().authorization.role;
  if (role !== NOT_ACTIVATED_ACCOUNT) {
    stateLoader.saveState(store.getState());
  }
});

export const getApiToken = () => {
  return authorizationSelectors.getApiToken(store.getState());
};

export const getRefreshToken = () => {
  return authorizationSelectors.getRefreshToken(store.getState());
};

export const getDevicesLastUpdate = () => {
    return deviceSelectors.getDevicesLastUpdate(store.getState());
};

render(
  <ReduxProvider store={store}>
    <Router>
      <App />
    </Router>
  </ReduxProvider>,
  document.getElementById("app")
);
