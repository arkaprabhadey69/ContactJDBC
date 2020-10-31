package com.bl.contact;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContactService {
    public List<Contact> contactDataList;
    private  ContactDBService contactDBService;

    public ContactService(){
        contactDBService= ContactDBService.getInstance();
        this.contactDataList=new ArrayList<>();
    }
    public List<Contact> readContactData() throws SQLException {
        this.contactDataList= contactDBService.readData();
        return this.contactDataList;

    }
    public List<Contact> readContactsBetweenDateRange(LocalDate start, LocalDate end){
        this.contactDataList=contactDBService.readDateRangeData(start,end);
        return this.contactDataList;

    }
}
