package com.example.authserver.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

/**
 * @author liuxy
 * @date 2018-08-17 16:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Entity
public class UserEntity implements Serializable {
    @Id
    private String id;
    @Column
    private String  userName;
    @Column
    private String nickname;
    @Column
    private String roles;
}
