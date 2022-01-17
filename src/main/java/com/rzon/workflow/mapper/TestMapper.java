package com.rzon.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rzon.workflow.model.TestModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface TestMapper extends BaseMapper<TestModel> {
}
