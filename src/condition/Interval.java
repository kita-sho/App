package condition;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: Interval.class */
public class Interval<Value> {
    private ValueHolder<Value> start;
    private Function<Value, Boolean> stop;
    private Function<Value, Value> step;

    public Interval(Value value, Function<Value, Boolean> function, Function<Value, Value> function2) {
        this.start = null;
        this.stop = null;
        this.step = null;
        this.start = new ValueHolder<>(value);
        this.stop = function;
        this.step = function2;
    }

    public void _break_() {
        throw new IntervalException(_break_message(), this);
    }

    public String _break_message() {
        return getClass().getName() + "._break_()";
    }

    public void _return_() {
        throw new IntervalException(_return_message(), this);
    }

    public String _return_message() {
        return getClass().getName() + "._return_()";
    }

    public void forEach() {
        forEach(obj -> {
        });
    }

    public void forEach(Consumer<Value> consumer) {
        forEach((obj, interval) -> {
            consumer.accept(obj);
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void forEach(BiConsumer<Value, Interval<Value>> biConsumer) {
        try {
            ValueHolder valueHolder = new ValueHolder(this.start.get());
            valueHolder.get();
            while (((Boolean) this.stop.apply(valueHolder.get())).booleanValue()) {
                biConsumer.accept(valueHolder.get(), this);
                valueHolder.set(this.step.apply(valueHolder.get()));
            }
        } catch (IntervalException e) {
            e.isBreakFalse(() -> {
                throw e;
            });
        }
    }

    public String toString() {
        Class<?> cls = getClass();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(cls.getName());
        stringBuffer.append("[");
        stringBuffer.append(this.start.toString());
        stringBuffer.append(",");
        stringBuffer.append(this.stop.toString());
        stringBuffer.append(",");
        stringBuffer.append(this.step.toString());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
