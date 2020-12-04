package cn.zefre.es.util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author pujian
 * @date 2020/11/30 14:37
 */
public class ESClientUtil {

    private static RestHighLevelClient client;

    private static final String HOST = "39.100.10.172";

    private static final int PORT_REST = 9200;

    /**
     * 获取esclient
     * @author pujian
     * @date 2020/11/30 14:43
     */
    public static synchronized RestHighLevelClient getClient() {
        if(null == client) {
            client = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(HOST, PORT_REST, "http")));
        }
        return client;
    }
}
