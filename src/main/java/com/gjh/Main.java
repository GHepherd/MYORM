package com.gjh;

import com.gjh.bean.User;
import com.gjh.mapper.UserMapper;
import com.gjh.wrapper.LambdaQueryWrapper;

public class Main {
    public static void main(String[] args) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(User::getName, "张")  // 模糊查询 name 包含 "张"
                .eq(User::getAge, 20);     // 年龄 > 20

        // 打印模拟生成的 SQL 条件（实际项目中用于构造真正 SQL 或 ORM 查询）
        String sqlCondition = wrapper.getSqlCondition();
        Object[] values = wrapper.getConditionValues();
        UserMapper mapper = new UserMapper();
        System.out.println(mapper.query(wrapper));
        System.out.println("条件值: " + String.join(", ", java.util.Arrays.stream(values).map(Object::toString).toArray(String[]::new)));
    }
}