package com.example.addressbook.service;

import com.example.addressbook.dto.ContactDTO;
import com.example.addressbook.model.Contact;
import com.example.addressbook.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
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

    // 1️⃣ Get all contacts
    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 2️⃣ Get contact by ID
    public Optional<ContactDTO> getContactById(Long id) {
        return contactRepository.findById(id).map(this::convertToDTO);
    }

    // 3️⃣ Create new contact
    public ContactDTO createContact(ContactDTO contactDTO) {
        Contact savedContact = contactRepository.save(convertToEntity(contactDTO));
        return convertToDTO(savedContact);
    }

    // 4️⃣ Update contact by ID
    public Optional<ContactDTO> updateContact(Long id, ContactDTO updatedDTO) {
        return contactRepository.findById(id).map(contact -> {
            contact.setName(updatedDTO.getName());
            contact.setPhone(updatedDTO.getPhone());
            contact.setEmail(updatedDTO.getEmail());
            contact.setAddress(updatedDTO.getAddress());
            Contact updatedContact = contactRepository.save(contact);
            return convertToDTO(updatedContact);
        });
    }

    // 5️⃣ Delete contact by ID
    public boolean deleteContact(Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
