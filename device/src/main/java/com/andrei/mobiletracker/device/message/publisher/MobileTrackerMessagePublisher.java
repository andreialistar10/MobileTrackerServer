package com.andrei.mobiletracker.device.message.publisher;

import com.andrei.mobiletracker.device.message.event.MobileTrackerMessageEvent;

public interface MobileTrackerMessagePublisher<T extends MobileTrackerMessageEvent> {

    void publish(T messageEvent);
}
