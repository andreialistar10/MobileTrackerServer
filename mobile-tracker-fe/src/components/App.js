import React from "react";
import { Route, Switch, BrowserRouter } from "react-router-dom";
import PageNotFound from "./PageNotFound";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import HomePage from "./homepage/HomePage";
import AuthLoading from "./auth/AuthLoading";
import NotActivatedAccount from "./dashboard/NotActivatedAccount";
import Account from "./dashboard/Account";
import PrivateRoute from "./auth/PrivateRoute";

function App() {
  return (
    <>
      <BrowserRouter>
        <Switch>
          <Route exact path="/" component={AuthLoading} />
          <Route path="/sign-in" component={HomePage} />
          <Route path="/sign-up" component={HomePage} />
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
