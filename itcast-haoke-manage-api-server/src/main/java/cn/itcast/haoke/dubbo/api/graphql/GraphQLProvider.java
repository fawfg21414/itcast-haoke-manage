package cn.itcast.haoke.dubbo.api.graphql;

import cn.itcast.haoke.dubbo.api.service.HouseResourcesService;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class GraphQLProvider {
    private GraphQL graphQL;

    @Autowired
    private List<MyDataFetcher> myDataFetchers;//注入容器中所有的MyDataFetcher实现类

    @PostConstruct
    public void init()throws IOException{
        File file = ResourceUtils.getFile("classpath:haoke.graphqls");
        GraphQLSchema graphQLSchema = buildSchema(file);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(File file){
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(file);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry,runtimeWiring);
    }

    private RuntimeWiring buildWiring(){
        return RuntimeWiring.newRuntimeWiring().
                type("HaokeQuery",builder -> {
                    for (MyDataFetcher myDataFetcher : myDataFetchers){
                        builder.dataFetcher(myDataFetcher.fieldName(),environment ->
                                myDataFetcher.dataFetcher(environment));
                    }
                    return builder;
                }).build();
    }

    @Bean
    public GraphQL graphQL(){
        return graphQL;
    }

}
