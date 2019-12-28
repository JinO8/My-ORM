package com.jin.demo.orm.utils;

import com.jin.demo.orm.pojo.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

public class ParameterMappingTokenHandler implements TokenHandler{

    private List<ParameterMapping> parameterMappings = new ArrayList<>();
    @Override
    public String handleToken(String content) {
        parameterMappings.add(buldParameterMapping(content));
        return "?";
    }

    private ParameterMapping buldParameterMapping(String content) {
        return new ParameterMapping(content);
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}
