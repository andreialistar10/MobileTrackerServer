package com.andrei.mobiletracker.user.service.mailsender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailUserDetails {

    private String username;
    private String destinationEmail;
    private String firstName;
    private String lastName;
}
