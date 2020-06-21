// eslint-disable-next-line no-unused-vars
import * as encoding from 'text-encoding';
import {Client} from '@stomp/stompjs';
import {ROOT_BACKEND_URL_STOMP_ENDPOINT} from './constants';

const SockJS = require('sockjs-client/dist/sockjs.js');

const buildStompClient = (autoReconnect) => {
  const client = new Client();
  client.configure({
    appendMissingNULLonIncoming: true,
    logRawCommunication: true,
    reconnectDelay: autoReconnect ? 5000 : 0,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
    webSocketFactory: () => {
      return new SockJS(ROOT_BACKEND_URL_STOMP_ENDPOINT);
    },
    beforeConnect: () => {
      console.log('Trying to connect');
    },
    onStompError: (error) => {
      console.log(error);
    },
    onWebSocketError: (error) => {
      // console.log('WS error');
      console.log(error);
    },
    onDisconnect: () => {
      console.log('disconnect');
    },
    debug: (str) => {
      console.log(new Date(), str);
    },
  });
  return client;
};

const defaultAction = () => {};

const defaultActionOnMessageReceive = (message) => {
  console.log(message);
};

const subscribeToTopics = (client, topics, actionsOnMessageReceived) => {
  for (let index = 0; index < topics.length; ++index) {
    client.subscribe(topics[index], (message) => {
      const messageBody = JSON.parse(message.body);
      actionsOnMessageReceived[index](messageBody);
    });
  }
};

const stompSubscribeOnConnect = (
  topics,
  actionsOnMessageReceived,
  autoReconnect = true,
  token,
  actionOnConnect = defaultAction,
  onWebSocketClose = defaultAction,
) => {
  let connected = false;
  const client = buildStompClient(autoReconnect);
  client.onConnect = () => {
    subscribeToTopics(client, topics, actionsOnMessageReceived);
    connected = true;
    if (actionOnConnect !== false) {
      actionOnConnect();
    }
  };
  client.onWebSocketClose = () => {
    if (autoReconnect || (autoReconnect === false && connected === false)) {
      onWebSocketClose();
    }
  };
  if (token) {
    client.connectHeaders = {'X-Authorization': token};
  }

  return client;
};

export const connectToPairingStompBroker = (
  id,
  onConnectionError,
  actionOnConnect,
  token = null,
  onMessageReceived,
  onErrorReceived = defaultActionOnMessageReceive,
) => {
  const eventTopic = `/devices/${id}/pairing`;
  const errorTopic = `/errors/pairing-device/${id}`;
  return stompSubscribeOnConnect(
    [eventTopic, errorTopic],
    [onMessageReceived, onErrorReceived],
    false,
    token,
    actionOnConnect,
    onConnectionError,
  );
};

export const connectToNotificationStompBroker = (
  id,
  token,
  onMessageReceived,
) => {
  const eventTopic = `/devices/${id}/notifications`;
  return stompSubscribeOnConnect(
    [eventTopic],
    [onMessageReceived],
    true,
    token,
    () => {
      console.log('CONNECTED');
    },
    () => {
      console.log('CLOSEEEEEEEEE');
    },
  );
};
