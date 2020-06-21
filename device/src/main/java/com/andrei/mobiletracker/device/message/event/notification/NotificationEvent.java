package com.andrei.mobiletracker.device.message.event.notification;

import com.andrei.mobiletracker.device.message.event.MobileTrackerMessageEvent;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NotificationEvent implements MobileTrackerMessageEvent {

    private String deviceCode;
    private DeviceNotificationType type;
    private NotificationData data;
}
