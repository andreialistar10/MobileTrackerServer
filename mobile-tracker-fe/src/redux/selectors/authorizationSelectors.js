export const getApiToken = ({ authorization }) => {
  return authorization.jwt;
};

export const getRefreshToken = ({ authorization }) => {
  return authorization.refreshToken;
};
