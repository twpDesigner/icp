package org.core.datasource.boot.config.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class DataSourceModel {
    private boolean defaultDS = false;
    private String url;
    private String driverClassName;
    private String username;
    private String password;
    private String type;
}
