package de.luckydonald.utils.dockerus;

import com.spotify.docker.client.DockerCertificateException;

/**
 * Created by  on
 *
 * @author luckydonald
 * @since 13.12.2016
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