package de.luckydonald.utils.dockerus;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.messages.Container;


import javax.cache.annotation.CacheResult;
import java.util.List;
import java.util.stream.Collectors;

// import static de.luckydonald.utils.lambdas.LambdaUtils.uncheckCall;

/**
 * @author luckydonald
 */
public class Dockerus {
    String LABEL_COMPOSE_CONTAINER_NUMBER = "com.docker.compose.container-number";
    String LABEL_COMPOSE_PROJECT = "com.docker.compose.project";
    String LABEL_COMPOSE_SERVICE = "com.docker.compose.service";

    final DockerClient docker;

    //CACHING_TIME = timedelta(seconds=60);

    static private Dockerus instance = null;

    static public Dockerus getInstance() throws DockerCertificateException {
        if (Dockerus.instance == null) {
            Dockerus.instance = new Dockerus();
        }
        return Dockerus.instance;
    }

    Dockerus() throws DockerCertificateException {
        // Create a client based on DOCKER_HOST and DOCKER_CERT_PATH env vars
        docker = DefaultDockerClient.fromEnv().build();
        // docker.listContainers(DockerClient.ListContainersParam.withLabel(""))
    }


    public DockerClient getCLI() {
        return docker;
    }

    public String getEnvHostname() {
        return System.getenv("HOSTNAME");
    }

    public Container me() throws DockerException, InterruptedException {
        return this.getCLI().listContainers().stream().filter(this::filterIsIdEqualHostname).limit(1).collect(Collectors.toList()).get(0);
    }

    public int getId() throws DockerException, InterruptedException {
        return Integer.parseInt(this.me().id());
    }

    public String getService() throws DockerException, InterruptedException {
        return this.getService(this.me());
    }
    public String getService(Container container) throws DockerException, InterruptedException {
        return container.labels().get(this.LABEL_COMPOSE_SERVICE);
    }

    public String getName() throws DockerException, InterruptedException {
        return this.getService();
    }
    public String getName(Container container) throws DockerException, InterruptedException {
        return this.getService(container);
    }

    public String getProject() throws DockerException, InterruptedException {
        return this.getProject(this.me());
    }
    public String getProject(Container container) throws DockerException, InterruptedException {
        return container.labels().get(this.LABEL_COMPOSE_PROJECT);
    }

    public int getNumber() throws DockerException, InterruptedException {
        return this.getNumber(this.me());
    }
    public int getNumber(Container container) throws DockerException, InterruptedException {
        return Integer.parseInt(container.labels().get(this.LABEL_COMPOSE_CONTAINER_NUMBER));
    }

    public List<Container> getContainers(boolean excludeSelf) throws DockerException, InterruptedException {
        Container me = me();
        return this.getCLI().listContainers(
                DockerClient.ListContainersParam.withLabel(this.LABEL_COMPOSE_PROJECT, this.getProject(me)),
                DockerClient.ListContainersParam.withLabel(this.LABEL_COMPOSE_SERVICE, this.getService(me))
        ).stream()
                .filter(c -> !excludeSelf || this.filterIsIdEqualHostname(c))
                .filter(c -> { try {
                    return this.getService(me).equals(c.labels().get(this.LABEL_COMPOSE_SERVICE));
                } catch (InterruptedException | DockerException e) {return false;}})
                //.filter(this::output)
                .collect(Collectors.toList());
    }

    public String getHostname() throws DockerException, InterruptedException {
        return this.getHostname(this.me());
    }

    String getHostname(Container container) throws DockerException, InterruptedException {
        return "" + this.getProject(container) + "_" + this.getService(container) + "_" + this.getNumber(container);
    }

    public List<String> getHostnames(boolean excludeSelf) throws DockerException, InterruptedException {
        return this.getContainers(excludeSelf).stream().map(c -> { try { return this.getHostname(c); } catch (InterruptedException | DockerException e) { e.printStackTrace(); return null; } }).filter(c -> c != null).collect(Collectors.toList());
        // #java_sucks  http://stackoverflow.com/a/19757456/3423324
    }

    boolean filterIsIdEqualHostname(Container c) {
        return c.id().substring(0, 12).equals(this.getEnvHostname().substring(0, 12));
    }

    boolean output(Object o) {
        System.out.println("LIST STREAM ELEMENT: " + o.toString());
        return true;
    }
}
