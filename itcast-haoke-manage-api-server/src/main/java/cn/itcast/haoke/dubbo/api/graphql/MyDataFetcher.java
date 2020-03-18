package cn.itcast.haoke.dubbo.api.graphql;

import graphql.schema.DataFetchingEnvironment;

public interface MyDataFetcher {

    /**
     * 查询名称
     */
    String fieldName();

    /**
     * 具体实现数据查询的逻辑
     */
    Object dataFetcher(DataFetchingEnvironment environment);
}
