import React from "react";
import { Route, Switch, BrowserRouter } from "react-router-dom";
import PageNotFound from "./not-found/PageNotFound";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import HomePage from "./homepage/HomePage";
import AuthLoading from "./auth/AuthLoading";
import NotActivatedAccount from "./account/not-activated-account/NotActivatedAccount";
import Account from "./account/activated-account/ActivatedAccount";
import PrivateRoute from "./auth/PrivateRoute";

function App() {
  return (
    <>
      <BrowserRouter>
        <Switch>
          <Route exact path="/" component={AuthLoading} />
          <Route path="/sign-in" component={HomePage} />
          <Route path="/sign-up" component={HomePage} />
          <PrivateRoute path="/account/:page" component={Account} />
          <PrivateRoute path="/account" component={Account} />
          <Route
            path="/not-activated-account"
            component={NotActivatedAccount}
          />
          <Route component={PageNotFound} />
        </Switch>
      </BrowserRouter>
      <ToastContainer autoCloase={3000} />
    </>
  );
}

export default App;
