import React from "react";
import PropTypes from "prop-types";
import { makeTitlePageStyle } from "../../../../style/activated-account/titlePage";

const TitlePage = ({ title }) => {
  const style = makeTitlePageStyle();
  const { root, titleText } = style;
  return (
    <h2 className={root}>
      <span className={titleText}>{title}</span>
    </h2>
  );
};

TitlePage.propTypes = {
  title: PropTypes.string.isRequired,
};

export default TitlePage;
