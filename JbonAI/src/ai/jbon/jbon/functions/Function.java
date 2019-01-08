package ai.jbon.jbon.functions;

import java.util.List;

public interface Function {
	
	public abstract float apply(List<Float> values);
	
	public abstract String getTag();
}
