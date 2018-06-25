package ai.jbon.jbon.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.swing.JPanel;

/**
 * 
 * A Panel, which displays the CPU Usage in a Bar Diagram
 * Adjusts itself to its width
 * 
 * Properties can be changed
 * 
 * @author SilvanJost
 *
 */
public class CPUMonitor extends JPanel{

	private int barCount = 6;
	private float gap = 0.2F;
	
	private float[] bars;

	private float periodPos;
	
	private int barWidth;
	private int gapWidth;
	
	private float periodsPerSec = 0.5F;
	
	private long last;
	
	public CPUMonitor(){
		
		bars = new float[barCount];

		initializeDimensions();

	}

	/**
	 * 
	 * Shifts the values in the bar Array one position down
	 * 
	 */
	private void shiftBars(){
		
		for(int i=0;i<bars.length-1;i++){
			bars[i] = bars[i+1];
		}
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		
		//Checks if new Bar appears
		
		g.clearRect(0, 0, getWidth(), getHeight());
		
		if(periodPos >= barWidth + gapWidth){
			
			periodPos = 0;
			
			shiftBars();
			
			bars[bars.length-1] = (float) getUsage();

		}
		
		//Draws all bars
		
		for(int i=0;i<bars.length;i++){
			
			float bar = bars[i];
			
			int height = (int) ((getHeight()-15) * bar);
			
			
			g.setColor(getColor(bar));										
			
			g.fillRect((int) (i * (barWidth + gapWidth) + gapWidth - periodPos - 3), getHeight() - height, barWidth, height);
			
			System.out.println(height);
			
			g.setColor(Color.BLACK);
			String value = Float.toString(bar*100);
			
			if(value.length() >= 4){
				value = value.substring(0, 4);
			}
			
			g.drawString(value+"%", (int) (i * (barWidth + gapWidth) + gapWidth - periodPos), getHeight() - height - 5);
			//System.out.println("X: "+ (i * (barWidth + gapWidth) + barWidth + periodPos) + " Y: "+ (getHeight() - height));
			//System.out.println("WIDTH: "+barWidth+" HEIGHT: "+height);
		}
		
		//increases the position of the current period
		
		periodPos += (System.currentTimeMillis() - last) / 1000F * periodsPerSec * (barWidth + gapWidth);
		
		last = System.currentTimeMillis();
	}
	
	private Color getColor(double value){
		
		Color fade = Color.YELLOW;
		Color initial = Color.GREEN;
		
		if(value > 0.85){
			
			fade = Color.RED;;
			initial = Color.YELLOW;
			
		}
		
		double factor = 1 / 0.85 * value;
		
		int red = (int) ((fade.getRed() - initial.getRed()) * factor + initial.getRed());
		int green = (int) ((fade.getGreen() - initial.getGreen()) * factor + initial.getGreen());
		int blue = (int) ((fade.getBlue() - initial.getBlue()) * factor + initial.getBlue());
		
		Color c = new Color(toPositive(red),toPositive(green),toPositive(blue));
		
		return c;
	}
	
	private int toPositive(int value){
		if(value < 0){
			value *= -1;
		}
		
		return value;
	}
	
	/**
	 * 
	 * returns the current CPU usage
	 * 
	 * @return CPU usage
	 */
	private double getUsage(){
		
		OperatingSystemMXBean operatingSystem = ManagementFactory.getOperatingSystemMXBean();
		
		try {
			
			Method method = operatingSystem.getClass().getDeclaredMethod("getSystemCpuLoad");
		
			method.setAccessible(true);
				
			double value;

			value = (double) method.invoke(operatingSystem);
					
			return value;
					
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
		
		return -1;
	}
	
	private void initializeDimensions(){
		
		System.out.println("Width: "+this.getWidth());
		System.out.println("Height: "+this.getHeight());
		
		barWidth = (int) (this.getWidth() / barCount * (1-gap));
		gapWidth = (int) (this.getWidth() / barCount * gap);
		
	}
	
	/**
	 * 
	 * Increases available slots for Load Bars
	 * 
	 * Shifts the previous ones at the last few slots
	 * 
	 * @param barCount
	 */
	public void setBarCount(int barCount) {
		
		float[] tmp = new float[barCount];
		
		int difference = barCount - this.barCount;
		
		for(int i=0 ; i < this.barCount ; i++){
			
			tmp[i + difference] = bars[i];
			
		}
		
		this.barCount = barCount;
		
		bars = tmp;
		
		initializeDimensions();
		
	}

	public void setGap(float gap) {
	
		this.gap = gap;
		
		initializeDimensions();
		
	}

	public void setPeriodsPerSec(float periodsPerSec) {
	
		this.periodsPerSec = periodsPerSec;
		
	}
}
