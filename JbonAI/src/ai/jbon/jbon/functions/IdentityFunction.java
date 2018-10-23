package ai.jbon.jbon.functions;

import java.util.List;

public class IdentityFunction extends Function{

	@Override
	public float getOutput(List<Float> values) {
		float x = 0;
		for(float value : values){
			x += value;
		}
		return x;
	}

}
