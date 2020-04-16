export * from "./homepage";

export const stringIsBlank = (str) => {
  return !str || /^\s*$/.test(str);
};
