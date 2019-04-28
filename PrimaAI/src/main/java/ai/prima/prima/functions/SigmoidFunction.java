package ai.prima.prima.functions;

import java.util.List;

public class SigmoidFunction implements Function{

	private static final String TAG = "sigmoid";
	
	@Override
	public float apply(List<Float> values) {
		float x = 0;
		for(float value : values){
			x += value;
		}
		return 1.0f/(1.0f+(float)Math.exp(-x));
	}

	@Override
	public String getTag() {
		return TAG;
	}
}
