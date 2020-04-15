import React from "react";
import { Route, Switch } from "react-router-dom";
import PageNotFound from "./PageNotFound";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import LoginPage from "./login/LoginPage";

function App() {
  return (
    <>
      <Switch>
        <Route exact path="/" component={LoginPage} />
        {/*<Route path="/about" component={} />*/}
        <Route component={PageNotFound} />
      </Switch>
      <ToastContainer autoCloase={3000} hideProgressBar />
    </>
  );
}

export default App;
