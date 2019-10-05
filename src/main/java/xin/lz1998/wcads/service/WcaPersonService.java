package xin.lz1998.wcads.service;

import xin.lz1998.wcads.entity.WcaPerson;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;


public interface WcaPersonService {
    public void importData() ;
    public WcaPerson findWcaPersonById(String id);
    public List<WcaPerson> findWcaPeopleByNameContaining(String name);
}
