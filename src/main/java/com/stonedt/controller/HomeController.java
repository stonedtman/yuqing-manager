package com.stonedt.controller;

import com.github.pagehelper.PageInfo;
import com.stonedt.service.UserService;
import com.stonedt.util.ResultUtil;
import com.stonedt.vo.UseRankVO;
import com.stonedt.vo.UserTrendChartVO;
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

    public HomeController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping("")
    public ModelAndView home(ModelAndView modelAndView) {

        modelAndView.setViewName("home");
        modelAndView.addObject("left", "home");
        return modelAndView;
    }


    @GetMapping("userTrendChart")
    public ResultUtil<List<UserTrendChartVO>> userTrendChart(@RequestParam(required = true) Integer days) {
        return userService.getUserTrend(days);
    }

    /**
     * 用户使用排名
     */
    @GetMapping("userUseRanking")
    public ResultUtil<PageInfo<UseRankVO>> userRanking(@RequestParam(required = false) String username,
                                                       @RequestParam(required = true) Integer days,
                                                       @RequestParam(defaultValue = "1") Integer pageNum,
                                                       @RequestParam(defaultValue = "10") Integer pageSize) {

        return userService.getUserUseRanking(username, days, pageNum, pageSize);
    }


}
