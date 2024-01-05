package com.stonedt.controller;

import com.stonedt.service.SynthesizeService;
import com.stonedt.util.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 文轩
 */
@RestController
@RequestMapping("/synthesize")
public class SynthesizeController {

	private final SynthesizeService synthesizeService;

	public SynthesizeController(SynthesizeService synthesizeService) {
		this.synthesizeService = synthesizeService;
	}

	@GetMapping(value = "/getNewSynthesize")
	public ResultUtil displayBoardList() {
		return synthesizeService.getNewSynthesize();
	}

}
