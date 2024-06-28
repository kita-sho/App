package condition;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

/* loaded from: ValueHolder.class */
public class ValueHolder<Value> {
    private final AtomicReference<Value> value = new AtomicReference<>();

    public ValueHolder() {
    }

    public ValueHolder(Value value) {
        this.value.set(value);
    }

    public synchronized Value get() {
        return this.value.get();
    }

    public synchronized Value getAndSetDo(Function<Value, Value> function) {
        Value value = get();
        set(function.apply(value));
        return value;
    }

    public synchronized Value set(Value value) {
        Value value2 = get();
        this.value.set(value);
        return value2;
    }

    public synchronized Value setDo(Function<Value, Value> function) {
        return set(function.apply(get()));
    }

    public synchronized String toString() {
        Class<?> cls = getClass();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(cls.getName());
        stringBuffer.append("[");
        stringBuffer.append(get().toString());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
