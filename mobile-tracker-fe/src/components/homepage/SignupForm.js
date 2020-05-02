import React from "react";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import InputAdornment from "@material-ui/core/InputAdornment";
import { AccountCircle, Lock, Email, Person } from "@material-ui/icons";
import { makeButtonStyle, makeTextFieldStyle } from "../../style/homepage";
import Grid from "@material-ui/core/Grid";
import PropTypes from "prop-types";
import "./HomePageForms.css";
import LinearProgress from "@material-ui/core/LinearProgress";
import { makeProgressStyle } from "../../style/homepage/linearProgress";

const SignupForm = ({
  data,
  onSubmit,
  onTextChange,
  errors,
  focus,
  onFocusChange,
  selectedTextFields,
  loading,
}) => {
  const {
    username,
    password,
    repeatPassword,
    firstName,
    lastName,
    email,
  } = data;
  const textFieldStyle = makeTextFieldStyle();
  const { submitButton } = makeButtonStyle();
  const { linearProgress } = makeProgressStyle();
  const showErrorMessage = (textFieldId) => {
    return (
      errors[textFieldId] !== undefined &&
      selectedTextFields[textFieldId] !== undefined
    );
  };

  return (
    <form onSubmit={onSubmit} className="homepage-form">
      <Grid container direction="column" justify="center" alignItems="center">
        <TextField
          id="username"
          label="Username"
          value={username}
          autoComplete="off"
          onChange={(event) =>
            onTextChange({ ...data, username: event.target.value })
          }
          onFocus={onFocusChange}
          error={showErrorMessage("username")}
          helperText={showErrorMessage("username") ? errors.username : ""}
          required
          className={textFieldStyle.textField}
          InputProps={{
            required: false,
            className: textFieldStyle.input,
            startAdornment: (
              <InputAdornment position="start">
                <AccountCircle />
              </InputAdornment>
            ),
          }}
          InputLabelProps={{
            classes: {
              root: textFieldStyle.label,
              error: textFieldStyle.erroredLabel,
            },
          }}
        />
        <TextField
          id="password"
          label="Password"
          type="password"
          value={password}
          autoComplete="off"
          onChange={(event) =>
            onTextChange({ ...data, password: event.target.value })
          }
          onFocus={onFocusChange}
          error={showErrorMessage("password")}
          helperText={showErrorMessage("password") ? errors.password : ""}
          className={textFieldStyle.textField}
          required
          InputProps={{
            required: false,
            className: textFieldStyle.input,
            startAdornment: (
              <InputAdornment position="start">
                <Lock />
              </InputAdornment>
            ),
          }}
          InputLabelProps={{
            classes: {
              root: textFieldStyle.label,
              error: textFieldStyle.erroredLabel,
            },
          }}
        />
        <TextField
          id="repeatPassword"
          label="Repeat Password"
          type="password"
          value={repeatPassword}
          autoComplete="off"
          onChange={(event) =>
            onTextChange({ ...data, repeatPassword: event.target.value })
          }
          onFocus={onFocusChange}
          error={showErrorMessage("repeatPassword")}
          helperText={
            showErrorMessage("repeatPassword") ? errors.repeatPassword : ""
          }
          className={textFieldStyle.textField}
          required
          InputProps={{
            required: false,
            className: textFieldStyle.input,
            startAdornment: (
              <InputAdornment position="start">
                <Lock />
              </InputAdornment>
            ),
          }}
          InputLabelProps={{
            classes: {
              root: textFieldStyle.label,
              error: textFieldStyle.erroredLabel,
            },
          }}
        />
        <TextField
          id="firstName"
          label="First Name"
          type="text"
          value={firstName}
          autoComplete="off"
          onChange={(event) =>
            onTextChange({ ...data, firstName: event.target.value })
          }
          onFocus={onFocusChange}
          error={showErrorMessage("firstName")}
          helperText={showErrorMessage("firstName") ? errors.firstName : ""}
          className={textFieldStyle.textField}
          required
          InputProps={{
            required: false,
            className: textFieldStyle.input,
            startAdornment: (
              <Person position="start">
                <Lock />
              </Person>
            ),
          }}
          InputLabelProps={{
            classes: {
              root: textFieldStyle.label,
              error: textFieldStyle.erroredLabel,
            },
          }}
        />
        <TextField
          id="lastName"
          label="Last Name"
          type="text"
          value={lastName}
          autoComplete="off"
          onChange={(event) =>
            onTextChange({ ...data, lastName: event.target.value })
          }
          onFocus={onFocusChange}
          error={showErrorMessage("lastName")}
          helperText={showErrorMessage("lastName") ? errors.lastName : ""}
          className={textFieldStyle.textField}
          required
          InputProps={{
            required: false,
            className: textFieldStyle.input,
            startAdornment: (
              <Person position="start">
                <Lock />
              </Person>
            ),
          }}
          InputLabelProps={{
            classes: {
              root: textFieldStyle.label,
              error: textFieldStyle.erroredLabel,
            },
          }}
        />
        <TextField
          id="email"
          label="E-mail"
          type="email"
          value={email}
          autoComplete="off"
          onChange={(event) =>
            onTextChange({ ...data, email: event.target.value })
          }
          onFocus={onFocusChange}
          error={showErrorMessage("email")}
          helperText={showErrorMessage("email") ? errors.email : ""}
          className={textFieldStyle.textField}
          required
          InputProps={{
            required: false,
            className: textFieldStyle.input,
            startAdornment: (
              <Email position="start">
                <Lock />
              </Email>
            ),
          }}
          InputLabelProps={{
            classes: {
              root: textFieldStyle.label,
              error: textFieldStyle.erroredLabel,
            },
          }}
        />
        {loading && <LinearProgress className={linearProgress} />}
        <Button className={submitButton} type="submit" disabled={loading}>
          {loading ? "Sign up..." : "Sign up"}
        </Button>
      </Grid>
    </form>
  );
};

SignupForm.propTypes = {
  onSubmit: PropTypes.func.isRequired,
  onTextChange: PropTypes.func.isRequired,
  onFocusChange: PropTypes.func.isRequired,
  focus: PropTypes.string.isRequired,
  loading: PropTypes.bool.isRequired,
  data: PropTypes.exact({
    username: PropTypes.string.isRequired,
    password: PropTypes.string.isRequired,
    repeatPassword: PropTypes.string.isRequired,
    lastName: PropTypes.string.isRequired,
    firstName: PropTypes.string.isRequired,
    email: PropTypes.string.isRequired,
  }).isRequired,
  errors: PropTypes.exact({
    username: PropTypes.string,
    password: PropTypes.string,
    repeatPassword: PropTypes.string,
    lastName: PropTypes.string,
    firstName: PropTypes.string,
    email: PropTypes.string,
  }).isRequired,
  selectedTextFields: PropTypes.exact({
    username: PropTypes.bool,
    password: PropTypes.bool,
    repeatPassword: PropTypes.bool,
    lastName: PropTypes.bool,
    firstName: PropTypes.bool,
    email: PropTypes.bool,
  }).isRequired,
};

export default SignupForm;
