package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    private final UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    /**
     * Get all notes
     *
     * @return list of notes
     */
    public List<Note> getAll() {
        return noteMapper.getAllNotes();
    }

    /**
     * Insert note
     *
     * @param noteForm note to insert
     * @return true if insert/update ok
     */
    public boolean insertOrUpdate(NoteForm noteForm) {
        if (noteForm.getNoteId() == null || noteForm.getNoteId() < 0) {
            //insert method
            Note note = new Note(null, noteForm.getNoteTitle(), noteForm.getNoteDescription(), userService.getConnectedUserId());
            return noteMapper.insert(note) > 0;
        }
        //update method
        Note note = new Note(noteForm.getNoteId(), noteForm.getNoteTitle(), noteForm.getNoteDescription(), userService.getConnectedUserId());
        return noteMapper.update(note);
    }

    /**
     * Delete note corresponding to id parameter
     *
     * @param noteId id of the note to delete
     * @return true if deletion ok
     */
    public boolean delete(int noteId) {
        return noteMapper.delete(noteId);
    }
}
