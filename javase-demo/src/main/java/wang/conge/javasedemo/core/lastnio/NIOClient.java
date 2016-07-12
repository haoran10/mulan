package wang.conge.javasedemo.core.lastnio;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class NIOClient implements Runnable {

	/*标识数字*/
	private int flag = 0;
	/*缓冲区大小*/
	private int BLOCK = 4096;
	/*接受数据缓冲区*/
	private ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
	/*发送数据缓冲区*/
	private ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);
	/*服务器端地址*/
	private final InetSocketAddress SERVER_ADDRESS = new InetSocketAddress(
			"localhost", 8888);
	
	private AtomicInteger readTimes = new AtomicInteger(2);
	private AtomicBoolean closeFlag = new AtomicBoolean(false);
	
	public NIOClient(int index){
		this.flag  = index;
	}
	
	public void run() {
		try {
			deal();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void deal() throws IOException {
		// 打开socket通道
		SocketChannel socketChannel = SocketChannel.open();
		// 设置为非阻塞方式
		socketChannel.configureBlocking(false);
		// 打开选择器
		Selector selector = Selector.open();
		// 注册连接服务端socket动作
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		// 连接
		socketChannel.connect(SERVER_ADDRESS);
		// 分配缓冲区大小内存
		
		Set<SelectionKey> selectionKeys;
		Iterator<SelectionKey> iterator;
		SelectionKey selectionKey;
		SocketChannel client;
		String receiveText;
		String sendText;
		int count=0;

		while (true) {
			if(closeFlag.compareAndSet(true, false)){
				break;
			}
			//选择一组键，其相应的通道已为 I/O 操作准备就绪。
			//此方法执行处于阻塞模式的选择操作。
			selector.select();
			//返回此选择器的已选择键集。
			selectionKeys = selector.selectedKeys();
			//System.out.println(selectionKeys.size());
			iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				selectionKey = iterator.next();
				if (selectionKey.isConnectable()) {
					client = (SocketChannel) selectionKey.channel();
					// 判断此通道上是否正在进行连接操作。
					// 完成套接字通道的连接过程。
					if (client.isConnectionPending()) {
						client.finishConnect();
						String init = "init--" + flag;
						System.out.println(init);
						
						sendbuffer.clear();
						sendbuffer.put(init.getBytes());
						sendbuffer.flip();
						client.write(sendbuffer);
					}
					client.register(selector, SelectionKey.OP_READ);
				} else if (selectionKey.isReadable()) {
					client = (SocketChannel) selectionKey.channel();
					//将缓冲区清空以备下次读取
					receivebuffer.clear();
					//读取服务器发送来的数据到缓冲区中
					count=client.read(receivebuffer);
					if(count>0){
						receiveText = new String( receivebuffer.array(),0,count);
						System.out.println(receiveText);
						client.register(selector, SelectionKey.OP_WRITE);
					}
					
					int desc = readTimes.decrementAndGet();
					if(desc  == 0){
						client.close();
						closeFlag.set(true);
						break;
					}
				} else if (selectionKey.isWritable()) {
					sendbuffer.clear();
					client = (SocketChannel) selectionKey.channel();
					sendText = "request--" + flag;
					sendbuffer.put(sendText.getBytes());
					 //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
					sendbuffer.flip();
					client.write(sendbuffer);
					System.out.println(sendText);
					client.register(selector, SelectionKey.OP_READ);
				}
			}
			selectionKeys.clear();
			
			
		}
	}
}
