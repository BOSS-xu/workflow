package com.rzon.workflow.controller;

import com.rzon.workflow.model.TestModel;
import com.rzon.workflow.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class Test {

    @Autowired
    TestService testService;

    /**
     * @api {get} /Test/{test} 测试API
     * @apiPermission 仓库管理
     * @apiExample url示例
     *  http://localhost:8089/
     * @apiGroup Test
     * @apiDescription 测试API
     * @apiParam {String} testId1 参数1
     * @apiParam {String} testId2 参数2
     * @apiSuccessExample 正常返回
     *  HTTP/1.1 202
     * @apiErrorExample 错误返回
     * HTTP/1.1 404
     * {
     *      "code": "xxx",
     *      "message": "xxxx"
     * }
     */
    @GetMapping("/test")
    public List<TestModel> test() {
        System.out.println("tttttttttttt");

        List<TestModel> list = testService.list();
        return list;
    }
}
