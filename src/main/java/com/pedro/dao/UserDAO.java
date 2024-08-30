package com.pedro.dao;

import com.pedro.model.impl.UserImpl;
import jakarta.annotation.PostConstruct;
import com.pedro.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends DAO<User> {

    private final Log log = LogFactory.getLog(UserDAO.class);

    @Value("${data.users}")
    private String dataFile;

    @PostConstruct
    public void init(){
        loadData(dataFile, User::setId);
        log.info(data.size() + " users were loaded from " + dataFile);
    }

    @Override
    protected User from(String fileLine) {
        var data = fileLine.split(",");
        return new UserImpl(data[0], data[1]);
    }
}
