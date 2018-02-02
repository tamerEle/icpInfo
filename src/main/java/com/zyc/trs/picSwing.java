package com.zyc.trs;

import java.awt.*;
import java.io.UnsupportedEncodingException;

import javax.swing.*;
public class picSwing extends JPanel implements Runnable
{
    private Image img;
    String imagePath;
    public picSwing()
    {
       //相对路经，注意有个点
       img = Toolkit.getDefaultToolkit().getImage("E:\\stsworkspace\\icpInfo\\src\\main\\resources\\static\\pic\\mpl.jpg");
    }  
    public picSwing(String picPath)
    {
    	this.imagePath = picPath;
       //相对路经，注意有个点
       img = Toolkit.getDefaultToolkit().getImage(picPath);
    } 
    public void paint(Graphics g)
   {
       //调用父类的paint方法是画背景
       super.paint(g);
       g.drawImage(img,0,0,null);
   }
    public void showPic(String picPath) {
    	JFrame f = new JFrame();
        ImageIcon icon=new ImageIcon(); 
        Image image=Toolkit.getDefaultToolkit().getImage(picPath);  
        icon.setImage(image);
        Container cp = f.getContentPane();
        JButton button = new JButton();  
        button.setIcon(icon);  
        cp.add(button);
        //f.getContentPane()
        f.setSize(400,300);
        f.setVisible(true);
        
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        f.dispose();
    }
    public static void main(String[] args)
    {
    	try {
			byte[] validataCodeBytes = "v中文".getBytes("UTF-8");
			byte[] validataCodeByte = "v中文".getBytes("GBK");
			for (byte b : validataCodeBytes) {
				System.out.print(b + " ");
			}
			System.out.println();
			System.out.println(new String(validataCodeBytes,"utf-8"));
			System.out.println(new String(validataCodeBytes,"GBK"));
			for (byte b : validataCodeByte) {
				System.out.print(b + " ");
			}
			System.out.println();
			System.out.println(new String(validataCodeByte,"GBK"));
			System.out.println(new String(validataCodeByte,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
	@Override
	public void run() {
		showPic(this.imagePath);
		
	}
}