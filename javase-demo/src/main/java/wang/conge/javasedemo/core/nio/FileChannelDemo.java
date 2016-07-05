package wang.conge.javasedemo.core.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo {
	public static void main(String[] args) throws IOException {
		testRead();
	}

	static void testRead() throws IOException {
		/**
		 * 1.从一个输入数据来源获取通道
		 */
		FileInputStream fin = new FileInputStream("/d:/apps/test.txt");
		FileChannel channel = fin.getChannel();
		
		/**
		 * 2.创建缓冲区
		 */
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		while (true) {
			buffer.clear();
			/**
			 * 3.把数据从通道读取到缓冲区
			 */
			int r = channel.read(buffer);
			if (r == -1) {
				break;
			}

			String str = new String(buffer.array());
			System.out.println(str);
			
			buffer.flip();
		}
		
		fin.close();
		channel.close();
	}

	static void testWrite() throws IOException {
		/**
		 * 1.从一个输出数据目的地获取通道
		 */
		FileOutputStream fout = new FileOutputStream("/d:/apps/test2.txt");
		FileChannel fc = fout.getChannel();
		
		/**
		 * 2.创建缓冲区
		 */
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		String[] message = new String[] { "hello", "conge", "byebye" };
		for (int i = 0; i < message.length; ++i) {
			buffer.put(message[i].getBytes());
			
		}
		buffer.flip();
		fc.write(buffer);

		fout.close();
		fc.close();
	}
}
