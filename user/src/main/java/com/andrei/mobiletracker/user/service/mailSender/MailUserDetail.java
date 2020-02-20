package com.andrei.mobiletracker.user.service.mailSender;

import com.andrei.mobiletracker.user.model.MyUser;
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
