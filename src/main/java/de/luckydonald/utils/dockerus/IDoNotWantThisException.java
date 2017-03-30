package de.luckydonald.utils.dockerus;

import com.spotify.docker.client.DockerCertificateException;

/**
 * This can be raised if something should be raised and we want to catch something.
 *
 * @author luckydonald
 **/
public class IDoNotWantThisException extends Exception {

    public IDoNotWantThisException(Exception e) {
        super(e.getMessage(), e);
    }
    public IDoNotWantThisException(final String message) {
        super(message);
    }

    public IDoNotWantThisException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public IDoNotWantThisException(final Throwable cause) {
        super(cause);
    }
}