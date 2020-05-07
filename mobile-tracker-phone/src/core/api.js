const BACKEND_HOST = '192.168.100.3';
const BACKEND_URL_API = `http://${BACKEND_HOST}:8080/mobile-tracker/`;

function setTimeoutFetch(url, config, ms = 10000) {
  return new Promise((resolve, reject) => {
    const timeoutId = setTimeout(() => {
      reject(new Error('promise timeout'));
    }, ms + 1000);
    fetch(url, config).then(
      (res) => {
        clearTimeout(timeoutId);
        resolve(res);
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
  return setTimeoutFetch(`${BACKEND_URL_API}devices`, config)
    .then((response) => {
      const {status} = response;
      if (status / 100 === 4) {
        throw {error: 'Invalid device information!'};
      }
      if (status / 100 === 5) {
        throw -1;
      }
      return Promise.resolve(response.json());
    })
    .catch((err) => {
      console.log(err);
      if (err && err.error) {
        throw new Error(err.error);
      }
      throw new Error('Something went wrong! Please try again later!');
    });
};
