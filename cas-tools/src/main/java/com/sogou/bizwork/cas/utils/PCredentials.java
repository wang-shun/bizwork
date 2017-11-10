package com.sogou.bizwork.cas.utils;

import org.jasig.cas.authentication.principal.Credentials;

public class PCredentials implements Credentials {

    private static final long serialVersionUID = 1361303175432363083L;

    private String username;

    private String token;

    /**
     * @return Returns the token.
     */
    public final String getToken() {
        return this.token;
    }

    /**
     * @param token The token to set.
     */
    public final void setToken(final String token) {
        this.token = token;
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
        result = prime * result + ((token == null) ? 0 : token.hashCode());
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
        final PCredentials other = (PCredentials) obj;
        if (token == null) {
            if (other.token != null)
                return false;
        } else if (!token.equals(other.token))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

}
