package com.rzon.workflow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rzon.workflow.mapper.WorkFlowInstanceMapper;
import com.rzon.workflow.model.WorkFlowInstance;
import com.rzon.workflow.service.WorkFlowInstanceService;
import org.springframework.stereotype.Service;

@Service
public class WorkFlowInstanceServiceImpl extends ServiceImpl<WorkFlowInstanceMapper, WorkFlowInstance> implements WorkFlowInstanceService {
}
