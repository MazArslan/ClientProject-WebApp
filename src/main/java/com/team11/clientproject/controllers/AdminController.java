package com.team11.clientproject.controllers;

import com.team11.clientproject.repositories.ExportDataRepository;
import com.team11.clientproject.services.*;
import com.team11.clientproject.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequestMapping("/admin/stats")
@Controller
public class AdminController {
    private final ImagesService imagesService;
    private final UsersService usersService;
    private final VotingService votingService;
    private final StorageService storageService;
    private final ExportDataService exportDataService;


    @Autowired
    public AdminController(ImagesService imagesService, UsersService usersService, VotingService votingService, StorageService storageService, ExportDataService exportDataService) {
        this.imagesService = imagesService;
        this.usersService = usersService;
        this.votingService = votingService;
        this.storageService = storageService;
        this.exportDataService = exportDataService;
    }

    /**
     * Gets a list of all users and their trustworthiness
     */
    @GetMapping("/users")
    public String mapUsers(Model model) {
        model.addAttribute("usersTrustworthiness", usersService.getAllUserTrustworthiness());
        return "adminStatisticsUser";

    }

    /**
     * Gets the list of users and their trustworthiness
     *
     * @return
     */
    @GetMapping("/images")
    public String getUsers(Model model) {
        model.addAttribute("images", imagesService.getImagesDescription());
        return "adminStatisticsImages";
    }

    /***
     * Gets a specific image and its ranking
     */
    @GetMapping("/images/{id}")
    public String getAdminImage(@PathVariable int id, Model model) {
        var image = votingService.getImageById(id).orElseThrow();
        model.addAttribute("image", imagesService.getAdminImageDTO(image));
        return "adminStatisticsImage";
    }

    @GetMapping("/images/upload")
    public String uploadImageForm() {
        return "imageUpload";
    }

    @PostMapping("/images/upload")
    public String uploadImage(@RequestParam("image") MultipartFile file, @RequestParam String trustworthy, Model model, @RequestParam("additional") MultipartFile[] additionalImages) {
        var anyNonImages = Arrays.stream(additionalImages)
                .map(MultipartFile::getOriginalFilename)
                .anyMatch(rs -> {
                    return !storageService.isImage(rs);
                });
        if (!storageService.isImage(file.getOriginalFilename()) || anyNonImages) {
            model.addAttribute("error", true);
            return "imageUpload";
        } else {
            var path = storageService.persistImage(file);
            var additionalImagePaths = Arrays.stream(additionalImages).map(storageService::persistImage).collect(Collectors.toList());
            votingService.addImage(path, Optional.ofNullable(StringUtils.stringToBoolean(trustworthy)), additionalImagePaths);
        }

        return "redirect:/admin/stats/images";
    }

    @GetMapping("/download")
    public ResponseEntity download() {
        var textResult = StringUtils.convertListOfTypeToJson(exportDataService.getAllDatabaseInformation());
        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"data.json\"");
        return new ResponseEntity(textResult, headers, HttpStatus.OK);
    }
}
