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
    public List<Contact> readContactsByCity(String city){
        this.contactDataList=contactDBService.readContactsByCity(city);
        return this.contactDataList;

    }
    public List<Contact> readContactsByState(String city){
        this.contactDataList=contactDBService.readContactsByState(city);
        return this.contactDataList;

    }
    public void updateContactsAddress(String name, String address){
        int result=contactDBService.updateContactData(name,address);
        if(result==0) return;
        Contact contact=this.getContactData(name);
        if(contact!=null) contact.address= address;
    }

    public Contact getContactData(String name) {
        return this.contactDataList.stream()
                .filter(employeePayrollData -> employeePayrollData.firstName.equals(name))
                .findFirst()
                .orElse(null);

    }
    public boolean checkIfDataBaseIsInSync(String name) throws SQLException {
        List<Contact> employeePayrollDataList= contactDBService.getContactFromDB(name);
        return employeePayrollDataList.get(0).equals(getContactData(name));

    }

}
