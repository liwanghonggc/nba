package com.lwh.elasticsearch.nba.service.impl;

import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.lwh.elasticsearch.nba.dao.NBAPlayerDao;
import com.lwh.elasticsearch.nba.model.NBAPlayer;
import com.lwh.elasticsearch.nba.service.NBAPlayerService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lwh
 * @date 2020-02-06
 * @desp
 */
@Service
@Slf4j
public class NBAPlayerServiceImpl implements NBAPlayerService {

    private static final String INDEX_NAME = "nba_latest";

    @Resource
    private RestHighLevelClient client;

    @Resource
    private NBAPlayerDao nbaPlayerDao;

    @Override
    public boolean addPlayer(NBAPlayer nbaPlayer, String id) throws IOException {
        IndexRequest request = new IndexRequest(INDEX_NAME).id(id).source(beanToMap(nbaPlayer));
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response);
        return true;
    }

    @Override
    public Map<String, Object> getPlayer(String id) throws IOException {
        GetRequest request = new GetRequest(INDEX_NAME, id);
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        return response.getSource();
    }

    @Override
    public boolean updatePlayer(NBAPlayer player, String id) throws IOException {
        UpdateRequest request = new UpdateRequest(INDEX_NAME, id).doc(beanToMap(player));
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(response);
        return true;
    }

    @Override
    public boolean deletePlayer(String id) throws IOException {
        DeleteRequest request = new DeleteRequest(INDEX_NAME, id);
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response);
        return true;
    }

    @Override
    public boolean deleteAllPlayer() throws IOException {
        DeleteByQueryRequest request = new DeleteByQueryRequest(INDEX_NAME);
        BulkByScrollResponse response = client.deleteByQuery(request, RequestOptions.DEFAULT);
        System.out.println(response);
        return true;
    }

    @Override
    public boolean importDataFromMysql() throws IOException {
        List<NBAPlayer> playerList = nbaPlayerDao.selectAll();
        for (NBAPlayer player : playerList) {
            addPlayer(player, String.valueOf(player.getId()));
        }
        return true;
    }

    @Override
    public List<NBAPlayer> matchSearchPlayerByName(String key, String value) throws IOException {
        SearchRequest request = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery(key, value));
        builder.from(0);
        builder.size(100);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(JSONObject.toJSON(response));

        SearchHit[] searchHits = response.getHits().getHits();
        List<NBAPlayer> nbaPlayers = Arrays.stream(searchHits).map(e -> JSONObject.parseObject(e.getSourceAsString(), NBAPlayer.class))
                .collect(Collectors.toList());
        return nbaPlayers;
    }

    @Override
    public List<NBAPlayer> termSearchPlayerByCountryOrTeam(String key, String value) throws IOException {
        SearchRequest request = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery(key, value));
        builder.from(0);
        builder.size(100);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(JSONObject.toJSON(response));

        SearchHit[] searchHits = response.getHits().getHits();
        List<NBAPlayer> nbaPlayers = Arrays.stream(searchHits).map(e -> JSONObject.parseObject(e.getSourceAsString(), NBAPlayer.class))
                .collect(Collectors.toList());
        return nbaPlayers;
    }

    @Override
    public List<NBAPlayer> prefixMatchSearchPlayer(String key, String value) throws IOException {
        SearchRequest request = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.prefixQuery(key, value));
        builder.from(0);
        builder.size(100);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(JSONObject.toJSON(response));

        SearchHit[] searchHits = response.getHits().getHits();
        List<NBAPlayer> nbaPlayers = Arrays.stream(searchHits).map(e -> JSONObject.parseObject(e.getSourceAsString(), NBAPlayer.class))
                .collect(Collectors.toList());
        return nbaPlayers;
    }

    private static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                if (beanMap.get(key) != null)
                    map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }
}
