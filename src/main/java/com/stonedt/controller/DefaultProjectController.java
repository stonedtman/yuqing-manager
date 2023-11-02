package com.stonedt.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.stonedt.entity.DefaultOpinionCondition;
import com.stonedt.entity.DefaultProject;
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
    public ResultUtil getUserList(@RequestBody JSONObject dataObject) {

        String groupIdStr = dataObject.getString("groupId");
        String page = dataObject.getString("page");

        Long groupId = Long.valueOf(groupIdStr);
        Integer pageNum = Integer.valueOf(page);
        PageInfo<DefaultProject> pageInfo = defaultProjectService.getDefaultSolutionListByGroupId(groupId, pageNum);
        return ResultUtil.ok(pageInfo);
    }

    /**
     * 修改方案状态
     */
    @PostMapping({"/updateProjectStatus"})
    public ResultUtil updateProjectStatus(@RequestBody JSONObject dataObject) {
        Long projectId = dataObject.getLong("projectId");
        Integer status = dataObject.getInteger("status");
        defaultProjectService.updateProjectStatus(projectId, status);
        return ResultUtil.ok();
    }

    /**
     * 添加方案页面
     */
    @GetMapping("/addProjectPage")
    public ModelAndView addProjectPage(ModelAndView modelAndView) {
        modelAndView.setViewName("addProject");
        modelAndView.addObject("left", "defaultProject");
        return modelAndView;
    }

    /**
     * 添加方案
     */
    @PostMapping("/addProject")
    public ResultUtil addProject(@RequestBody JSONObject jsonObject) {

        return defaultProjectService.addProject(jsonObject);

    }

    /**
     * 修改方案页面
     */
    @GetMapping("/updateProjectPage")
    public ModelAndView updateProjectPage(ModelAndView modelAndView, @RequestParam Long projectId) {
        modelAndView.setViewName("updateProject");
        modelAndView.addObject("left", "defaultProject");
        modelAndView.addObject("projectId", String.valueOf(projectId));
        return modelAndView;
    }

    /**
     * 方案详情
     */
    @GetMapping("/getProjectDetail")
    public ResultUtil getProjectDetail(@RequestParam Long projectId) {
        return defaultProjectService.getProjectDetail(projectId);
    }

    /**
     * 修改方案
     */
    @PostMapping("/updateProject")
    public ResultUtil updateProject(@RequestBody JSONObject jsonObject) {
        return defaultProjectService.updateProject(jsonObject);
    }
}
