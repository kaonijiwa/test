package com.example.api;

import com.alibaba.fastjson.JSON;
import com.example.api.pojo.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class ApiApplicationTests {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient restHighLevelClient;

    @Test
    void createIndex() throws IOException {
        //创建索引
        CreateIndexRequest request = new CreateIndexRequest("test_index");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
        //获取索引
        GetIndexRequest getIndexRequest = new GetIndexRequest("test_index2");
        boolean flag = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(flag);
        //删除索引
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("test_index");
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(acknowledgedResponse.isAcknowledged());

    }

    //添加文档
    @Test
    void addDocument() throws IOException {
        User user = new User("狂神", 3);
        IndexRequest indexRequest = new IndexRequest("test_index");
        //规则 put test_index/_doc/1
        indexRequest.id("1");
        indexRequest.timeout(TimeValue.timeValueSeconds(2));
        //将数据放入请求
        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);
        //客户端发送请求
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.toString());
        System.out.println(indexResponse.status());
    }

    //获取文档
    @Test
    void getDocument() throws IOException {
        GetRequest getRequest = new GetRequest("test_index", "1");
        //不获取返回的_source中的内容了
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        //判断文档是否存在
        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse);
        System.out.println(getResponse.getSourceAsString());
    }

    //更新文档
    @Test
    void updateDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("test_index", "1");
        updateRequest.timeout("1s");
        User user = new User("钟哥", 18);
        updateRequest.doc(JSON.toJSONString(user), XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(updateResponse);
    }

    //删除文档
    @Test
    void deleteDocument() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("test_index", "1");
        deleteRequest.timeout("1s");
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse);
    }

    //批量操作
    @Test
    void testBatchRequest() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        List<User> list = new CopyOnWriteArrayList<>();
        list.add(new User("钟哥01", 1));
        list.add(new User("钟哥02", 2));
        list.add(new User("钟哥03", 3));
        list.add(new User("彭钟", 4));
        list.add(new User("彭中2", 5));

        //批处理请求
        for (int i = 0; i < list.size(); i++) {
            bulkRequest.add(
                    new IndexRequest("test_index")
                            .id("" + (i + 1))
                            .source(JSON.toJSONString(list.get(i)), XContentType.JSON)
            );
        }

        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.status());
    }

    //查询
    @Test
    void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("test_index");
        //构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //精确查询 不是keyword只能单个汉字查询
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "钟");
        //构建高亮
        sourceBuilder.highlighter();
        //匹配全部的查询
        //MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        sourceBuilder.query(termQueryBuilder);
        //分页
//        sourceBuilder.from();
//        sourceBuilder.size();
        sourceBuilder.timeout(new TimeValue(3, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse);

    }

}
