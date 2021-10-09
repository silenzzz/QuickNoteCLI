package com.demmage.qnc.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
class RequestResult {

    private boolean affected;
    private List<Map<String, Object>> queryResult;

}
