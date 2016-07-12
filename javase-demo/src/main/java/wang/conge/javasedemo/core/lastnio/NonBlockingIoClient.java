package wang.conge.javasedemo.core.lastnio;

import wang.conge.javasedemo.core.lastnio.NIOClient;

public class NonBlockingIoClient {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 1000; i++) {
            new Thread(new NIOClient(i)).start();
        }
    }
}