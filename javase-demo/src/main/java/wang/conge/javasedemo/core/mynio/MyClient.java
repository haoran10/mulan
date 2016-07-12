package wang.conge.javasedemo.core.mynio;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyClient {

    private final static Logger logger = Logger.getLogger(MyClient.class.getName());

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100; i++) {
            final int idx = i;
            new Thread(new MyRunnable(idx)).start();
        }
    }

    private static final class MyRunnable implements Runnable {

        private final int idx;

        private MyRunnable(int idx) {
            this.idx = idx;
        }

        public void run() {
            //SocketChannel是一个连接到TCP网络套接字的通道
            SocketChannel socketChannel = null;
            try {
                //创建一个SocketChannel
                socketChannel = SocketChannel.open();
                //InetSocketAddress此类实现IP套接字地址（IP 地址 + 端口号）
                SocketAddress socketAddress = new InetSocketAddress("localhost", 10000);
                socketChannel.connect(socketAddress);

                MyRequestObject myRequestObject = new MyRequestObject("request_" + idx, "request_" + idx);
                logger.log(Level.INFO, myRequestObject.toString());
                sendData(socketChannel, myRequestObject);

                MyResponseObject myResponseObject = receiveData(socketChannel);
                logger.log(Level.INFO, myResponseObject.toString());
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
            } finally {
                try {
                    socketChannel.close();
                } catch (Exception ex) {
                }
            }
        }

        private void sendData(SocketChannel socketChannel, MyRequestObject myRequestObject) throws IOException {
            byte[] bytes = SerializableUtil.toBytes(myRequestObject);
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            socketChannel.write(buffer);
            socketChannel.socket().shutdownOutput();
        }

        private MyResponseObject receiveData(SocketChannel socketChannel) throws IOException {
            MyResponseObject myResponseObject = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            try {
                //字节缓冲区ByteBuffer
                //是一个直接字节缓冲区
                ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                byte[] bytes;
                int count = 0;
                while ((count = socketChannel.read(buffer)) >= 0) {
                    /**
                     * flip()方法
                     * flip方法将Buffer从写模式切换到读模式。调用flip()方法会将position设回0，并将limit设置成之前position的值。
                     * 换句话说，position现在用于标记读的位置，limit表示之前写进了多少个byte、char等 —— 现在能读取多少个byte、char等。
                     */
                    buffer.flip();
                    bytes = new byte[count];
                    //使用get()方法从Buffer中读取数据
                    buffer.get(bytes);
                    baos.write(bytes);
                    buffer.clear();
                }
                bytes = baos.toByteArray();
                //反序列化
                Object obj = SerializableUtil.toObject(bytes);
                myResponseObject = (MyResponseObject) obj;
                socketChannel.socket().shutdownInput();
            } finally {
                try {
                    baos.close();
                } catch (Exception ex) {
                }
            }
            return myResponseObject;
        }
    }
}