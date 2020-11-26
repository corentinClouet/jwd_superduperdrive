package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.MessageInfo;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public CredentialController(FileService fileService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/add")
    public String addCredential(CredentialForm credentialForm, Model model) {
        //verify URL format
        if (!credentialService.verifyUrl(credentialForm.getCredentialUrl())) {
            model.addAttribute("messageCredential", new MessageInfo("Wrong format for the URL.", true));
            return "home";
        }

        //insert or update credential depending on credentialId value
        if (credentialService.insertOrUpdate(credentialForm)) {
            model.addAttribute("messageCredential", new MessageInfo("Credential added/updated successfully.", false));
        } else {
            model.addAttribute("messageCredential", new MessageInfo("Error while adding/updating credential.", true));
        }
        model.addAttribute("credentials", credentialService.getAll());
        return "home";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialId, Model model) {
        if (credentialService.delete(credentialId)) {
            model.addAttribute("messageCredential", new MessageInfo("Credential deleted.", false));
        } else {
            model.addAttribute("messageCredential", new MessageInfo("Error while deleting credential.", true));
        }
        model.addAttribute("credentials", credentialService.getAll());
        return "home";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("files", fileService.getAllFiles());
        model.addAttribute("notes", noteService.getAll());
        model.addAttribute("credentials", credentialService.getAll());
        model.addAttribute("noteForm", new NoteForm());
        model.addAttribute("credentialForm", new CredentialForm());
        model.addAttribute("tab", "tabCredential");
        model.addAttribute("encryptionService", encryptionService);
    }

}
