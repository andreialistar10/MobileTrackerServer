package com.andrei.mobiletracker.device.message.listener;

import com.andrei.mobiletracker.device.message.event.MobileTrackerMessageEvent;

public interface MobileTrackerMessageListener<T extends MobileTrackerMessageEvent> {

    void listen(T message);
}
