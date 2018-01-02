package TextAdventure;

import java.util.Map.Entry;

public class SimpleEntry<T1, T2> implements Entry<T1, T2> {
	
	private T1 key;
	private T2 value;

	public T1 getKey() {
		return this.key;
	}

	public T2 getValue() {
		return this.value;
	}
	
	public SimpleEntry(T1 K, T2 V) {
		this.key = K;
		this.value = V;
	}

	public T2 setValue(T2 value) {
		return null;
	}

}
