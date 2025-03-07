package com.example.addressbook.controller;

import com.example.addressbook.model.Contact;
import com.example.addressbook.repository.ContactRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // 1️⃣ Get all contacts
    @GetMapping
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    // 2️⃣ Get contact by ID
    @GetMapping("/{id}")
    public Contact getContactById(@PathVariable Long id) {
        return contactRepository.findById(id).orElse(null);
    }

    // 3️⃣ Create new contact
    @PostMapping
    public Contact createContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    // 4️⃣ Update contact by ID
    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable Long id, @RequestBody Contact updatedContact) {
        return contactRepository.findById(id).map(contact -> {
            contact.setName(updatedContact.getName());
            contact.setPhone(updatedContact.getPhone());
            contact.setEmail(updatedContact.getEmail());
            return contactRepository.save(contact);
        }).orElse(null);
    }

    // 5️⃣ Delete contact by ID
    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Long id) {
        contactRepository.deleteById(id);
    }
}
