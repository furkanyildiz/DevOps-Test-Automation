

package testing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class autorun {
	
	private String command1;
	private String command2;
	private String commandALL;
	private Boolean flag = false;
	
	private class MyThread  implements Runnable{
		String class_name;
		String path;
		public MyThread(String class_name, String path) {
			super();
			this.class_name = class_name;
			this.path = path;
		}

		@Override
		public void run() {
			try {
			
				// I try this
				Process p = Runtime.getRuntime().exec("bash runner.sh",null, new File(path));
				p.waitFor();
			

				// and I try this
				//Process p = Runtime.getRuntime().exec(commandALL,null, new File(path));
				//p.waitFor();
			
			} catch (Exception e) {
				System.err.println("getRuntime exception:"+e.getMessage());
				e.printStackTrace();
			}
			System.out.println("run1 sonlandı.");
			flag = true;
		}
		
		
	}
	private class MyThread2  implements Runnable{
		String class_name;
		String path;
		public MyThread2(String class_name, String path) {
			super();
			this.class_name = class_name;
			this.path = path;
		}

		@Override
		public void run() {
			try {
			
				// I try this
				Process p = Runtime.getRuntime().exec(command2,null, new File(path));
				p.waitFor();
			

				// and I try this
				//Process p = Runtime.getRuntime().exec(commandALL,null, new File(path));
				//p.waitFor();
			
			} catch (Exception e) {
				System.err.println("getRuntime2 exception:"+e.getMessage());
				e.printStackTrace();
			}
			System.out.println("run2 sonlandı.");
		}
		
		
	}
	
	public autorun(String class_name, String path) throws InterruptedException, IOException {
	    
		//class_name= "TestRunner";
		System.out.println(class_name);

		
		command1 = "javac -cp .:/home/bilmuhlab/Documents/Testing_group/jars/*: "+class_name+".java ";
		command2 = "java -cp .:/home/bilmuhlab/Documents/Testing_group/jars/*: org.junit.runner.JUnitCore " +class_name +" > test_result.txt";
		
		
		commandALL = command1 + " && " +  command2;
		System.out.println(commandALL);
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path +"/runner.sh"))) {

			bw.write(commandALL);

		} catch (IOException e) {

			e.printStackTrace();

		}
		
		
		Thread t = new Thread(new MyThread(class_name,path));

		try{ 
		    t.start();
    	}
    	catch(Exception e){
    		System.err.println("thread exception");
    	}
    	//also I need to wait for die this thread.
		t.join();
		System.out.println("javac join");

		while(!flag);
		Thread t2 = new Thread(new MyThread2(class_name,path));

		try{ 
		    t2.start();
    	}
    	catch(Exception e){
    		System.err.println("thread exception");
    	}
    	//also I need to wait for die this thread.
		t2.join();
		System.out.println("java join");


	}
	
	
}

