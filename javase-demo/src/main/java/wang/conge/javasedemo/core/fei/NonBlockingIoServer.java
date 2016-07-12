package wang.conge.javasedemo.core.fei;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
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

public class NonBlockingIoServer {

    private final static Logger log = Logger.getLogger(NonBlockingIoServer.class.getName());
    static ExecutorService threadPool = Executors.newFixedThreadPool(1000);
    static Selector selector = null;
    
    static ByteBuffer receivebuffer = ByteBuffer.allocate(1024);
    static ByteBuffer sendbuffer = ByteBuffer.allocate(1024);
    static AtomicInteger flag = new AtomicInteger(0);
    
    public static void main(String[] args) {
        Selector selector = null;
        ServerSocketChannel serverSocketChannel = null;
        try {
            //打开一个selector
            selector = Selector.open();

            // Create a new server socket and set to non blocking mode
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            // Bind the server socket to the local host and port
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
            	log.log(Level.INFO, "NUM:===" + num);
                // Someone is ready for I/O, get the ready keys
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();

                // Walk through the ready keys collection and process date requests.
                while (it.hasNext()) {
                    SelectionKey readyKey = it.next();
                    it.remove();
                    
                    deal(readyKey);
                }
            }
        } catch (ClosedChannelException ex) {
            log.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
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

    private static void deal(SelectionKey selectionKey) throws IOException {
    	// 接受请求
		ServerSocketChannel server = null;
		SocketChannel client = null;
		String receiveText;
		String sendText;
		int count=0;
		// 测试此键的通道是否已准备好接受新的套接字连接。
		if (selectionKey.isAcceptable()) {
			// 返回为之创建此键的通道。
			server = (ServerSocketChannel) selectionKey.channel();
			// 接受到此通道套接字的连接。
			// 此方法返回的套接字通道（如果有）将处于阻塞模式。
			client = server.accept();
			if(client == null){
				return;
			}
			// 配置为非阻塞
			client.configureBlocking(false);
			// 注册到selector，等待连接
			client.register(selector, SelectionKey.OP_READ);
		} else if (selectionKey.isReadable()) {
			// 返回为之创建此键的通道。
			client = (SocketChannel) selectionKey.channel();
			
			//将缓冲区清空以备下次读取
			receivebuffer .clear();
			//读取服务器发送来的数据到缓冲区中
			count = client.read(receivebuffer);
			if (count > 0) {
				receiveText = new String( receivebuffer.array(),0,count);
				System.out.println("服务器端接受客户端数据--:"+receiveText);
				client.register(selector, SelectionKey.OP_WRITE);
			}
		} else if (selectionKey.isWritable()) {
			//将缓冲区清空以备下次写入
			sendbuffer.clear();
			// 返回为之创建此键的通道。
			client = (SocketChannel) selectionKey.channel();
			int cc = flag.incrementAndGet();
			sendText="message from server--" + cc;
			//向缓冲区中输入数据
			sendbuffer.put(sendText.getBytes());
			 //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
			sendbuffer.flip();
			//输出到通道
			client.write(sendbuffer);
			System.out.println("服务器端向客户端发送数据--："+sendText);
			
//			client.register(selector, SelectionKey.OP_READ);
		}
	}

	private static void execute(SocketChannel socketChannel) throws IOException {
    	threadPool.execute(()->{
            try {
            	String receiveData = receiveData(socketChannel);
            	log.log(Level.INFO, receiveData );
            	
            	sendData(socketChannel, "response :"+receiveData);
                log.log(Level.INFO, "response :"+receiveData);
            } catch (Exception e) {
            	log.log(Level.SEVERE, "catch:" + e.getMessage());
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
                	log.log(Level.SEVERE, "finally" + e.getMessage());
                }
            }
    	});
        
    }

    private static String receiveData(SocketChannel socketChannel){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

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
            return new String(bytes);
        }catch (Exception e) {
        	log.log(Level.SEVERE, "catch:" + e.getMessage());
		}finally {
            try {
                baos.close();
            } catch (Exception ex) {
            	log.log(Level.SEVERE, "finally:" + ex.getMessage());
            }
        }
        
        return "";
    }

    private static void sendData(SocketChannel socketChannel, String result){
        ByteBuffer buffer = ByteBuffer.wrap(result.getBytes());
        /**
         * 写入 SocketChannel
         * 写数据到SocketChannel用的是SocketChannel.write()方法，
         * 该方法以一个Buffer作为参数
         */
        try {
			socketChannel.write(buffer);
		} catch (IOException e) {
			log.log(Level.SEVERE, "catch:" + e.getMessage());
		}
    }
}