package com.rzon.workflow.controller;

import com.rzon.workflow.utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wfInstance")
public class WorkFlowInstanceController {

    @PostMapping("/works ")
    public Result test() {

        return Result.ok();
    }

}
