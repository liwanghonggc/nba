package com.lwh.elasticsearch.nba.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lwh.elasticsearch.nba.model.NBAPlayer;
import com.lwh.elasticsearch.nba.service.NBAPlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author lwh
 * @date 2020-02-06
 * @desp
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NBAPlayerServiceImplTest {

    @Resource
    private NBAPlayerService nbaPlayerService;

    @Test
    public void addPlayer() throws IOException {
        NBAPlayer player = new NBAPlayer();
        player.setId(999);
        player.setDisplayName("李旺红");
        nbaPlayerService.addPlayer(player, "999");
    }

    @Test
    public void getPlayer() throws IOException {
        Map<String, Object> player = nbaPlayerService.getPlayer("999");
        System.out.println(JSONObject.toJSON(player));
    }

    @Test
    public void updatePlayer() throws IOException {
        NBAPlayer player = new NBAPlayer();
        player.setDisplayName("许春杰");
        nbaPlayerService.updatePlayer(player, "999");
    }

    @Test
    public void deletePlayer() throws IOException {
        nbaPlayerService.deletePlayer("999");
    }
}