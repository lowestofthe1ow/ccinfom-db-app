package BocchiTheGUI.interfaces;

import java.util.List;
import java.util.function.Function;

public interface DataLoadable {
    public abstract void loadData(Function<Object, List<Object[]>> source);
}