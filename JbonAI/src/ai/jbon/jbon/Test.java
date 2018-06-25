package ai.jbon.jbon;

import java.awt.Color;
import java.awt.Dimension;

import ai.jbon.jbon.gui.CPUMonitor;
import ai.jbon.jbon.gui.Window;

public class Test {

	public static void main(String[] args){
		
		Window w = new Window();
		
		CPUMonitor c = new CPUMonitor();
		c.setBounds(0,0,600, 400);
		c.setBarCount(8);
		c.setPeriodsPerSec(0.2F);
		c.setVisible(true);
		w.add(c);
		w.setVisible(true);
		
		long now = 0;
		long last = System.currentTimeMillis();
		long delta = 0;
		
		while(true){
			now = System.currentTimeMillis();
			delta += now - last;
			
			if(delta > 200){
				c.repaint();
				delta = 0;
			}
			
			last = now;
		}
	}
}
