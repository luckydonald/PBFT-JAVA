package de.luckydonald.utils.dockerus;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.messages.Container;
import org.apache.commons.lang.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class DockerusDummy extends Dockerus{

    private DockerusDummy() throws DockerCertificateException {
        System.out.println("Project probably not loaded in docker-compose...");
    }

    static private DockerusDummy instance = null;

    static public DockerusDummy getInstance() throws DockerCertificateException {
        if (DockerusDummy.instance == null) {
            DockerusDummy.instance = new DockerusDummy();
        }
        return DockerusDummy.instance;
    }

    @Override
    public List<String> getHostnames(boolean excludeSelf) throws DockerException, InterruptedException {
        List<String> foo = new ArrayList<>();
        foo.add("localhost");
        return foo;
    }

    @Override
    public String getHostname() throws DockerException, InterruptedException {
        return "localhost";
    }

    @Override
    public DockerClient getCLI() {
        throw new NotImplementedException("Project probably not loaded in docker-compose...");
    }

    @Override
    public String getEnvHostname() {
        throw new NotImplementedException("Project probably not loaded in docker-compose...");
    }

    @Override
    public Container me() throws DockerException, InterruptedException {
        throw new NotImplementedException("Project probably not loaded in docker-compose...");
    }

    @Override
    public int getId() throws DockerException, InterruptedException {
        throw new NotImplementedException("Project probably not loaded in docker-compose...");
    }

    @Override
    public String getService() throws DockerException, InterruptedException {
        throw new NotImplementedException("Project probably not loaded in docker-compose...");
    }

    @Override
    public String getService(Container container) throws DockerException, InterruptedException {
        throw new NotImplementedException("Project probably not loaded in docker-compose...");
    }

    @Override
    public String getName() throws DockerException, InterruptedException {
        throw new NotImplementedException("Project probably not loaded in docker-compose...");
    }

    @Override
    public String getName(Container container) throws DockerException, InterruptedException {
        throw new NotImplementedException("Project probably not loaded in docker-compose...");
    }

    @Override
    public String getProject() throws DockerException, InterruptedException {
        throw new NotImplementedException("Project probably not loaded in docker-compose...");
    }

    @Override
    public String getProject(Container container) throws DockerException, InterruptedException {
        throw new NotImplementedException("Project probably not loaded in docker-compose...");
    }

    @Override
    public int getNumber() throws DockerException, InterruptedException {
        return 1;
    }

    @Override
    public int getNumber(Container container) throws DockerException, InterruptedException {
        throw new NotImplementedException("Project probably not loaded in docker-compose...");
    }

    @Override
    public List<Container> getContainers(boolean excludeSelf) throws DockerException, InterruptedException {
        throw new NotImplementedException("Project probably not loaded in docker-compose...");
    }
    private int total = 0;

    @Override
    public int getTotal(boolean excludeSelf) {
        return (excludeSelf ? total -1 : total);   // stored not excluded
    }

    public void setTotal(int total) {
        this.setTotal(total, false);
    }
    public void setTotal(int total, boolean excludeSelf) {
        if (excludeSelf) {
            total++;  // store not excluded
        }
        this.total = total;
    }
}
