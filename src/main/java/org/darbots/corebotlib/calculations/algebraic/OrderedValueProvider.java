package org.darbots.corebotlib.calculations.algebraic;

public interface OrderedValueProvider {
    boolean orderIncremental();
    double valueAt(double independentVar);
}
