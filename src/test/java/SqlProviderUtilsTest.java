import com.simon.commonsall.utils.SqlProviderUtils;
import com.simon.tablegenerator.annotation.Column;
import com.simon.tablegenerator.annotation.Table;

import lombok.Getter;
import lombok.Setter;

/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */

/** 
 * SqlProviderUtilsTest 
 * @author  ShiXiaoyong 
 * @date    2020年1月7日
 */
public class SqlProviderUtilsTest {
    
    public static void main(String[] args) {
        System.out.println(SqlProviderUtils.Tools.generateUpdateSql(DBSettings.class, null, false));
    }

    @Table(name = DBSettings.TABLE_NAME)
    @Getter
    @Setter
    public class DBSettings {
        
        public static final String TABLE_NAME="t_settings";

        @Column(isKey=true,isAutoIncrement=true)
        Long id;
        String key;
        @Column(type="text")
        String value;
        Boolean encrypt;
    }
}
