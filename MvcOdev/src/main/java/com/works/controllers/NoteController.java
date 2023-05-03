package com.works.controllers;

import com.works.props.Note;
import com.works.props.User;
import com.works.services.NoteService;
import com.works.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class NoteController {

    int status = -1;
    String message = "";
    NoteService service = new NoteService();
    UserService serviceUser = new UserService();

    @GetMapping("/note")
    public String note(Model model, @RequestParam(defaultValue = "1") int pa){
        model.addAttribute("notes",service.notes(pa));
        int countnid = serviceUser.totalCountNote();
        model.addAttribute("countnid",countnid);
        int pageNote = countnid %2 == 0 ? countnid / 2 : (countnid/2) + 1;
        model.addAttribute("pageNote",pageNote);
        model.addAttribute("pa",pa);
        return "note";
    }

    @PostMapping("/noteSave")
    public String noteSave(Note note){
        NoteService service = new NoteService();
        int status = service.noteSave(note);
        if (status > 0)
            return "redirect:/note";
        System.out.println(note);
        return "redirect:/note";
    }

    @GetMapping("/noteDelete/{nid}")
    public String noteDelete(@PathVariable int nid) {
        status = service.deleteNote(nid);
        if (status > 0) {
            message = "Delete Success - " + nid;
        }else {
            message = "Delete Fail - " + nid;
        }
        return "redirect:/note";
    }

    @PostMapping("/noteUpdate")
    public String userUpdate (Note note){
        service.updateNote(note);
        return "redirect:/note";
    }
}
