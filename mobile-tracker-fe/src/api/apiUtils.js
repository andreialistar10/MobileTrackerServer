export const handleConnectionAndServerErrors = (error) => {
  if (!error.response || Math.trunc(error.response.status / 100) === 5) {
    throw new Error("Something went wrong! Please try again later!");
  }
};
