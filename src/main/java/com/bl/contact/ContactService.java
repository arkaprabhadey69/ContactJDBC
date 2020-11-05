package com.bl.contact;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactService {
    public List<Contact> contactDataList;
    private ContactDBService contactDBService;

    public ContactService() {
        contactDBService = ContactDBService.getInstance();
        this.contactDataList = new ArrayList<>();
    }

    public ContactService(List<Contact> contactList) {
        super();
        this.contactDataList=new ArrayList<>(contactList);
    }

    public void WelcomeMessage() {
        System.out.println("Welcome to Adv Address Book JDBC problem");
    }

    //Service To Retrieve All Data
    public List<Contact> readContactData() throws SQLException {
        this.contactDataList = contactDBService.readData();
        return this.contactDataList;

    }

    //Service to Retrieve Contacts within a given Date Range
    public List<Contact> readContactsBetweenDateRange(LocalDate start, LocalDate end) {
        this.contactDataList = contactDBService.readDateRangeData(start, end);
        return this.contactDataList;

    }

    //Service To get contacts based on city
    public List<Contact> readContactsByCity(String city) {
        this.contactDataList = contactDBService.readContactsByCity(city);
        return this.contactDataList;

    }

    //Service To get contacts based on state
    public List<Contact> readContactsByState(String city) {
        this.contactDataList = contactDBService.readContactsByState(city);
        return this.contactDataList;

    }

    //Service To update address based on name
    public void updateContactsAddress(String name, String address) {
        int result = contactDBService.updateContactData(name, address);
        if (result == 0) return;
        Contact contact = this.getContactData(name);
        if (contact != null) contact.address = address;
    }

    //Service To add contact to addressbook
    public void addContactToAddressBook(String firstName, String lastName, String address, String city, String state, String zip, String number, String email, LocalDate start) throws SQLException {
        contactDataList.add(contactDBService.addContactToAddress(firstName, lastName, address, city, state, zip, number, email, start));
    }

    //Service To get contact to contactList by name
    public Contact getContactData(String name) {
        return this.contactDataList.stream()
                .filter(employeePayrollData -> employeePayrollData.firstName.equals(name))
                .findFirst()
                .orElse(null);

    }

    //Service To check if DB is in sync
    public boolean checkIfDataBaseIsInSync(String name) throws SQLException {
        List<Contact> employeePayrollDataList = contactDBService.getContactFromDB(name);
        return employeePayrollDataList.get(0).equals(getContactData(name));

    }

    public int countEntries() {
        return this.contactDataList.size();
    }

    public void addMultipleEmployee(List<Contact> contactList) {
        Map<Integer, Boolean> employeeStatus = new HashMap<>();
        contactList.forEach(contact -> {
            Runnable task = () -> {
                try {
                    employeeStatus.put(contact.hashCode(), false);
                    System.out.println("Adding.." + Thread.currentThread().getName());
                    this.addContactToAddressBook(contact.firstName, contact.lastName, contact.address, contact.city, contact.state, contact.zip, contact.number, contact.email, contact.start);
                    employeeStatus.put(contact.hashCode(), true);
                    System.out.println("Added!" + Thread.currentThread().getName());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            };
            Thread thread = new Thread(task, contact.firstName);
            thread.start();
        });

    }

    public void addEmployeeDataForREST(Contact contact) {
        this.contactDataList.add(contact);
    }
}
