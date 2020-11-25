package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.MessageInfo;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;
    private final FileService fileService;

    public NoteController(NoteService noteService, FileService fileService) {
        this.noteService = noteService;
        this.fileService = fileService;
    }

    @PostMapping("/add")
    public String addNote(NoteForm noteForm, Model model) {
        //insert or update note depending on noteId value
        if (noteService.insertOrUpdate(noteForm)) {
            model.addAttribute("messageNote", new MessageInfo("Note added/updated successfully.", false));
        } else {
            model.addAttribute("messageNote", new MessageInfo("Error while adding/updating note.", true));
        }
        model.addAttribute("notes", noteService.getAll());
        return "home";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Model model) {
        if (noteService.delete(noteId)) {
            model.addAttribute("messageNote", new MessageInfo("Note deleted.", false));
        } else {
            model.addAttribute("messageNote", new MessageInfo("Error while deleting note.", true));
        }
        model.addAttribute("notes", noteService.getAll());
        return "home";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("notes", noteService.getAll());
        model.addAttribute("noteForm", new NoteForm());
        model.addAttribute("files", fileService.getAllFiles());
        model.addAttribute("tab", "tabNote");
    }
}
