package BocchiTheGUI.interfaces;

import java.util.List;
import java.util.function.BiFunction;

public interface DataLoadable {
    public abstract void loadData(BiFunction<Object, Object[], List<Object[]>> source);

    public abstract boolean allowEmptyDatasets();

    public abstract String getLoadFailureMessage();
}