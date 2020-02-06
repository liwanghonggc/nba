package com.lwh.elasticsearch.nba.dao;

import com.lwh.elasticsearch.nba.model.NBAPlayer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lwh
 * @date 2020-02-06
 * @desp
 */
@Mapper
public interface NBAPlayerDao {

    @Select("select * from nba_player")
    List<NBAPlayer> selectAll();
}
