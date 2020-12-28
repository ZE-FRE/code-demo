package cn.zefre.es;

import cn.zefre.es.entity.Movie;
import cn.zefre.es.util.ESClientUtil;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * @author pujian
 * @date 2020/11/30 14:46
 */
public class ESTest {

    private RestHighLevelClient client = ESClientUtil.getClient();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 测试index api
     * @author pujian
     * @date 2020/11/30 15:24
     */
    @Test
    public void testIndex() throws InterruptedException {
        Map<String, Object> sourceMap = new HashMap<>();
        sourceMap.put("user", "kimchy");
        sourceMap.put("postDate", new Date());
        sourceMap.put("message", "index a document");
        // 创建一个index请求
        IndexRequest request = new IndexRequest("posts", "doc", "1")
                .source(sourceMap)
                .opType(DocWriteRequest.OpType.CREATE);

        // 异步请求完成监听器
        ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                System.out.println(indexResponse);
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("失败：" + e.getMessage());
            }
        };
        // 异步执行index
        client.indexAsync(request, RequestOptions.DEFAULT, listener);

        TimeUnit.MILLISECONDS.sleep(1500);
    }


    /**
     * 测试get api
     * @author pujian
     * @date 2020/11/30 15:26
     */
    @Test
    public void tetGet() throws IOException {
        GetRequest getRequest = new GetRequest("posts", "doc", "1");
        GetResponse getResponse = null;
        try {
            getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
           e.printStackTrace();
        }
        if(null != getResponse && getResponse.isExists()) {
            String sourceAsString = getResponse.getSourceAsString();
            System.out.println(sourceAsString);
        } else {
            System.out.println("document not found");
        }
    }

    /**
     * 测试部分更新api
     * @author pujian
     * @date 2020/12/2 17:23
     */
    @Test
    public void testUpdate() throws InterruptedException {
        UpdateRequest updateRequest = new UpdateRequest("user", "programmer", "3")
                .doc("name", "王五")
                .fetchSource(true);
        ActionListener listener = new ActionListener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse updateResponse) {
                System.out.println("version = " + updateResponse.getVersion());
                // 成功更新
                if(updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                    GetResult result = updateResponse.getGetResult();
                    System.out.println(result.sourceAsString());
                } else if(updateResponse.getResult() == DocWriteResponse.Result.DELETED) {
                    System.out.println("文档已被删除！");
                } else if(updateResponse.getResult() == DocWriteResponse.Result.NOOP) {
                    System.out.println("noop");
                }
            }
            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        };
        client.updateAsync(updateRequest, RequestOptions.DEFAULT, listener);
        TimeUnit.MILLISECONDS.sleep(1500);
    }


    /**
     * 测试bulk api
     * @author pujian
     * @date 2020/12/2 17:57
     */
    @Test
    public void testBulk() throws InterruptedException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new IndexRequest("user", "programmer", "4")
                .source(XContentType.JSON, "name","王麻子","age",18,"introduce","大学才刚接触编程！","birth","2002-12-01"))
                .add(new IndexRequest("user", "programmer", "5")
                .source(XContentType.JSON,"name","田六","age",24,"introduce","","birth","1996-04-06"));

        client.bulkAsync(bulkRequest, RequestOptions.DEFAULT, new ActionListener<BulkResponse>() {
            @Override
            public void onResponse(BulkResponse bulkResponse) {
                System.out.println("是否有执行失败的：" + bulkResponse.hasFailures());
                // 遍历每个请求结果
                for (BulkItemResponse bulkItemResponse : bulkResponse) {
                    String doc = "/" + bulkItemResponse.getIndex() + "/" + bulkItemResponse.getType() + "/" + bulkItemResponse.getId();
                    if(bulkItemResponse.isFailed()) {
                        System.out.println(bulkItemResponse.getFailureMessage());
                    } else if(bulkItemResponse.getOpType() == DocWriteRequest.OpType.INDEX
                        || bulkItemResponse.getOpType() == DocWriteRequest.OpType.CREATE) {
                        System.out.println(doc + " index/create success");
                    } else if(bulkItemResponse.getOpType() == DocWriteRequest.OpType.UPDATE) {
                        System.out.println(doc + " update success");
                    } else if(bulkItemResponse.getOpType() == DocWriteRequest.OpType.DELETE) {
                        System.out.println(doc + " delete success");
                    }
                }
            }
            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        });
        TimeUnit.MILLISECONDS.sleep(2000);
    }


    /**
     * 创建索引结构
     * @author pujian
     * @date 2020/12/3 11:27
     */
    @Test
    public void testCreateIndex() throws IOException {
        // 构建settings
        Settings settings = Settings.builder()
                .put("index.number_of_shards", 5)
                .put("index.number_of_replicas", 1)
                .build();
        CreateIndexRequest request = new CreateIndexRequest("movie", settings);
        // mappings
        String mappingJson = "{" +
                "   \"properties\": {" +
                "        \"name\": {" +
                "          \"type\": \"keyword\"" +
                "        }," +
                "        \"releaseDate\": {" +
                "          \"type\": \"date\"," +
                "          \"format\": \"yyyyMM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\"" +
                "        }," +
                "        \"duration\": {" +
                "          \"type\": \"keyword\"" +
                "        }," +
                "        \"introduce\": {" +
                "          \"type\": \"text\"," +
                "          \"analyzer\": \"ik_smart\"" +
                "        }" +
                "      }" +
                "   }";
        request.mapping("plot", mappingJson, XContentType.JSON);
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.isAcknowledged());
    }

    /**
     * 使用BulkProcessor
     * @author pujian
     * @date 2020/12/3 10:16
     */
    @Test
    public void testBulkByBulkProcessor() throws ParseException, InterruptedException {
        // 构建实体类
        Movie movie1 = new Movie(5,"画皮",sdf.parse("2011-06-05"),"1小时57分23秒","节选自聊斋志异，由周迅、赵薇、陈坤、甄子丹、孙俪领衔主演的中国古代奇幻爱情电影");
        Movie movie2 = new Movie(6,"星际穿越",sdf.parse("2013-11-13"),"2小时49分24秒","经典太空科幻星际大片，着重描述亲情");
        Movie movie3 = new Movie(3,"泰坦尼克号",sdf.parse("1999-12-31"),"2小时49分23秒","经典不朽的爱情灾难电影");
        Movie movie4 = new Movie(4,"叶问",sdf.parse("2011-05-12"),"1小时58分36秒","甄子丹奠定叶问系列基础之作");

        // 构建index请求
        IndexRequest indexRequest1 = new IndexRequest("movie", "plot", movie1.getId().toString())
                .source(JSON.toJSONString(movie1), XContentType.JSON);
        IndexRequest indexRequest2 = new IndexRequest("movie", "plot", movie2.getId().toString())
                .source(JSON.toJSONString(movie2), XContentType.JSON);
        IndexRequest indexRequest3 = new IndexRequest("movie", "plot", movie3.getId().toString())
                .source(JSON.toJSONString(movie3), XContentType.JSON);
        IndexRequest indexRequest4 = new IndexRequest("movie", "plot", movie4.getId().toString())
                .source(JSON.toJSONString(movie4), XContentType.JSON);

        // 构建update请求
        UpdateRequest updateRequest = new UpdateRequest("/user", "programmer", "3").doc("name", "王五");
        UpdateRequest updateRequestNotExists = new UpdateRequest("movie", "xxx", "1").doc("name", "xxx");

        // 构建一个listener
        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            // bulk操作执行前
            @Override
            public void beforeBulk(long executionId, BulkRequest bulkRequest) {
                int numberOfActions = bulkRequest.numberOfActions();
                System.out.println("executionId: " + executionId + " bulk请求数：" + numberOfActions);
            }
            // bulk操作执行完毕后
            @Override
            public void afterBulk(long executionId, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                System.out.print("executionId: " + executionId + " ");
                if(bulkResponse.hasFailures()) {
                    System.out.println(" 存在执行失败请求 ");
                } else {
                    System.out.println("花费时间：" + bulkResponse.getTook().getMillis());
                }
            }
            // 操作执行失败处理
            @Override
            public void afterBulk(long executionId, BulkRequest bulkRequest, Throwable throwable) {
                System.out.println("executionId: " + executionId + " execute error");
                System.out.println(throwable.getMessage());
                throwable.printStackTrace();
            }
        };
        // 构建BulkProcessor
        BiConsumer<BulkRequest, ActionListener<BulkResponse>> biConsumer =
                (request, bulkListener) -> client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener);
        BulkProcessor processor = BulkProcessor.builder(biConsumer, listener).build();
        processor.add(indexRequest1)
                .add(indexRequest2)
                .add(indexRequest3)
                .add(indexRequest4)
                .add(updateRequest)
                .add(updateRequestNotExists);

        boolean executionStatus = processor.awaitClose(3000L, TimeUnit.MILLISECONDS);
        System.out.println("是否执行完成：" + executionStatus);
    }


    /**
     * 测试multiGet
     * @author pujian
     * @date 2020/12/5 21:17
     * @return
     */
    @Test
    public void testMultiGet() throws IOException {
        MultiGetRequest request = new MultiGetRequest();
        request.add(new MultiGetRequest.Item("movie", "plot", "1"))
                .add(new MultiGetRequest.Item("movie", "plot", "2"))
                .add(new MultiGetRequest.Item("movie", "plot", "3"))
                .add(new MultiGetRequest.Item("movie", "plot", "222"));

        // 执行请求
        MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
        MultiGetItemResponse[] responses = response.getResponses();
        // 对每个请求进行迭代
        Arrays.stream(responses).forEach(itemResponse -> {
            GetResponse getResponse = itemResponse.getResponse();
            if(getResponse.isExists()) {
                System.out.println(getResponse.getSourceAsString());
            } else {
                System.out.println("/" + getResponse.getIndex()
                        + "/" + getResponse.getType()
                        + "/" + getResponse.getId()
                        + " is not exists!");
            }

            /** 如果有异常信息，获取异常信息 */
            Optional.ofNullable(itemResponse.getFailure())
                    .map(failure -> failure.getFailure())
                    .ifPresent(exception -> {
                        ElasticsearchException ee = (ElasticsearchException)exception;
                        System.out.println(ee.getMessage());
                    });
        });
    }


    /**
     * term查询
     * @author pujian
     * @date 2020/12/7 10:08
     */
    @Test
    public void testTermQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("movie");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("name", "泰坦尼克号"));
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsString());
        }
    }


    /**
     * match查询
     * @author pujian
     * @date 2020/12/7 10:01
     */
    @Test
    public void testMatchQuery() throws IOException {
        SearchRequest request = new SearchRequest("movie");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("introduce", "爱情"));
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }


    /**
     * scroll查询
     * @author pujian
     * @date 2020/12/7 14:40
     */
    @Test
    public void testFirstScrollQuery() throws IOException {
        SearchRequest request = new SearchRequest("movie");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        builder.size(2);
        request.source(builder);
        request.scroll(TimeValue.timeValueMinutes(1L));
        // 执行请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        String scrollId = response.getScrollId();
        SearchHit[] hits = response.getHits().getHits();
        System.out.println("scrollId = " + scrollId);
        for(SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * scroll查询下一页
     * @author pujian
     * @date 2020/12/7 14:45
     */
    @Test
    public void testScrollQuery() throws IOException {
        SearchScrollRequest request = new SearchScrollRequest("DnF1ZXJ5VGhlbkZldGNoBQAAAAAAAJHQFkNoY3JpMjh6UTgtY19BVkZwLVJPeWcAAAAAAACR1BZDaGNyaTI4elE4LWNfQVZGcC1ST3lnAAAAAAAAkdEWQ2hjcmkyOHpROC1jX0FWRnAtUk95ZwAAAAAAAJHSFkNoY3JpMjh6UTgtY19BVkZwLVJPeWcAAAAAAACR0xZDaGNyaTI4elE4LWNfQVZGcC1ST3ln");
        request.scroll(TimeValue.timeValueSeconds(30L));
        SearchResponse response = client.scroll(request, RequestOptions.DEFAULT);
        String scrollId = response.getScrollId();
        SearchHit[] hits = response.getHits().getHits();
        System.out.println("scrollId = " + scrollId);
        for(SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 测试bool查询
     * @author pujian
     * @date 2020/12/7 15:49
     */
    @Test
    public void testBoolQuery() throws IOException {
        SearchRequest request = new SearchRequest("movie");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(QueryBuilders.matchQuery("introduce", "电影"));
        boolBuilder.must(QueryBuilders.rangeQuery("releaseDate").gte("1999-12-31").lte("2011-06-04"));
        builder.query(boolBuilder);
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        for(SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsString());
        }
    }
}
