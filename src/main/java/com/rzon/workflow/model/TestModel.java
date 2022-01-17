package com.rzon.workflow.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "\"test_model\"")
public class TestModel {

    String id;
    String name;
}
