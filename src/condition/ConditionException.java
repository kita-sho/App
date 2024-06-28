package condition;

import java.util.function.Consumer;

/* loaded from: ConditionException.class */
public class ConditionException extends RuntimeException {

    /* renamed from: condition, reason: collision with root package name */
    private Condition f0condition;

    public ConditionException(String str, Condition condition2) {
        super(str);
        this.f0condition = null;
        this.f0condition = condition2;
    }

    public Boolean isBreak() {
        return Boolean.valueOf(getMessage().equals(this.f0condition._break_message()));
    }

    public void isBreakFalse(Runnable runnable) {
        isBreakThenElse(() -> {
        }, runnable);
    }

    public void isBreakFalse(Consumer<Condition> consumer) {
        isBreakThenElse(condition2 -> {
        }, consumer);
    }

    public void isBreakThenElse(Runnable runnable, Runnable runnable2) {
        isBreakThenElse(condition2 -> {
            runnable.run();
        }, condition22 -> {
            runnable2.run();
        });
    }

    public void isBreakThenElse(Consumer<Condition> consumer, Consumer<Condition> consumer2) {
        new Condition(this::isBreak).ifThenElse(() -> {
            consumer.accept(this.f0condition);
        }, () -> {
            consumer2.accept(this.f0condition);
        });
    }

    public void isBreakTrue(Runnable runnable) {
        isBreakThenElse(runnable, () -> {
        });
    }

    public void isBreakTrue(Consumer<Condition> consumer) {
        isBreakThenElse(consumer, condition2 -> {
        });
    }

    public Boolean isReturn() {
        return Boolean.valueOf(getMessage().equals(this.f0condition._return_message()));
    }

    public void isReturnFalse(Runnable runnable) {
        isReturnThenElse(() -> {
        }, runnable);
    }

    public void isReturnFalse(Consumer<Condition> consumer) {
        isReturnThenElse(condition2 -> {
        }, consumer);
    }

    public void isReturnThenElse(Runnable runnable, Runnable runnable2) {
        isReturnThenElse(condition22 -> {
            runnable.run();
        }, condition222 -> {
            runnable2.run();
        });
    }

    public void isReturnThenElse(Consumer<Condition> consumer, Consumer<Condition> consumer2) {
        new Condition(this::isReturn).ifThenElse(() -> {
            consumer.accept(this.f0condition);
        }, () -> {
            consumer2.accept(this.f0condition);
        });
    }

    public void isReturnTrue(Runnable runnable) {
        isReturnThenElse(runnable, () -> {
        });
    }

    public void isReturnTrue(Consumer<Condition> consumer) {
        isReturnThenElse(consumer, condition2 -> {
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
