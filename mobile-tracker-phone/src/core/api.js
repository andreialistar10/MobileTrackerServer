const BACKEND_HOST = '192.168.100.3';
const BACKEND_URL_API = `http://${BACKEND_HOST}:8080/mobile-tracker`;

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
    }, ms + 1000);
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

export const registerDevice = (deviceInformation) => {
  const config = {
    method: 'POST',
    body: JSON.stringify(deviceInformation),
    headers: {'content-type': 'application/json', accept: 'application/json'},
  };
  return setTimeoutFetch(
    `${BACKEND_URL_API}/unregistered-devices`,
    config,
  ).catch((error) => {
    console.log(error);
    return Promise.reject('Something went wrong! Please try again later!');
  });
};
