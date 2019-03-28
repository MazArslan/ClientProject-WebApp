package com.team11.clientproject.controllers.api;

import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.SampleImage;
import com.team11.clientproject.repositories.ExportDataRepository;
import com.team11.clientproject.services.UsersService;
import com.team11.clientproject.services.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/voting")
public class VotingApiController {

    private VotingService votingService;
    private final UsersService usersService;

    @Autowired
    public VotingApiController(VotingService votingService, UsersService usersService) {
        this.votingService = votingService;
        this.usersService = usersService;
    }

    /**
     * Gets an image that the user will vote on
     *
     * @return image - the image voted on
     */
    @GetMapping("/image")
    public ResponseEntity getImageToVoteOn(Authentication authentication, @RequestParam(defaultValue = "0") int numberOfImages) {
        var user = usersService.getUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        var encouragement = votingService.getEncouragementForNumberOfImages(numberOfImages);
        if (encouragement.isPresent()) {
            return new ResponseEntity(encouragement.get(), HttpStatus.OK);
        }
        return new ResponseEntity(votingService.getImageToVoteOn(user), HttpStatus.OK);
    }

    /**
     * Casts a vote on an image
     *
     * @param imageId the image ID
     * @param isUp    if the vote is up or not
     * @return a success status
     */
    @PostMapping("/vote/{imageId}")
    public ResponseEntity castVote(@PathVariable int imageId, @RequestParam boolean isUp, Authentication authentication) {

        var user = usersService.getUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        var image = votingService.getImageById(imageId).orElseThrow();
        votingService.voteForImage(image, isUp, user);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Gets all the sample images for a tutorial
     *
     * @return the sample images
     */
    @GetMapping("/sampleimages")
    public List<SampleImage> getSampleImages() {
        return votingService.getSampleImages();
    }


}
