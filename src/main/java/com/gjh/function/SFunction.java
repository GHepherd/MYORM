package com.gjh.function;

import java.io.Serializable;
import java.util.function.Function;

public interface SFunction<S,R> extends Function<S,R>, Serializable {
}
