import java.awt.Color;

import javax.swing.JButton;


public class CheckValue {
	public static JButton bttn;
	public static JButton bttn2;
	public static JButton bttn3;
	
	CheckValue(){
	}
	void checkmatch(){
		int nw1,nw2,nw3;
		String s1 = bttn.getText();
		if(s1.equals("NULL")) nw1 = -100;
		else nw1 = Integer.parseInt(s1);
		
		String s2 = bttn2.getText();
		if(s2.equals("NULL")) nw2 = -1000;
		else nw2 = Integer.parseInt(s2);
		
		String s3 = bttn3.getText();
		if(s3.equals("NULL")) nw3 = -10000;
		else nw3 = Integer.parseInt(s3);
		
		bttn.setBackground(Color.GRAY);
		bttn.setContentAreaFilled(false);
		bttn.setOpaque(true);
		bttn2.setBackground(Color.GRAY);
		bttn2.setContentAreaFilled(false);
		bttn2.setOpaque(true);
		bttn3.setBackground(Color.GRAY);
		bttn3.setContentAreaFilled(false);
		bttn3.setOpaque(true);
		
		
		//Check between a and b
		int flag= 0;
		if(Math.abs(nw1-nw2)<=1) flag = 1;
		if(flag==1) {
			bttn.setBackground(Color.RED);
			bttn2.setBackground(Color.RED);
		}
		//Check between b and c
		int flag2= 0;
		if(Math.abs(nw2-nw3)<=1) flag2 = 1;
		if(flag2==1) {
			bttn2.setBackground(Color.RED);
			bttn3.setBackground(Color.RED);
		}
		
		//Check between a,b,c
		if(flag==1&&flag2==1){
			bttn2.setBackground(Color.GREEN);
		}	
	}
}
