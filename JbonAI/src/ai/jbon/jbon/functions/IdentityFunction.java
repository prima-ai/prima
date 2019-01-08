package ai.jbon.jbon.functions;

import java.util.List;

public class IdentityFunction implements Function{

	private static final String TAG = "identity";
	
	@Override
	public float apply(List<Float> values) {
		float x = 0;
		for(float value : values){
			x += value;
		}
		return x;
	}

	@Override
	public String getTag() {
		return TAG;
	}

}
