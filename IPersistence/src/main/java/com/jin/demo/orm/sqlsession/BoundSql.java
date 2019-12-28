package com.jin.demo.orm.sqlsession;

import com.jin.demo.orm.pojo.ParameterMapping;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class BoundSql {
    private String sqlText;
    private List<ParameterMapping> parameterMappingList = new ArrayList<>();
}
