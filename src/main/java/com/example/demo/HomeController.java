package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class HomeController {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    UserService userService;

    /*@RequestMapping("/")
    public String jobList(Model model){
        model.addAttribute("jobs", jobRepository.findAll());
        return "joblist";
    }*/


    @RequestMapping("/")
    public String jobList(Model model){
        model.addAttribute("jobs", jobRepository.findAll());
//        model.addAttribute("user",userService.getUser());
        if(userService.getUser() != null) {
            model.addAttribute("user_id", userService.getUser().getId());
        }
        return "joblist";
    }



    @RequestMapping("/add")
    public String addJob(Model model){
        model.addAttribute("job", new Job());
        return "jobform";
    }

    @PostMapping("/processjob")
    public String processForm(@ModelAttribute Job job, @RequestParam(name = "date") String date){
        String pattern = "yyyy-MM-dd'T'hh:mm";
        try{
            String formattedDate = date.substring(1,date.length()-1);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date realDate = simpleDateFormat.parse(formattedDate);
            job.setDate(realDate);
//            job.setUser(userService.getUser());
        }
        catch(java.text.ParseException e){
            e.printStackTrace();
        }
        //added**************
        job.setUser(userService.getUser());
        jobRepository.save(job);
        return "redirect:/";
    }

    //ADDED
//    @PostMapping("/process")
//    public String processForm(@Valid Job job, BindingResult result){
//        if(result.hasErrors()){
//            return "jobform";
//        }
//
//        job.setUser(userService.getUser());
//        jobRepository.save(job);
//        return "redirect:/";
//    }

    @PostMapping("/processsearch")
    public String searchResult(Model model, @RequestParam(name = "search") String search,
                               @RequestParam(name = "category") String category){

        if (category.equals("1")){
            model.addAttribute("jobs", jobRepository.findByTitleContainingIgnoreCase(search));
        }
        else if(category.equals("2")){
            model.addAttribute("jobs", jobRepository.findByAuthorContainingIgnoreCase(search));
        }
        else if (category.equals("3")){
            model.addAttribute("jobs", jobRepository.findByDescriptionContainingIgnoreCase(search));
        }
        else if (category.equals("4")){
            model.addAttribute("jobs", jobRepository.findByPhoneNum(search));
        }
        return "searchlist";
    }

    @RequestMapping("/detail/{id}")
    public String showJob(@PathVariable("id") long id, Model model) {
        model.addAttribute("job", jobRepository.findById(id).get());
        return "showdetail";
    }

    @RequestMapping("/update/{id}")
    public String updateJob(@PathVariable("id") long id, Model model) {
        model.addAttribute("job", jobRepository.findById(id).get());
        return "jobform";
    }

    @RequestMapping("/delete/{id}")
    public String deleteJob(@PathVariable("id")long id) {
        jobRepository.deleteById(id);
        return "redirect:/";
    }


}