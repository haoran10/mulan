package wang.conge.javasedemo.core.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import wang.conge.javasedemo.core.fei.NonBlockingIoServer;

public class ServerSocketChannelMain {
	private final static Logger log = Logger.getLogger(ServerSocketChannelMain.class.getName());
	public static void main(String[] args) throws IOException {
		InetSocketAddress address = new InetSocketAddress(8080);
		
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking( false );
		
		ServerSocket ss = ssc.socket();
		ss.bind( address );
		
		Selector selector = Selector.open();
		ssc.register( selector, SelectionKey.OP_ACCEPT );
		
		while(true){
			selector.select();
			
			Iterator<SelectionKey> it = selector.selectedKeys().iterator();
			while(it.hasNext()){
				SelectionKey key = it.next();
				it.remove();
				
				if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    // 获得和客户端连接的通道  
                    SocketChannel channel = server.accept();  
                    // 设置成非阻塞  
                    channel.configureBlocking(false);  
  
                    //在这里可以给客户端发送信息哦  
                    channel.write(ByteBuffer.wrap(new String("向客户端发送了一条信息").getBytes()));  
                    //在和客户端连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限。  
                    channel.register(selector, SelectionKey.OP_READ);  
                      
                    // 获得了可读的事件  
                } else if (key.isReadable()) {  
                        read(key);  
                }  
			}
			
		}
		
	}
	
	 /** 
     * 处理读取客户端发来的信息 的事件 
     * @param key 
     * @throws IOException  
     */  
    public static void read(SelectionKey key) throws IOException{  
        // 服务器可读取消息:得到事件发生的Socket通道  
        SocketChannel socketChannel = (SocketChannel) key.channel();
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        
        String msg = null;
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
            msg =  new String(bytes);
        }catch (Exception e) {
        	log.log(Level.SEVERE, "catch:" + e.getMessage());
		}finally {
            try {
                baos.close();
            } catch (Exception ex) {
            	log.log(Level.SEVERE, "finally:" + ex.getMessage());
            }
        }
        
        
        System.out.println("服务端收到信息："+msg);  
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());  
        socketChannel.write(outBuffer);// 将消息回送给客户端  
    }  

}
