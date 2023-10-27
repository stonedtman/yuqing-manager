package com.stonedt.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.stonedt.service.DefaultProjectService;
import com.stonedt.util.ResultUtil;
import com.stonedt.vo.DefaultProjectVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认方案配置管理
 *
 * @author 文轩
 */
@RestController
@RequestMapping("/defaultProject")
public class DefaultProjectController {

    private final DefaultProjectService defaultProjectService;

    public DefaultProjectController(DefaultProjectService defaultProjectService) {
        this.defaultProjectService = defaultProjectService;
    }

    @RequestMapping("/list")
    public ModelAndView list(ModelAndView modelAndView) {
        modelAndView.setViewName("defaultProjectList");
        modelAndView.addObject("left", "defaultProject");
        return modelAndView;
    }

    @GetMapping("getDefaultGroupList")
    public ResultUtil getDefaultGroupList() {
        return ResultUtil.ok(defaultProjectService.getDefaultSolutionGroupList());
    }


    @PostMapping({"/getDefaultProjectList"})
    @ResponseBody
    public ResultUtil getUserList(@RequestBody JSONObject dataObject) {

        String groupIdStr = dataObject.getString("groupId");

        Long groupId = Long.valueOf(groupIdStr);
        List<DefaultProjectVO> defaultProjectListPage = defaultProjectService.getDefaultSolutionListByGroupId(groupId);
        return ResultUtil.ok(defaultProjectListPage);
    }

}
