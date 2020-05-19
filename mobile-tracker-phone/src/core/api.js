import {getAuthorization} from './localStorage';
import {ROOT_BACKEND_URL_API} from './constants';

function handleResponse(response) {
  if (!response.ok) {
    return Promise.reject(response);
  }
  return Promise.resolve(response.json());
}

function setTimeoutFetch(url, config, ms = 10000) {
  return new Promise((resolve, reject) => {
    const timeoutId = setTimeout(() => {
      reject(new Error('promise timeout'));
    }, ms);
    fetch(url, config).then(
      (response) => {
        clearTimeout(timeoutId);
        resolve(handleResponse(response));
      },
      (err) => {
        clearTimeout(timeoutId);
        reject(err);
      },
    );
  });
}

export function registerDevice(deviceInformation) {
  const config = {
    method: 'POST',
    body: JSON.stringify(deviceInformation),
    headers: {'content-type': 'application/json', accept: 'application/json'},
  };
  return setTimeoutFetch(
    `${ROOT_BACKEND_URL_API}/unregistered-devices`,
    config,
  ).catch((error) => {
    console.log(error);
    return Promise.reject('Something went wrong! Please try again later!');
  });
}

export async function getPasswordDevice() {
  // try {
  //   const {token} = await getAuthorization();
  //   const config = {
  //     method: 'POST',
  //     headers: {accept: 'application/json', authorization: `Bearer ${token}`},
  //   };
  //   return setTimeoutFetch(
  //     `${BACKEND_URL_API}/unregistered-devices/startPairing`,
  //     config,
  //   );
  // } catch (error) {
  //   console.log(error);
  //   return Promise.reject('Something went wrong! Please try again later!');
  // }
  return getAuthorization().then(({token}) => {
    const config = {
      method: 'POST',
      headers: {accept: 'application/json', authorization: `Bearer ${token}`},
    };
    return setTimeoutFetch(
      `${ROOT_BACKEND_URL_API}/unregistered-devices/start-pairing`,
      config,
    ).catch((error) => {
      console.log(error);
      return Promise.reject('Something went wrong! Please try again later!');
    });
  });
}
