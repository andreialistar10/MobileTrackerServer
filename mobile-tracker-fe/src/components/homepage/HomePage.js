import React, { useState } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import "./HomePage.css";
import LoginForm from "./LoginForm";
import SingupForm from "./SingupForm";
import { Redirect, Route } from "react-router-dom";
import NavigationHeader from "./NavigationHeader";
import { stringIsBlank, validateUser } from "../../utils/validation";
import { login } from "../../redux/actions/authorizationActions";
import { signUp } from "../../redux/actions/userActions";
import { toast } from "react-toastify";

const initialLoginData = {
  username: "",
  password: "",
};

const initialSignUpData = {
  username: "",
  password: "",
  repeatPassword: "",
  firstName: "",
  lastName: "",
  email: "",
};

const selectAllTextFields = {
  username: true,
  password: true,
  repeatPassword: true,
  firstName: true,
  lastName: true,
  email: true,
};

const HomePage = ({ history, selectedPage, login, signUp, loggedIn }) => {
  const [loginData, setLoginData] = useState(initialLoginData);
  const [loginError, setLoginError] = useState("");
  const [loginIsLoading, setLoginIsLoading] = useState(false);
  const [signUpData, setSignUpData] = useState(initialSignUpData);
  const [signUpErrors, setSignUpErrors] = useState(
    validateUser(initialSignUpData)
  );
  const [signUpIsLoading, setSignUpIsLoading] = useState(false);
  const [focus, setFocus] = useState("");
  const [selectedTextFields, setSelectedTextFields] = useState({});

  const handleChangeMenu = (clickedButtonId) => {
    if (selectedPage === clickedButtonId) {
      return;
    }
    history.push(`/${clickedButtonId}`);
  };

  const handleLoginDataChange = (data) => {
    setLoginData(data);
  };

  const loginFormIsValid = () => {
    let error = "";
    if (
      stringIsBlank(loginData.username) ||
      stringIsBlank(loginData.password)
    ) {
      error = "Invalid username or password! Please try again!";
    }
    setLoginError(error);
    return error === "";
  };

  const handleLogin = (event) => {
    event.preventDefault();
    if (!loginFormIsValid()) {
      return;
    }
    setLoginIsLoading(true);
    login(loginData)
      .then(() => {
        history.push("/");
      })
      .catch((error) => {
        setLoginIsLoading(false);
        setLoginError(error.message);
        setLoginData((prevState) => {
          return { ...prevState, password: "" };
        });
      });
  };

  const handleSignUpDataChange = (data) => {
    setSignUpData(data);
    setSignUpErrors(validateUser(data));
  };

  const handleSignUpOnFocusChange = (event) => {
    const textFieldId = event.target.id;
    setFocus(textFieldId);
    if (setSelectedTextFields[textFieldId] === undefined) {
      setSelectedTextFields((prevState) => {
        const currentState = { ...prevState };
        currentState[textFieldId] = true;
        return currentState;
      });
    }
  };

  const signUpFormIsValid = () => {
    const errors = validateUser(signUpData);
    setSignUpErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleSignUp = (event) => {
    event.preventDefault();
    setSelectedTextFields({ ...selectAllTextFields });
    if (!signUpFormIsValid()) {
      return;
    }
    setSignUpIsLoading(true);
    signUp(signUpData)
      .then(() => {
        toast.success(
          "Your account was created! A confirmation email has been sent to your e-mail address!"
        );
        setSignUpData({ ...initialSignUpData });
        setLoginData({ ...initialLoginData });
        history.push("/sign-in");
      })
      .catch((error) => {
        if (error.username) {
          setSignUpErrors({ ...signUpErrors, username: error.username });
        } else {
          toast.error(error.message, { autoClose: false });
        }
      })
      .finally(() => {
        setSignUpIsLoading(false);
      });
  };
  return loggedIn ? (
    <Redirect to="/" />
  ) : (
    <div className="wrapper">
      <div className="bg" />
      <div className="container right-side">
        <div className="right-side-overflow">
          <NavigationHeader
            selectedPage={selectedPage}
            onMenuChanged={handleChangeMenu}
          />
          <div className="row">
            <Route
              path="/sign-in"
              render={(props) => (
                <LoginForm
                  {...props}
                  onSubmit={handleLogin}
                  error={loginError}
                  onTextChange={handleLoginDataChange}
                  data={loginData}
                  loading={loginIsLoading}
                />
              )}
            />
            <Route
              path="/sign-up"
              render={(props) => (
                <SingupForm
                  {...props}
                  focus={focus}
                  onFocusChange={handleSignUpOnFocusChange}
                  selectedTextFields={selectedTextFields}
                  onSubmit={handleSignUp}
                  onTextChange={handleSignUpDataChange}
                  errors={signUpErrors}
                  data={signUpData}
                  loading={signUpIsLoading}
                />
              )}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

HomePage.propTypes = {
  history: PropTypes.object.isRequired,
  selectedPage: PropTypes.string.isRequired,
  login: PropTypes.func.isRequired,
  signUp: PropTypes.func.isRequired,
  loggedIn: PropTypes.bool.isRequired,
};

const mapStateToProps = ({ authorization }, ownProps) => {
  const { role, jwt, refreshToken } = authorization;
  return {
    selectedPage: ownProps.match.path.replace("/", ""),
    loggedIn:
      (role !== null && jwt !== null) ||
      (role !== null && refreshToken !== null),
  };
};

const mapDispatchToProps = {
  login,
  signUp,
};

export default connect(mapStateToProps, mapDispatchToProps)(HomePage);
