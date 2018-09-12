package ai.jbon.jbon.functions;

import java.util.Arrays;
import java.util.List;

public class SigmoidFunction extends Function{

	@Override
	public float getOutput(List<Float> values) {
		
		float x = 0;
		
		for(float value : values){
			x += value;
		}
		
		return 1.0f/(1.0f+(float)Math.exp(-x));
	}

}
