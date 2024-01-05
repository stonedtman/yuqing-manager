package com.stonedt.impl;

import com.stonedt.dao.SynthesizeDao;
import com.stonedt.entity.Synthesize;
import com.stonedt.service.SynthesizeService;
import com.stonedt.util.ResultUtil;
import org.springframework.stereotype.Service;

/**
 * @author 文轩
 */
@Service
public class SynthesizeServiceImpl implements SynthesizeService {

    private final SynthesizeDao synthesizeDao;

    public SynthesizeServiceImpl(SynthesizeDao synthesizeDao) {
        this.synthesizeDao = synthesizeDao;
    }

    @Override
    public ResultUtil getNewSynthesize() {
        Synthesize synthesize = null;
        try {
            synthesize = synthesizeDao.getNewSynthesize();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.build(500,"查询失败");
        }
        return ResultUtil.ok(synthesize);
    }
}
