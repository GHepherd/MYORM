package com.gjh.utils;

import com.gjh.function.SFunction;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

public class LambdaUtils {

    public static <T> String getFieldName(SFunction<T, ?> field) {
        try {
            Method writeReplaceMethod = field.getClass().getDeclaredMethod("writeReplace");
            writeReplaceMethod.setAccessible(true);

            SerializedLambda serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(field);
            String methodName = serializedLambda.getImplMethodName();
            return methodName.substring(3);

        } catch (Exception e) {
            System.out.println("getFieldName error");
        }
        return null;
    }
}
