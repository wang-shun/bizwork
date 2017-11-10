package com.sogou.bizwork.cas.utils;

import org.jasig.cas.authentication.principal.Credentials;

public class BizworkCredentials implements Credentials {

    private static final long serialVersionUID = 1361303175432363081L;

    private String username;

    private String password;

    /**
     * @return Returns the password.
     */
    public final String getPassword() {
        return this.password;
    }

    /**
     * @param password The password to set.
     */
    public final void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return Returns the userName.
     */
    public final String getUsername() {
        return this.username;
    }

    /**
     * @param userName The userName to set.
     */
    public final void setUsername(final String userName) {
        this.username = userName;
    }

    public String toString() {
        return "[username: " + this.username + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final BizworkCredentials other = (BizworkCredentials) obj;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

}
