package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.MessageInfo;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/note/add")
    public String addNote(NoteForm noteForm, Model model) {
        //set model before check errors
        model.addAttribute("notes", noteService.getAll());

        //verify if the note already exists
        if (noteService.alreadyExists(noteForm.getNoteTitle())) {
            model.addAttribute("messageNote", new MessageInfo("A note already exists with this title.", true));
            return "home";
        }

        //insert or update note depending on noteId value
        if (noteService.insertOrUpdate(noteForm)) {
            model.addAttribute("messageNote", new MessageInfo("Note added successfully.", false));
        } else {
            model.addAttribute("messageNote", new MessageInfo("Error while adding/updating note.", true));
        }

        model.addAttribute("notes", noteService.getAll());
        return "home";
    }

}
