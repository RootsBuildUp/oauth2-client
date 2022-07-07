package com.RootsBuildup.OAuth2Client.dao;

import com.RootsBuildup.OAuth2Client.annotations.NativeQueryResultsMapper;
import com.RootsBuildup.OAuth2Client.model.AccessToken;
import lombok.SneakyThrows;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TokenAccessDao {

    public static DataSource dataSource = null;
    public static Connection connection = null;


    @SneakyThrows
    private void tokenDbConnection(){
        if(dataSource == null) dataSource = dataSource();
        if(connection == null) connection = dataSource.getConnection();
    }

    private DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource() ;
        dataSource.setServerName( "localhost" );
        dataSource.setDatabaseName( "oauth" );
        dataSource.setUser( "postgres" );
        dataSource.setPassword( "123456" );
        return dataSource;
    }


    @SneakyThrows
    public List<AccessToken> tokenForQuery(String id) {
        tokenDbConnection();
        String query = "SELECT id, authentication, client_id, expired_date_and_time, o_auth2access_token, refresh_token, \"token\", username\n" +
                " FROM public.access_token where token = '"+id+"'";

        System.out.println(query);
        Statement statement = connection.createStatement();
        List<AccessToken> accessTokenList = NativeQueryResultsMapper.map(statement.executeQuery(query), AccessToken.class);

        return accessTokenList;
    }

    @SneakyThrows
    public Integer tokenUpdated(String tokenId, LocalDateTime localDateTime) {
        tokenDbConnection();
        String updatedQuery = "UPDATE access_token set expired_date_and_time='"+convertToLocalDateTimeToString(localDateTime)+"'\n" +
                "WHERE id='"+tokenId+"'";

        System.out.println(updatedQuery);
        Statement statement = connection.createStatement();
        return statement.executeUpdate(updatedQuery);
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String convertToLocalDateTimeToString(LocalDateTime localDateTime) {
        return (localDateTime == null ? null : formatter.format(localDateTime));
    }
}
