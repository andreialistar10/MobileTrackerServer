export const getMidnightDate = () => {
  const midnightDate = new Date();
  midnightDate.setHours(0, 0, 0, 0);
  return midnightDate;
};
