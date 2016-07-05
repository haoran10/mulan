package wang.conge.javasedemo.core.jdk8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTest {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		for(int i=0;i<=1000;i++){
			list.add(i);
		}
		System.out.println("xx" + Thread.currentThread().getId());
		List<Integer> list2 = list.parallelStream()
		.filter(num-> num>2)
		.map(num -> {
				num = num + 1;
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ThreadLoaaa.getString();
				return num;
		}).collect(Collectors.toList());
		
		System.out.println("xx" + Thread.currentThread().getId());
		for(int i:list2){
			System.out.println(i);
		}
	}

}
