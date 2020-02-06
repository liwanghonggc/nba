package com.lwh.elasticsearch.nba.dao;

import com.lwh.elasticsearch.nba.model.NBAPlayer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;

import java.util.List;


/**
 * @author lwh
 * @date 2020-02-06
 * @desp
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NBAPlayerDaoTest {

    @Resource
    private NBAPlayerDao nbaPlayerDao;

    @Test
    public void selectAll() {
        List<NBAPlayer> nbaPlayers = nbaPlayerDao.selectAll();
        System.out.println(JSONObject.toJSON(nbaPlayers));
    }
}