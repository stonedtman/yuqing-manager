package com.stonedt.controller;

import com.github.pagehelper.PageInfo;
import com.stonedt.service.LogService;
import com.stonedt.service.UserService;
import com.stonedt.util.ResultUtil;
import com.stonedt.vo.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author 文轩
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;

    private final LogService logService;

    public HomeController(UserService userService,
                          LogService logService) {
        this.userService = userService;
        this.logService = logService;
    }


    @RequestMapping("")
    public ModelAndView home(ModelAndView modelAndView) {

        modelAndView.setViewName("home");
        modelAndView.addObject("left", "home");
        return modelAndView;
    }


    /**
     * 用户增长趋势图
     * @param days 天数
     * @return
     */
    @GetMapping("/userTrendChart")
    public ResultUtil<List<UserTrendChartVO>> userTrendChart(@RequestParam(required = true,defaultValue = "7") Integer days) {
        return userService.getUserTrend(days);
    }

    /**
     * 用户使用排名
     */
    @GetMapping("/userUseRanking")
    public ResultUtil<PageInfo<UseRankVO>> userRanking(@RequestParam(required = false) String username,
                                                       @RequestParam(required = true) Integer days,
                                                       @RequestParam(defaultValue = "1") Integer pageNum,
                                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                                       @RequestParam(required = false) boolean isASC) {

        return userService.getUserUseRanking(username, days, pageNum, pageSize, isASC);
    }

    /**
     * 系统热门模块排名
     */
    @GetMapping("/systemHotModuleRanking")
    public ResultUtil<List<SystemHotModuleVO>> systemHotModuleRanking(@RequestParam(required = true) Integer days,
                                                                          @RequestParam(required = false,defaultValue = "1") Integer orderType,
                                                                          @RequestParam(required = false,defaultValue = "10") Integer size) {

        return logService.getSystemHotModuleRanking(days, orderType,size);
    }


    /**
     * 系统模块下拉列表
     */
    @GetMapping("/systemModuleList")
    public ResultUtil<List<String>> systemModuleList() {
        return logService.getSystemModuleList();
    }

    /**
     * 模块使用情况图表
     */
    @GetMapping("/moduleUseChart")
    public ResultUtil<List<ModelUseChartVO>> moduleUseChart(@RequestParam(required = true) Integer userId,
                                                            @RequestParam(required = false) String module){

        return logService.getModuleUseChart(userId,module);
    }

    /**
     * 用户模块使用记录
     */
    @GetMapping("/userModuleUseRecord")
    public ResultUtil<PageInfo<UserUseRecord>> userModuleUseRecord(@RequestParam(required = true) Integer userId,
                                                                   @RequestParam(required = false) Integer days,
                                                                   @RequestParam(required = false) Integer pageNum,
                                                                   @RequestParam(required = false) Integer pageSize) {

        return userService.getUserModuleUseRecord(userId, days, pageNum, pageSize);
    }

    /**
     * 根据系统模块获取子模块下拉列表
     */
    @GetMapping("/systemSubModuleList")
    public ResultUtil<List<String>> systemSubModuleList(@RequestParam(required = true) String module) {
        return ResultUtil.ok(userService.getSystemSubModuleList(module));
    }

    /**
     * 子模块使用情况图表
     */
    @GetMapping("/subModuleUseChart")
    public ResultUtil<List<ModelUseChartVO>> subModuleUseChart(@RequestParam(required = true) String module,
                                                               @RequestParam(required = false) String subModule){

        return ResultUtil.ok(userService.getSubModuleUseChart(module,subModule));
    }

    /**
     * 模块使用记录
     */
    @GetMapping("/moduleUseRecord")
    public ResultUtil<List<ModuleUseRecord>> moduleUseRecord(@RequestParam(required = true) String module,
                                                           @RequestParam(required = false) Integer days,
                                                             @RequestParam(required = false) Integer pageNum,
                                                             @RequestParam(required = false) Integer pageSize) {

        return ResultUtil.ok(userService.getModuleUseRecord(module, days, pageNum, pageSize));
    }


}
