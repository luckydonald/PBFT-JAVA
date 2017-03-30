package de.luckydonald.utils.mockups;

import de.luckydonald.utils.ObjectWithLogger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.channels.SocketChannel;

/**
 * Mocks a socket. For testing.
 **/
public class SocketMockup extends Socket {
    public InputStream in = null;
    public SocketMockup() {
        System.out.print("Mock Socket");
    }

    public SocketMockup(Proxy proxy) {
        throw new UnsupportedOperationException("Not implemented."); // super(proxy);
    }

    protected SocketMockup(SocketImpl impl) throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // super(impl);
    }

    public SocketMockup(String host, int port) throws UnknownHostException, IOException {
        throw new UnsupportedOperationException("Not implemented."); // super(host, port);
    }

    public SocketMockup(InetAddress address, int port) throws IOException {
        throw new UnsupportedOperationException("Not implemented."); // super(address, port);
    }

    public SocketMockup(String host, int port, InetAddress localAddr, int localPort) throws IOException {
        throw new UnsupportedOperationException("Not implemented."); // super(host, port, localAddr, localPort);
    }

    public SocketMockup(InetAddress address, int port, InetAddress localAddr, int localPort) throws IOException {
        throw new UnsupportedOperationException("Not implemented."); // super(address, port, localAddr, localPort);
    }

    public SocketMockup(String host, int port, boolean stream) throws IOException {
        throw new UnsupportedOperationException("Not implemented."); // super(host, port, stream);
    }

    public SocketMockup(InetAddress host, int port, boolean stream) throws IOException {
        throw new UnsupportedOperationException("Not implemented."); // super(host, port, stream);
    }

    @Override
    public void connect(SocketAddress endpoint) throws IOException {
        throw new UnsupportedOperationException("Not implemented."); // super.connect(endpoint);
    }

    @Override
    public void connect(SocketAddress endpoint, int timeout) throws IOException {
        throw new UnsupportedOperationException("Not implemented."); // super.connect(endpoint, timeout);
    }

    @Override
    public void bind(SocketAddress bindpoint) throws IOException {
        throw new UnsupportedOperationException("Not implemented."); // super.bind(bindpoint);
    }

    @Override
    public InetAddress getInetAddress() {
        throw new UnsupportedOperationException("Not implemented."); // return super.getInetAddress();
    }

    @Override
    public InetAddress getLocalAddress() {
        throw new UnsupportedOperationException("Not implemented."); // return super.getLocalAddress();
    }

    @Override
    public int getPort() {
        throw new UnsupportedOperationException("Not implemented."); // return super.getPort();
    }

    @Override
    public int getLocalPort() {
        throw new UnsupportedOperationException("Not implemented."); // return super.getLocalPort();
    }

    @Override
    public SocketAddress getRemoteSocketAddress() {
        throw new UnsupportedOperationException("Not implemented."); // return super.getRemoteSocketAddress();
    }

    @Override
    public SocketAddress getLocalSocketAddress() {
        throw new UnsupportedOperationException("Not implemented."); // return super.getLocalSocketAddress();
    }

    @Override
    public SocketChannel getChannel() {
        throw new UnsupportedOperationException("Not implemented."); // return super.getChannel();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        System.out.println("getInputStream()");
        return in;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Not implemented."); // return super.getOutputStream();
    }

    @Override
    public void setTcpNoDelay(boolean on) throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // super.setTcpNoDelay(on);
    }

    @Override
    public boolean getTcpNoDelay() throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // return super.getTcpNoDelay();
    }

    @Override
    public void setSoLinger(boolean on, int linger) throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // super.setSoLinger(on, linger);
    }

    @Override
    public int getSoLinger() throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // return super.getSoLinger();
    }

    @Override
    public void sendUrgentData(int data) throws IOException {
        throw new UnsupportedOperationException("Not implemented."); // super.sendUrgentData(data);
    }

    @Override
    public void setOOBInline(boolean on) throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // super.setOOBInline(on);
    }

    @Override
    public boolean getOOBInline() throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // return super.getOOBInline();
    }

    @Override
    public synchronized void setSoTimeout(int timeout) throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // super.setSoTimeout(timeout);
    }

    @Override
    public synchronized int getSoTimeout() throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // return super.getSoTimeout();
    }

    @Override
    public synchronized void setSendBufferSize(int size) throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // super.setSendBufferSize(size);
    }

    @Override
    public synchronized int getSendBufferSize() throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // return super.getSendBufferSize();
    }

    @Override
    public synchronized void setReceiveBufferSize(int size) throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // super.setReceiveBufferSize(size);
    }

    @Override
    public synchronized int getReceiveBufferSize() throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // return super.getReceiveBufferSize();
    }

    @Override
    public void setKeepAlive(boolean on) throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // super.setKeepAlive(on);
    }

    @Override
    public boolean getKeepAlive() throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // return super.getKeepAlive();
    }

    @Override
    public void setTrafficClass(int tc) throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // super.setTrafficClass(tc);
    }

    @Override
    public int getTrafficClass() throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // return super.getTrafficClass();
    }

    @Override
    public void setReuseAddress(boolean on) throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // super.setReuseAddress(on);
    }

    @Override
    public boolean getReuseAddress() throws SocketException {
        throw new UnsupportedOperationException("Not implemented."); // return super.getReuseAddress();
    }

    @Override
    public synchronized void close() throws IOException {
        System.out.println("close()");
    }

    @Override
    public void shutdownInput() throws IOException {
        throw new UnsupportedOperationException("Not implemented."); // super.shutdownInput();
    }

    @Override
    public void shutdownOutput() throws IOException {
        throw new UnsupportedOperationException("Not implemented."); // super.shutdownOutput();
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not implemented."); // return super.toString();
    }

    @Override
    public boolean isConnected() {
        throw new UnsupportedOperationException("Not implemented."); // return super.isConnected();
    }

    @Override
    public boolean isBound() {
        throw new UnsupportedOperationException("Not implemented."); // return super.isBound();
    }

    @Override
    public boolean isClosed() {
        throw new UnsupportedOperationException("Not implemented."); // return super.isClosed();
    }

    @Override
    public boolean isInputShutdown() {
        throw new UnsupportedOperationException("Not implemented."); // return super.isInputShutdown();
    }

    @Override
    public boolean isOutputShutdown() {
        throw new UnsupportedOperationException("Not implemented."); // return super.isOutputShutdown();
    }

    @Override
    public void setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
        throw new UnsupportedOperationException("Not implemented."); // super.setPerformancePreferences(connectionTime, latency, bandwidth);
    }
}