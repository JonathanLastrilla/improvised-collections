/*
 * 
 */
package jon.dev.collections.improvised;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Inspired by dict collection from python language. This attempts to provide
 * shorthand functions for ease in use.
 * @author Jonathan Lastrilla
 * @since 0.1
 */
public abstract class Dict {

    public static Dict createNew() {
        return new Parent();
    }

    private static Dict newSubParent(Object name) {
        return new Parent(name);
    }

    private static Dict newLeaf(Object name, Object value) {
        return new Leaf(value, name);

    }

    public void forEach(Consumer<Dict> consumer) {
        throw new UnsupportedOperationException("foreach");
    }

    public Dict add(Object name, Object value) {
        throw new UnsupportedOperationException("add name value");
    }

    public Dict add(Object name) {
        throw new UnsupportedOperationException("add parent");
    }

    public Dict get(Object name) {
        throw new UnsupportedOperationException("");
    }

    public Dict get(Object... name) {
        throw new UnsupportedOperationException("get with names");
    }

    public void remove(Object name) {
        throw new UnsupportedOperationException("remove");
    }

    public void setValue(Object o) {
        throw new UnsupportedOperationException("set value");
    }

    public abstract boolean isLeaf();

    public abstract Object getName();

    public <T> T getValue(Class<T> clazz, Object name) {
        return (T) get(name).getValue();
    }

    public <T> T getValue(Class<T> clazz, Object... names) {
        return (T) get(names).getValue();
    }

    protected abstract Object getValue();

    static class Parent extends Dict {

        private List<Dict> children;
        private Object name;

        public Parent() {
            this.children = new ArrayList<>();
        }

        public Parent(Object name) {
            this();
            this.name = name;
        }

        @Override
        public Object getName() {
            return name;
        }

        @Override
        protected Object getValue() {
            throw new UnsupportedOperationException(String.format("unable to get individual value from parent node '%s'", getName()));
        }

        @Override
        public Dict get(Object name) {
            return children.stream().filter(e -> e.getName().equals(name)).findFirst().orElseGet(() -> null);
        }

        @Override
        public Dict get(Object... name) {
            Dict tmp = null;
            List<String> path = new ArrayList<>();
            for (Object local : name) {
                tmp = tmp != null ? tmp.get(local) : get(local);
                path.add(local.toString());
                if (tmp == null) {
                    throw new NullPointerException("Node '" + String.join(".", path) + "' is null");
                }

            }
            return tmp;
        }

        @Override
        public Dict add(Object name, Object value) {
            Dict newLeaf = Dict.newLeaf(name, value);
            this.children.add(newLeaf);
            return newLeaf;
        }

        @Override
        public Dict add(Object name) {
            Dict newParent = Dict.newSubParent(name);
            this.children.add(newParent);
            return newParent;
        }

        @Override
        public void remove(Object name) {
            Dict tr = children.stream().filter(e -> e.getName() == name).findFirst().orElseGet(() -> null);
            if (tr != null) {
                children.remove(tr);
            }
        }

        @Override
        public void forEach(Consumer<Dict> consumer) {
            children.stream().forEach(consumer);
        }

        @Override
        public boolean isLeaf() {
            return false;
        }
    }

    static class Leaf extends Dict {

        private Object value;
        private final Object name;

        public Leaf(Object value, Object name) {
            this.value = value;
            this.name = name;
        }

        @Override
        public void setValue(Object value) {
            this.value = value;
        }

        @Override
        protected Object getValue() {
            return value;
        }

        @Override
        public Object getName() {
            return name;
        }

        @Override
        public boolean isLeaf() {
            return true;
        }
    }
}
