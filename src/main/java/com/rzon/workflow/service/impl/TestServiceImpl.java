package com.rzon.workflow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rzon.workflow.mapper.TestMapper;
import com.rzon.workflow.model.TestModel;
import com.rzon.workflow.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, TestModel> implements TestService {
}
