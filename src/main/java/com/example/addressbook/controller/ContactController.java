package com.example.addressbook.controller;

import com.example.addressbook.dto.ContactDTO;
import com.example.addressbook.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    // 1️⃣ Get all contacts
    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    // 2️⃣ Get contact by ID
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        Optional<ContactDTO> contact = contactService.getContactById(id);
        return contact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3️⃣ Create new contact
    @PostMapping
    public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactDTO) {
        return ResponseEntity.ok(contactService.createContact(contactDTO));
    }

    // 4️⃣ Update contact by ID
    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody ContactDTO updatedDTO) {
        Optional<ContactDTO> updatedContact = contactService.updateContact(id, updatedDTO);
        return updatedContact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 5️⃣ Delete contact by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        return contactService.deleteContact(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
