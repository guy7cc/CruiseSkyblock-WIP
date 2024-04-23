package io.github.guy7cc.cruiseskyblock.core.system;

import java.util.Arrays;
import java.util.function.BiConsumer;

public class ElementalVector implements Cloneable {
    public double[] values = new double[Element.values().length];

    public double get(Element element){
        return values[element.ordinal()];
    }

    public void set(Element element, double value){
        values[element.ordinal()] = value;
    }

    public double sum(){
        return Arrays.stream(values).sum();
    }

    public static ElementalVector zero(){
        ElementalVector vector = new ElementalVector();
        Arrays.fill(vector.values, 0D);
        return vector;
    }

    public static ElementalVector one(){
        ElementalVector vector = new ElementalVector();
        Arrays.fill(vector.values, 1D);
        return vector;
    }

    @Override
    public Object clone() {
        ElementalVector clone = new ElementalVector();
        for(int i = 0; i < clone.values.length; ++i){
            clone.values[i] = values[i];
        }
        return clone;
    }
}
