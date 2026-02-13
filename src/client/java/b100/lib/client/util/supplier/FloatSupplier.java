package b100.lib.client.util.supplier;

import java.util.function.Supplier;

@FunctionalInterface
public interface FloatSupplier extends Supplier<Float> {
	
	public float getFloat();
	
	@Deprecated
	@Override
	default Float get() {
		return getFloat();
	}
	
}
