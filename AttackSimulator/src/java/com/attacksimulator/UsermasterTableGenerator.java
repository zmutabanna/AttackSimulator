/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attacksimulator;

import java.util.ArrayList;
import java.util.HashMap;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author securonix
 */
public class UsermasterTableGenerator extends ValueGeneratorType{
    private String query;
    private MySQLDBClass mydb;
    private String secuserid;
    
    public UsermasterTableGenerator(String variableName, ArrayList<String> params) {
        super(variableName, params);
        secuserid = params.get(params.size()-1);
        mydb = new MySQLDBClass();
    }

    @Override
    public HashMap<String, String> getValue() throws OperationNotSupportedException, NumberFormatException {
        String countQuery = "select count(*) from usermaster, sysipusermapping where usermaster.id=sysipusermapping.userid and sysipusermapping.secuserid="+secuserid+";";
        query = "select usermaster.userid, usermaster.firstname, usermaster.middlename, usermaster.lastname, usermaster.workemail, usermaster.lanid, usermaster.networkid, usermaster.manageremployeeid, usermaster.department, sysipusermapping.ipaddress1, sysipusermapping.ipaddress2, sysipusermapping.ipaddress3 from usermaster, sysipusermapping where usermaster.id=sysipusermapping.userid and sysipusermapping.secuserid="+secuserid+";";
        
        if(params.size() == 2){
            //need to include these in the where clause. Allow only one parameter?
            query = "select usermaster.userid, usermaster.firstname, usermaster.middlename, usermaster.lastname, usermaster.workemail, usermaster.lanid, usermaster.networkid, usermaster.manageremployeeid, usermaster.department,usermaster.internalip,usermaster.desktopipaddress,usermaster.desktopname,usermaster.account1,usermaster.externalip1,usermaster.externalip2,usermaster.externalip3, sysipusermapping.ipaddress1, sysipusermapping.ipaddress2, sysipusermapping.ipaddress3 from usermaster, sysipusermapping where usermaster.id=sysipusermapping.userid and sysipusermapping.secuserid="+secuserid+" and "+params.get(0)+";";
            countQuery = "select count(*) from usermaster, sysipusermapping where usermaster.id=sysipusermapping.userid and sysipusermapping.secuserid="+secuserid+" and "+ params.get(0)+";";
            
            //System.out.println("usermaster query = "+query);
        }else if(params.size() > 2){
            throw new OperationNotSupportedException();
        }

        HashMap<String, String> temp = mydb.executeQuery(query, countQuery, variableName);
        
        return temp;
    }
}
