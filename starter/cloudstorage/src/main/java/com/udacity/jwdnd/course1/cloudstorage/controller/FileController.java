package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.MessageInfo;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;
    private final NoteService noteService;

    public FileController(FileService fileService, NoteService noteService) {
        this.fileService = fileService;
        this.noteService = noteService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model) {
        // check if file is empty
        if (file.isEmpty()) {
            model.addAttribute("messageFile", new MessageInfo("Please select a file to upload.", true));
            return "home";
        }

        //check if file already exists
        if (!fileService.isFileAvailable(file.getOriginalFilename())) {
            model.addAttribute("messageFile", new MessageInfo("File already exists.", true));
            return "home";
        }

        //upload file
        if (fileService.uploadFile(file) >= 0) {
            // return success response
            model.addAttribute("messageFile", new MessageInfo("You successfully uploaded " + file.getOriginalFilename() + '!', false));
        } else {
            // return success response
            model.addAttribute("messageFile", new MessageInfo("Error while uploading file, please try again.", true));
        }

        model.addAttribute("files", fileService.getAllFiles());
        return "home";
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable int fileId) {
        File file = fileService.getFile(fileId);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                + file.getFileName() + "\"").body(new
                        ByteArrayResource(file.getFileData()));
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, Model model) {
        if (fileService.deleteFile(fileId)) {
            model.addAttribute("messageFile", new MessageInfo("File deleted.", false));
        } else {
            model.addAttribute("messageFile", new MessageInfo("Error while deleting file.", true));
        }
        model.addAttribute("files", fileService.getAllFiles());
        return "home";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("notes", noteService.getAll());
        model.addAttribute("noteForm", new NoteForm());
        model.addAttribute("files", fileService.getAllFiles());
        model.addAttribute("tab", "tabFile");
    }

}
