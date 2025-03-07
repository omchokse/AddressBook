package com.example.addressbook.service;

import com.example.addressbook.dto.ContactDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private final List<ContactDTO> contactList = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1); // Generates unique IDs

    // 1️⃣ Get all contacts
    public List<ContactDTO> getAllContacts() {
        return new ArrayList<>(contactList);
    }

    // 2️⃣ Get contact by ID
    public Optional<ContactDTO> getContactById(Long id) {
        return contactList.stream().filter(contact -> contact.getId().equals(id)).findFirst();
    }

    // 3️⃣ Create new contact
    public ContactDTO createContact(ContactDTO contactDTO) {
        contactDTO.setId(counter.getAndIncrement()); // Assign unique ID
        contactList.add(contactDTO);
        return contactDTO;
    }

    // 4️⃣ Update contact by ID
    public Optional<ContactDTO> updateContact(Long id, ContactDTO updatedDTO) {
        return contactList.stream()
                .filter(contact -> contact.getId().equals(id))
                .findFirst()
                .map(contact -> {
                    contact.setName(updatedDTO.getName());
                    contact.setPhone(updatedDTO.getPhone());
                    contact.setEmail(updatedDTO.getEmail());
                    contact.setAddress(updatedDTO.getAddress());
                    return contact;
                });
    }

    // 5️⃣ Delete contact by ID
    public boolean deleteContact(Long id) {
        return contactList.removeIf(contact -> contact.getId().equals(id));
    }
}
