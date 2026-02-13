package b100.lib.client.util.supplier;

import java.util.function.Supplier;

@FunctionalInterface
public interface DoubleSupplier extends Supplier<Double> {
	
	public double getDouble();
	
	@Deprecated
	@Override
	default Double get() {
		return getDouble();
	}
	
}
