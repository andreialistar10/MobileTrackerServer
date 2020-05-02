import React, { useState } from "react";
import TextField from "@material-ui/core/TextField";
import PropTypes from "prop-types";
import Button from "@material-ui/core/Button";
import InputAdornment from "@material-ui/core/InputAdornment";
import { AccountCircle, Lock } from "@material-ui/icons";
import { makeButtonStyle, makeTextFieldStyle } from "../../style/homepage";
import Grid from "@material-ui/core/Grid";
import "./LoginForm.css";
import { MobileTrackerAlert } from "../common/MobileTrackerAlert";
import LinearProgress from "@material-ui/core/LinearProgress";
import { makeProgressStyle } from "../../style/homepage/linearProgress";

const LoginForm = ({ onSubmit, data, onTextChange, error, loading }) => {
  const textFieldStyle = makeTextFieldStyle();
  const { submitButton } = makeButtonStyle();
  const { linearProgress } = makeProgressStyle();
  const { username, password } = data;
  const [open, setOpen] = useState(true);
  const handleSubmit = (event) => {
    setOpen(true);
    onSubmit(event);
  };
  return (
    <form onSubmit={handleSubmit} className="homepage-form">
      <Grid container direction="column" justify="center" alignItems="center">
        {error && open && (
          <MobileTrackerAlert
            severity="error"
            className="message-alert"
            onClose={() => setOpen(false)}
          >
            <h1 className="message-alert-header">Login error</h1>
            {error}
          </MobileTrackerAlert>
        )}
        <TextField
          label="Username"
          value={username}
          onChange={(event) => {
            onTextChange({ ...data, username: event.target.value });
          }}
          placeholder="username"
          error={error !== ""}
          className={textFieldStyle.textField}
          required
          InputProps={{
            required: false,
            readOnly: loading,
            className: textFieldStyle.input,
            startAdornment: (
              <InputAdornment position="start">
                <AccountCircle />
              </InputAdornment>
            ),
          }}
          FormHelperTextProps={{
            className: textFieldStyle.helperText,
          }}
          InputLabelProps={{
            classes: {
              root: textFieldStyle.label,
              error: textFieldStyle.erroredLabel,
            },
          }}
        />
        <TextField
          label="Password"
          type="password"
          value={password}
          onChange={(event) => {
            onTextChange({ ...data, password: event.target.value });
          }}
          placeholder="password"
          error={error !== ""}
          className={textFieldStyle.textField}
          required
          InputProps={{
            required: false,
            readOnly: loading,
            className: textFieldStyle.input,
            startAdornment: (
              <InputAdornment position="start">
                <Lock />
              </InputAdornment>
            ),
          }}
          FormHelperTextProps={{
            className: textFieldStyle.helperText,
          }}
          InputLabelProps={{
            classes: {
              root: textFieldStyle.label,
              error: textFieldStyle.erroredLabel,
            },
          }}
        />
        {loading && <LinearProgress className={linearProgress} />}
        <Button type="submit" className={submitButton} disabled={loading}>
          {loading ? "Sign in..." : "Sign in"}
        </Button>
      </Grid>
    </form>
  );
};

LoginForm.propTypes = {
  onSubmit: PropTypes.func.isRequired,
  onTextChange: PropTypes.func.isRequired,
  data: PropTypes.exact({
    username: PropTypes.string.isRequired,
    password: PropTypes.string.isRequired,
  }).isRequired,
  error: PropTypes.string.isRequired,
  loading: PropTypes.bool.isRequired,
};

export default LoginForm;
