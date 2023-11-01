package com.stonedt.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.stonedt.entity.DefaultSolutionGroup;
import com.stonedt.service.DefaultProjectService;
import com.stonedt.util.ResultUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 文轩
 */
@RestController
@RequestMapping("/defaultGroup")
public class DefaultGroupController {

    private final DefaultProjectService defaultProjectService;

    public DefaultGroupController(DefaultProjectService defaultProjectService) {
        this.defaultProjectService = defaultProjectService;
    }

    @RequestMapping("/list")
    public ModelAndView list(ModelAndView modelAndView) {
        modelAndView.setViewName("defaultGroupList");
        modelAndView.addObject("left", "defaultGroup");
        return modelAndView;
    }

    @PostMapping("/getDefaultGroupList")
    public ResultUtil getDefaultGroupList(@RequestBody JSONObject dataObject) {

        String page = dataObject.getString("page");

        Integer pageNum = Integer.valueOf(page);
        PageInfo<DefaultSolutionGroup> pageInfo = defaultProjectService.getDefaultSolutionList(pageNum);
        return ResultUtil.ok(pageInfo);
    }

    @PostMapping("/updateGroupStatus")
    public ResultUtil updateGroupStatus(@RequestBody JSONObject dataObject) {

        String groupIdStr = dataObject.getString("groupId");
        String statusStr = dataObject.getString("status");

        Long groupId = Long.valueOf(groupIdStr);
        Integer status = Integer.valueOf(statusStr);
        defaultProjectService.updateGroupStatus(groupId, status);
        return ResultUtil.ok();
    }

    /**
     * 新增方案组页面
     */
    @RequestMapping("/addGroupPage")
    public ModelAndView addDefaultGroup(ModelAndView modelAndView) {
        modelAndView.setViewName("addDefaultGroup");
        modelAndView.addObject("left", "defaultGroup");
        return modelAndView;
    }

    /**
     * 新增方案组
     */
    @PostMapping("/addGroup")
    public ResultUtil addDefaultGroup(@RequestBody JSONObject dataObject) {

        String groupName = dataObject.getString("groupName");

       return defaultProjectService.addDefaultGroup(groupName);

    }

    /**
     * 修改方案组页面
     */
    @RequestMapping("/updateGroupPage")
    public ModelAndView updateGroupPage(@RequestParam Integer groupId, ModelAndView modelAndView) {
        modelAndView.setViewName("updateDefaultGroup");

        modelAndView.addObject("left", "defaultGroup");
        DefaultSolutionGroup groupById = defaultProjectService.getGroupById(groupId);
        System.out.println(groupById);
        modelAndView.addObject("groupId", String.valueOf(groupById.getGroup_id()));
        modelAndView.addObject("groupName", groupById.getGroup_name());
        return modelAndView;
    }

    /**
     * 修改方案组
     */
    @PostMapping("/updateGroup")
    public ResultUtil updateGroup(@RequestBody JSONObject dataObject) {

        String groupIdStr = dataObject.getString("groupId");
        String groupName = dataObject.getString("groupName");

        Long groupId = Long.valueOf(groupIdStr);
        return defaultProjectService.updateGroup(groupId, groupName);

    }
}
