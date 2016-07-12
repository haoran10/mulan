package wang.conge.javasedemo.core.mynio;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyNewChannelServer {

    private final static Logger logger = Logger.getLogger(MyNewChannelServer.class.getName());
    static ExecutorService threadPool = Executors.newFixedThreadPool(1000);
    static AtomicInteger count = new AtomicInteger();
    static  Selector selector = null;
    public static void main(String[] args) {
        ServerSocketChannel serverSocketChannel = null;

        try {
            //打开一个selector
            selector = Selector.open();

            // Create a new server socket and set to non blocking mode
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            // Bind the server socket to the local host and port
            serverSocketChannel.socket().setReuseAddress(true);
            serverSocketChannel.socket().bind(new InetSocketAddress(10000));

            // Register accepts on the server socket with the selector. This
            // step tells the selector that the socket wants to be put on the
            // ready list when accept operations occur, so allowing multiplexed
            // non-blocking I/O to take place.
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            // Here's where everything happens. The select method will
            // return when any operations registered above have occurred, the
            // thread has been interrupted, etc.
            while (true) {
            	int num = selector.select();
            	logger.log(Level.INFO, "" + num);
                // Someone is ready for I/O, get the ready keys
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();

                // Walk through the ready keys collection and process date requests.
                while (it.hasNext()) {
                    SelectionKey readyKey = it.next();
                    it.remove();

                    execute(readyKey);
                }
            }
        } catch (ClosedChannelException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            try {
                selector.close();
            } catch (Exception ex) {
            }
            try {
                serverSocketChannel.close();
            } catch (Exception ex) {
            }
        }
    }
    
    private static void execute(SelectionKey key) throws IOException {
    	// The key indexes into the selector so you
        // can retrieve the socket that's ready for I/O
    	if (key.isAcceptable()) {
    		ServerSocketChannel selectServerSocketChannel =  (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = selectServerSocketChannel.accept();// non-blocking I/O
            
            if(socketChannel==null){
            	logger.log(Level.INFO, "socketChannel is null,continue" );
            	return;
            }
            socketChannel.configureBlocking(false);
            
            System.out.println("Accepted connection from " + socketChannel);
            
            socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
    	}
    	if (key.isReadable()) {
            SocketChannel socketChannel =  (SocketChannel) key.channel();// non-blocking I/O
            
            MyRequestObject myRequestObject = receiveData(socketChannel);
            logger.log(Level.INFO, myRequestObject.toString());
            
            SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_WRITE);
            selectionKey.attach(myRequestObject);
    	}
    	if (key.isWritable()) {
    		SocketChannel socketChannel =  (SocketChannel) key.channel();// non-blocking I/O
    		
    		MyRequestObject myRequestObject  = (MyRequestObject) key.attachment();
    		if(myRequestObject == null){
    			return;
    		}
    		MyResponseObject myResponseObject = new MyResponseObject(
                    "response for " + myRequestObject.getName(),
                    "response for " + myRequestObject.getValue());
            sendData(socketChannel, myResponseObject);
            logger.log(Level.INFO, myResponseObject.toString());
            
            socketChannel.register(selector, SelectionKey.OP_READ);
    	}
        
    }
    private static void execute(SocketChannel socketChannel) throws IOException {
    	threadPool.execute(()->{
            try {
            	count.incrementAndGet();
            	System.out.println(count);
                MyRequestObject myRequestObject = receiveData(socketChannel);
                logger.log(Level.INFO, myRequestObject.toString());

                MyResponseObject myResponseObject = new MyResponseObject(
                        "response for " + myRequestObject.getName(),
                        "response for " + myRequestObject.getValue());
                sendData(socketChannel, myResponseObject);
                logger.log(Level.INFO, myResponseObject.toString());
            } catch (Exception e) {
            	logger.log(Level.SEVERE, "catch:" + e.getMessage());
				e.printStackTrace();
			} finally {
				if(socketChannel == null){
					return;
				}
                try {
                	if(socketChannel.isConnected()){
                		socketChannel.close();
                	}
                } catch (Exception e) {
                	logger.log(Level.SEVERE, "finally" + e.getMessage());
                }
            }
    	});
        
    }

    private static MyRequestObject receiveData(SocketChannel socketChannel) throws IOException {
        MyRequestObject myRequestObject = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteBuffer buffer = ByteBuffer.allocate(10240);

        try {
            byte[] bytes;
            int size = 0;
            /**
             * 从SocketChannel中读取数据
             * socketChannel.read(buffer)
             * 首先，分配一个Buffer。从SocketChannel读取到的数据将会放到这个Buffer中。
             * 然后，调用SocketChannel.read()。该方法将数据从SocketChannel 读到Buffer中。
             * read()方法返回的int值表示读了多少字节进Buffer里。如果返回的是-1，表示已经读到了流的末尾（连接关闭了）。
             */
            while (true) {
            	size = socketChannel.read(buffer) ;
            	if(size<0){
            		break;
            	}
                buffer.flip();
                bytes = new byte[size];
                buffer.get(bytes);
                baos.write(bytes);
                buffer.clear();
            }
            bytes = baos.toByteArray();
            Object obj = SerializableUtil.toObject(bytes);
            myRequestObject = (MyRequestObject) obj;
        }catch (Exception e) {
        	logger.log(Level.SEVERE, "CATCH:" + e.getMessage());
			e.printStackTrace();
		}finally {
            try {
                baos.close();
            } catch (Exception ex) {
            }
        }
        return myRequestObject;
    }

    private static void sendData(SocketChannel socketChannel, MyResponseObject myResponseObject) throws IOException {
        byte[] bytes = SerializableUtil.toBytes(myResponseObject);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        /**
         * 写入 SocketChannel
         * 写数据到SocketChannel用的是SocketChannel.write()方法，
         * 该方法以一个Buffer作为参数
         */
        socketChannel.write(buffer);
    }
}