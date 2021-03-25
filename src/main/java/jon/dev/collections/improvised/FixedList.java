/*
 * COPYRIGHT LASTRILLA, Jonathan
 */
package jon.dev.collections.improvised;

import java.util.ArrayList;

/**
 *
 * @author Jonathan Lastrilla
 */
public final class FixedList<T> extends ArrayList<T> {

    int limit = -1;

    public FixedList(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean add(T e) {
        return size() < limit ? super.add(e) : false;
    }

}
