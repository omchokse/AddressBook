package com.example.addressbook.controller;

import com.example.addressbook.dto.ContactDTO;
import com.example.addressbook.model.Contact;
import com.example.addressbook.repository.ContactRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // Convert Entity to DTO
    private ContactDTO convertToDTO(Contact contact) {
        return new ContactDTO(contact.getName(), contact.getPhone(), contact.getEmail(), contact.getAddress());
    }

    // Convert DTO to Entity
    private Contact convertToEntity(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setName(contactDTO.getName());
        contact.setPhone(contactDTO.getPhone());
        contact.setEmail(contactDTO.getEmail());
        contact.setAddress(contactDTO.getAddress());
        return contact;
    }

    // 1️⃣ Get all contacts (returns DTOs)
    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        List<ContactDTO> contacts = contactRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contacts);
    }

    // 2️⃣ Get contact by ID
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        return contactRepository.findById(id)
                .map(contact -> ResponseEntity.ok(convertToDTO(contact)))
                .orElse(ResponseEntity.notFound().build());
    }

    // 3️⃣ Create new contact
    @PostMapping
    public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactDTO) {
        Contact savedContact = contactRepository.save(convertToEntity(contactDTO));
        return ResponseEntity.ok(convertToDTO(savedContact));
    }

    // 4️⃣ Update contact by ID
    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody ContactDTO updatedDTO) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contact.setName(updatedDTO.getName());
                    contact.setPhone(updatedDTO.getPhone());
                    contact.setEmail(updatedDTO.getEmail());
                    contact.setAddress(updatedDTO.getAddress());
                    Contact updatedContact = contactRepository.save(contact);
                    return ResponseEntity.ok(convertToDTO(updatedContact));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5️⃣ Delete contact by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
