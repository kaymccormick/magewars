package us.heptet.magewars.domain.persistence.jpa;

import java.io.Serializable;

/* Created by kay on 5/14/2014. */
/**
 * Primary key type for {@link UserConnection} entity class.
 */

public class UserConnectionPK  implements Serializable
{
    String providerId;
    String providerUserId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserConnectionPK)) return false;

        UserConnectionPK that = (UserConnectionPK) o;

        if (!providerId.equals(that.providerId)) return false;
        if (!providerUserId.equals(that.providerUserId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = providerId.hashCode();
        result = 31 * result + providerUserId.hashCode();
        return result;
    }
}
