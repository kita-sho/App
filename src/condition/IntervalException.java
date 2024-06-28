package condition;

import java.util.function.Consumer;

/* loaded from: IntervalException.class */
public class IntervalException extends RuntimeException {
    private Interval<?> interval;

    public IntervalException(String str, Interval<?> interval) {
        super(str);
        this.interval = null;
        this.interval = interval;
    }

    public Boolean isBreak() {
        return Boolean.valueOf(getMessage().equals(this.interval._break_message()));
    }

    public void isBreakFalse(Runnable runnable) {
        isBreakThenElse(() -> {
        }, runnable);
    }

    public void isBreakFalse(Consumer<Interval<?>> consumer) {
        isBreakThenElse(interval -> {
        }, consumer);
    }

    public void isBreakThenElse(Runnable runnable, Runnable runnable2) {
        isBreakThenElse(interval -> {
            runnable.run();
        }, interval2 -> {
            runnable2.run();
        });
    }

    public void isBreakThenElse(Consumer<Interval<?>> consumer, Consumer<Interval<?>> consumer2) {
        new Condition(this::isBreak).ifThenElse(() -> {
            consumer.accept(this.interval);
        }, () -> {
            consumer2.accept(this.interval);
        });
    }

    public void isBreakTrue(Runnable runnable) {
        isBreakThenElse(runnable, () -> {
        });
    }

    public void isBreakTrue(Consumer<Interval<?>> consumer) {
        isBreakThenElse(consumer, interval -> {
        });
    }

    public Boolean isReturn() {
        return Boolean.valueOf(getMessage().equals(this.interval._return_message()));
    }

    public void isReturnFalse(Runnable runnable) {
        isReturnThenElse(() -> {
        }, runnable);
    }

    public void isReturnFalse(Consumer<Interval<?>> consumer) {
        isReturnThenElse(interval -> {
        }, consumer);
    }

    public void isReturnThenElse(Runnable runnable, Runnable runnable2) {
        isReturnThenElse(interval2 -> {
            runnable.run();
        }, interval22 -> {
            runnable2.run();
        });
    }

    public void isReturnThenElse(Consumer<Interval<?>> consumer, Consumer<Interval<?>> consumer2) {
        new Condition(this::isReturn).ifThenElse(() -> {
            consumer.accept(this.interval);
        }, () -> {
            consumer2.accept(this.interval);
        });
    }

    public void isReturnTrue(Runnable runnable) {
        isReturnThenElse(runnable, () -> {
        });
    }

    public void isReturnTrue(Consumer<Interval<?>> consumer) {
        isReturnThenElse(consumer, interval -> {
        });
    }

    @Override // java.lang.Throwable
    public String toString() {
        Class<?> cls = getClass();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(cls.getName());
        stringBuffer.append("[");
        stringBuffer.append(getMessage());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
