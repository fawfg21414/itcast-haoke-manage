package cn.itcast.haoke.dubbo.api.controller;

import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("graphql")
@Controller
public class GraphQLController {
    @Autowired
    private GraphQL graphql;

    @GetMapping
    @ResponseBody
    public Map<String,Object> graphql(@RequestParam("query") String query)throws Exception{
        return this.graphql.execute(query).toSpecification();
    }

}
