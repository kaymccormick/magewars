package us.heptet.magewars.domain.persistence.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.math.BigInteger;

/* Created by kay on 5/14/2014. */
/**
 *
 *
 * spring source definition
 *
 * create table UserConnection (userId varchar(255) not null,
 providerId varchar(255) not null,
 providerUserId varchar(255),
 rank int not null,
 displayName varchar(255),
 profileUrl varchar(512),
 imageUrl varchar(512),
 accessToken varchar(255) not null,
 secret varchar(255),
 refreshToken varchar(255),
 expireTime bigint,
 primary key (userId, providerId, providerUserId));
 create unique index UserConnectionRank on UserConnection(userId, providerId, rank);

 */
@Entity
@IdClass(UserConnectionPK.class)

public class UserConnection {
    /* the primary key should match the definition provided by spring social */
    @OneToOne
    @JoinColumn(name = "userid", referencedColumnName = "username")
    User user;

    @Id
    String providerId;
    @Id
    String providerUserId;
    int rank;
    String displayName;
    String profileUrl;
    String imageUrl;
    String accessToken;
    String secret;
    String refreshToken;
    BigInteger expireTime;
}
