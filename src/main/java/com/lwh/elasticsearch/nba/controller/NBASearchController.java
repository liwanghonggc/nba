package com.lwh.elasticsearch.nba.controller;

import com.lwh.elasticsearch.nba.model.NBAPlayer;
import com.lwh.elasticsearch.nba.service.NBAPlayerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author lwh
 * @date 2020-02-06
 * @desp
 */
@RestController
@RequestMapping("/nba")
public class NBASearchController {

    @Autowired
    private NBAPlayerService nbaPlayerService;

    @RequestMapping("/importAll")
    public String importAll(){
        try {
            nbaPlayerService.importDataFromMysql();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping("/searchMatch")
    public List<NBAPlayer> searchMatch(@RequestParam(value = "displayNameEn", required = false) String displayNameEn){
        try {
            List<NBAPlayer> nbaPlayers = nbaPlayerService.matchSearchPlayerByName("displayNameEn", displayNameEn);
            return nbaPlayers;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/searchTerm")
    public List<NBAPlayer> searchTerm(@RequestParam(value = "country", required = false) String country,
                                      @RequestParam(value = "teamName", required = false) String teamName){
        try {
            if(StringUtils.isNotEmpty(country)){
                return nbaPlayerService.termSearchPlayerByCountryOrTeam("country", country);
            }else if(StringUtils.isNotEmpty(teamName)){
                return nbaPlayerService.termSearchPlayerByCountryOrTeam("teamName", teamName);
            }else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/searchPrefix")
    public List<NBAPlayer> searchPrefix(@RequestParam(value = "displayNameEn", required = false) String displayNameEn){
        try {
            return nbaPlayerService.prefixMatchSearchPlayer("displayNameEn", displayNameEn);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
