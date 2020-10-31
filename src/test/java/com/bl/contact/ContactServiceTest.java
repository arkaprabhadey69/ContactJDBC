package com.bl.contact;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class ContactServiceTest {

    @Test
    public void readData() throws SQLException {
        ContactService contactService = new ContactService();
        List<Contact> contactList = contactService.readContactData();
        Assert.assertEquals(3, contactList.size());

    }
    @Test
    public void readDataInGivenRange() throws SQLException {
        ContactService contactService = new ContactService();
        LocalDate start=LocalDate.of(2018,4,4);
        List<Contact> contactList = contactService.readContactsBetweenDateRange(start,LocalDate.now());
        Assert.assertEquals(3, contactList.size());

    }
    @Test
    public void readDataInGivenCity() throws SQLException {
        ContactService contactService = new ContactService();
        List<Contact> contactList = contactService.readContactsByCity("Kolkata");
        Assert.assertEquals(2, contactList.size());

    }
    @Test
    public void readDataInGivenState() throws SQLException {
        ContactService contactService = new ContactService();
        List<Contact> contactList = contactService.readContactsByState("Maharastra");
        Assert.assertEquals(1, contactList.size());

    }
    @Test
    public void updateDateMatchesWithDB() throws SQLException {
        ContactService contactService = new ContactService();
        List<Contact> employeePayrollDataList = contactService.readContactData();
        contactService.updateContactsAddress("Orko", "Kalighat");
        boolean result = contactService.checkIfDataBaseIsInSync("Orko");
       Assert.assertTrue(result);
    }
}