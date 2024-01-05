package com.stonedt.dao;

import com.stonedt.entity.Synthesize;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SynthesizeDao {

	void insertSynthesize(Synthesize synthesize);

	Synthesize getNewSynthesize();

}
