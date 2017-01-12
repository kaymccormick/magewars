package us.heptet.magewars.rest.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by jade on 18/07/2016.
 */
@XmlRootElement
public class User implements Serializable {
    private String userName;
    private String emailAddress;

    public User() {
        /* no op */
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
