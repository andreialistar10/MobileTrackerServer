package com.andrei.mobiletracker.user.service.mailSender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailUserDetail {

    private String username;
    private String destinationEmail;
    private String firstName;
    private String lastName;
}
