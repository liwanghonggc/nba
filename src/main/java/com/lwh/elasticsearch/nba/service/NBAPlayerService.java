package com.lwh.elasticsearch.nba.service;

import com.lwh.elasticsearch.nba.model.NBAPlayer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author lwh
 * @date 2020-02-06
 * @desp
 */
public interface NBAPlayerService {

    /**
     * 像ES中增加一个文档
     */
    boolean addPlayer(NBAPlayer nbaPlayer, String id) throws IOException;

    /**
     * 查询文档
     */
    Map<String, Object> getPlayer(String id) throws IOException;

    /**
     * 更新文档
     */
    boolean updatePlayer(NBAPlayer player, String id) throws IOException;

    /**
     * 删除文档
     */
    boolean deletePlayer(String id) throws IOException;

    /**
     * 删除所有文档
     */
    boolean deleteAllPlayer() throws IOException;

    /**
     * 导入球员数据
     */
    boolean importDataFromMysql() throws IOException;

    /**
     * 通姓名搜索球员,分词
     */
    List<NBAPlayer> matchSearchPlayerByName(String key, String value) throws IOException;

    /**
     * 通过国家或者球队查询,精确查询
     */
    List<NBAPlayer> termSearchPlayerByCountryOrTeam(String key, String value) throws IOException;

    /**
     * 通过字母开头查询
     */
    List<NBAPlayer> prefixMatchSearchPlayer(String key, String value) throws IOException;
}
