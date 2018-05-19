package org.core.datasource.boot.config;

import lombok.extern.slf4j.Slf4j;
import org.core.datasource.boot.config.container.DynamicDataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return  DynamicDataSourceContextHolder.getDataSourceKey();
    }

}