package com.zyc.trs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOTest {

	public IOTest() {
	}
	public static void main(String[]  args) {
		
		File outTextFile = new File("D:/outTextFile/out.txt");
		String outText = "钟元昌\t啊哈哈1\r\n";
		IOTest ioTest = new IOTest();
		ioTest.WriteInFile(outTextFile, outText);
	}
	public void WriteInFile(File outTextFile, String outText) {
		if(!outTextFile.getParentFile().exists()) {
			outTextFile.getParentFile().mkdirs();
		}
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(outTextFile);
			byte[] outTextByte = outText.getBytes("UTF-8");
			for (byte b : outTextByte) {
				fileOutputStream.write(b);
			}
			//fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void appendInFile(File outTextFile, String outText) {
		if(!outTextFile.getParentFile().exists()) {
			outTextFile.getParentFile().mkdirs();
		}
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(outTextFile, true);
			byte[] outTextByte = outText.getBytes("UTF-8");
			for (byte b : outTextByte) {
				fileOutputStream.write(b);
			}
			//fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
