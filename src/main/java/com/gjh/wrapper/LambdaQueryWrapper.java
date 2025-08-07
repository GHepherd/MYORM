package com.gjh.wrapper;


import com.gjh.function.SFunction;
import com.gjh.utils.LambdaUtils;

import java.util.ArrayList;
import java.util.List;

public class LambdaQueryWrapper<T> {

    private final List<Condition<T>> conditions = new ArrayList<>();

    private static class Condition<T>{
        private SFunction<T,?> field;
        private Object value;
        private String op;

        public Condition(SFunction<T,?> field, Object value, String op){
            this.field = field;
            this.value = value;
            this.op = op;
        }
    }

    public <V> LambdaQueryWrapper<T> eq(SFunction<T,V> field, V value){
        conditions.add(new Condition<>(field, value, "="));
        return this;
    }

    public <V> LambdaQueryWrapper<T> ne(SFunction<T,V> field, V value){
        conditions.add(new Condition<>(field, value, "!="));
        return this;
    }

    public <V> LambdaQueryWrapper<T> like(SFunction<T,V> field, V value){
        conditions.add(new Condition<>(field, "%"+value+"%", "like"));
        return this;
    }

    public String getSqlCondition() {
        StringBuilder sb = new StringBuilder(" WHERE ");
        for (int i = 0; i < conditions.size(); i++) {
            Condition c = conditions.get(i);
            if (i > 0) sb.append(" AND ");
            sb.append(getColumnName(c.field))  // 简化：实际应解析字段名，这里用占位符
                    .append(" ")
                    .append(c.op)
                    .append(" ")
                    .append("?");  // 实际应绑定具体值
        }
        return sb.toString();
    }


    private String getColumnName(SFunction<T, ?> columnFunc) {
        return LambdaUtils.getFieldName(columnFunc);
    }


    public Object[] getConditionValues() {
        return conditions.stream().map(c -> c.value).toArray();
    }


}
