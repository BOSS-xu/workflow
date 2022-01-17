package com.rzon.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rzon.workflow.model.WorkFlowInstance;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkFlowInstanceMapper extends BaseMapper<WorkFlowInstance> {
}
