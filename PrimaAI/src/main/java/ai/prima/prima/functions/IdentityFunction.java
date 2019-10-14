package ai.prima.prima.functions;

import java.util.List;

public class IdentityFunction implements Function{

	@Override
	public float apply(List<Float> values) {
		float x = 0;
		for(float value : values){
			x += value;
		}
		return x;
	}
}
