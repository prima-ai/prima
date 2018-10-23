package ai.jbon.jbon.data.dto;

public class ConnectionDTO {
	
	private final int id;
	private final int targetId;
	private final float weight;
	
	public ConnectionDTO(final int id, final int targetId, final float weight) {
		this.id = id;
		this.targetId = targetId;
		this.weight = weight;
	}

	public int getId() {
		return id;
	}

	public int getTargetId() {
		return targetId;
	}

	public float getWeight() {
		return weight;
	}
	
	@Override
	public String toString() {
		return 	"{" + this.id + "," +
				this.targetId + "," +
				this.weight + "}";
	}
}
