package com.andrei.mobiletracker.user.dao.userDetailDao.impl.jpaRepository;

import com.andrei.mobiletracker.user.dao.userDao.impl.jpaRepository.MyUserPersistence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "USER_DETAILS")
public class MyUserDetailPersistence {

    @Id
    @Column(name = "USERNAME")
    @Length(max = 255)
    private String username;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @PrimaryKeyJoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")
//    @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")
//    @MapsId
    private MyUserPersistence user;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;
}