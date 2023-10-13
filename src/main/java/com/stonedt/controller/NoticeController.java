package com.stonedt.controller;

import cn.hutool.http.useragent.UserAgentUtil;
import com.github.pagehelper.PageInfo;
import com.stonedt.dao.NoticeMapper;
import com.stonedt.entity.Notice;
import com.stonedt.entity.UserEntity;
import com.stonedt.service.NoticeService;
import com.stonedt.util.ResultUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 文轩
 * @Description 公告管理控制层
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }


    /**
     * 公告列表页面
     */
    @RequestMapping(value = "/list")
    public ModelAndView noticeList() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("noticeList");
        mv.addObject("left", "notice");
        return mv;
    }

    /**
     * 公告列表
     */
    @GetMapping(value = "/getNoticeList")
    public ResultUtil getNoticeList(@RequestParam(defaultValue = "1") Integer pageNum) {
        PageInfo<Notice> pageInfo = noticeService.getNoticeList(pageNum);
        return ResultUtil.ok(pageInfo);
    }

    /**
     * 发布公告
     */
    @PostMapping(value = "/addNotice")
    public ResultUtil addNotice(@RequestBody Notice notice, HttpServletRequest request) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("User");
        return noticeService.addNotice(user,notice);

    }

    /**
     * 获取最新公告
     */
    @GetMapping(value = "/getNewNotice")
    public ResultUtil getNewNotice() {
        return noticeService.getNewNotice();
    }

    /**
     * 下架公告
     */
    @GetMapping(value = "/deleteNotice")
    public ResultUtil deleteNotice(@RequestParam Integer id) {
        return noticeService.deleteNotice(id);
    }

    /**
     * 上架公告
     */
    @GetMapping(value = "/upNotice")
    public ResultUtil upNotice(@RequestParam Integer id) {
        return noticeService.upNotice(id);
    }

    /**
     * 发布公告页面
     */
    @RequestMapping(value = "/addNoticePage")
    public ModelAndView addNoticePage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("addNotice");
        mv.addObject("left", "notice");
        return mv;
    }

}
