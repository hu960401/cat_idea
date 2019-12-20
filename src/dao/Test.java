package dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Test {
	static boolean handle(String src){
		//判断长度
		if(src==null||src.length()<=8){
			return false;
		}
		
		//判断是否包括大小写、数字、其它这四种的至少三种
		int[]arr = new int[4];
		int count=0;
		for(int i=0;i<src.length();i++){
			if(src.charAt(i)>='a'&&src.charAt(i)<='z'){
				arr[0]++;
			}else if(src.charAt(i)>='A'&&src.charAt(i)<='Z'){
				arr[1]++;
			}else if(src.charAt(i)>='0'&&src.charAt(i)<='9'){
				arr[2]++;
			}else{
				arr[3]++;
			}
		}
		for(int i=0;i<arr.length;i++){
			if(arr[i]>0){
				count++;
			}
		}
		if(count<3){
			return false;
		}
		
		//判断是否有重复的子串，这些子串长度大于2
		String tmp;
		for(int i=0;i<src.length()-5;i++){
			tmp = src.substring(i,i+3);
			if(src.indexOf(tmp, i+3)!=-1){
				return false;
			}
		}
		return true;
	}
	
	public static Map<String,String>getMap(){
		String s;
		Map<String,String> m= new HashMap<String,String>();
		Scanner in = new Scanner(System.in);
		while(true){
			System.out.println("请输入下一个字符串，如果输入quit则退出");
			s = in.next();
			if(s.equals("quit")){
				break;
			}
			boolean f = handle(s);
			String answer = f?"OK":"NO";
			m.put(s, answer);		
		}
		return m;
	}
	
	public static void main(String[] args) {
		Map<String,String>m = getMap();
		System.out.println("输入内容：");
		Set<String> s = m.keySet();
		for(String k:s){
			System.out.println(k);
		}
		System.out.println("输出结果：");
		for(String k:s){
			System.out.println(m.get(k));
		}	
	}
}
