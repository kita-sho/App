package condition;

import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: Condition.class */
public class Condition {

    /* renamed from: condition, reason: collision with root package name */
    private Supplier<Boolean> f0condition;

    public Condition(Supplier<Boolean> supplier) {
        this.f0condition = null;
        this.f0condition = supplier;
    }

    public void _break_() {
        throw new ConditionException(_break_message(), this);
    }

    public String _break_message() {
        return getClass().getName() + "._break_()";
    }

    public void _return_() {
        throw new ConditionException(_return_message(), this);
    }

    public String _return_message() {
        return getClass().getName() + "._return_()";
    }

    public void ifFalse(Runnable runnable) {
        ifThenElse(() -> {
        }, runnable);
    }

    public void ifFalse(Consumer<Condition> consumer) {
        ifThenElse(condition2 -> {
        }, consumer);
    }

    public void ifThenElse(Runnable runnable, Runnable runnable2) {
        ifThenElse(condition2 -> {
            runnable.run();
        }, condition22 -> {
            runnable2.run();
        });
    }

    public void ifThenElse(Consumer<Condition> consumer, Consumer<Condition> consumer2) {
        if (this.f0condition.get().booleanValue()) {
            consumer.accept(this);
        } else {
            consumer2.accept(this);
        }
    }

    public void ifTrue(Runnable runnable) {
        ifThenElse(runnable, () -> {
        });
    }

    public void ifTrue(Consumer<Condition> consumer) {
        ifThenElse(consumer, condition2 -> {
        });
    }

    public String toString() {
        Class<?> cls = getClass();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(cls.getName());
        stringBuffer.append("[");
        stringBuffer.append(this.f0condition.toString());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    public void whileTrue(Runnable runnable) {
        whilePredicate(() -> {
            return this.f0condition.get();
        }, runnable);
    }

    public void whileTrue(Consumer<Condition> consumer) {
        whilePredicate(() -> {
            return this.f0condition.get();
        }, consumer);
    }

    public void whileFalse(Runnable runnable) {
        whilePredicate(() -> {
            return Boolean.valueOf(!this.f0condition.get().booleanValue());
        }, runnable);
    }

    public void whileFalse(Consumer<Condition> consumer) {
        whilePredicate(() -> {
            return Boolean.valueOf(!this.f0condition.get().booleanValue());
        }, consumer);
    }

    private void whilePredicate(Supplier<Boolean> supplier, Runnable runnable) {
        whilePredicate(supplier, condition22 -> {
            runnable.run();
        });
    }

    private void whilePredicate(Supplier<Boolean> supplier, Consumer<Condition> consumer) {
        while (supplier.get().booleanValue()) {
            try {
                consumer.accept(this);
            } catch (ConditionException e) {
                e.isBreakFalse(() -> {
                    throw e;
                });
                return;
            }
        }
    }
}
