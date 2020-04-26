import React from "react";
import "./PageNotFound.css";
import { Link } from "react-router-dom";
const PageNotFound = () => {
  return (
    <div className="not-found-page-container">
      <div className="not-found-page-content">
        <h1 className="header-404">
          4<span className="zero-span">0</span>4
        </h1>
        <h2 className="second-header">
          Sorry! The page you were looking for could not be found!
        </h2>
        <p className="message-text-404">
          You have been tricked into click on a link that can not be found.
          Please check de URL or go to{" "}
          <Link to="/" className="anchor-404">
            Main Page
          </Link>{" "}
          and see if you can locate what you are looking for.
        </p>
      </div>
    </div>
  );
};

export default PageNotFound;
