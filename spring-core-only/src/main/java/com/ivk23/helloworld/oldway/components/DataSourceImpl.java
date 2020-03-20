package com.ivk23.helloworld.oldway.components;

public class DataSourceImpl implements DataSource {

    @Override
    public String getConnection() {
        return "Test Connection";
    }
}
