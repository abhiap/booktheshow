package org.aap.booking.controller;

import org.aap.booking.dto.BrowseTheatresRequest;
import org.aap.booking.dto.BrowseTheatresResponse;
import org.aap.booking.dto.ErrorResponse;
import org.aap.booking.exception.ResourceNotFoundException;
import org.aap.booking.service.TheatresService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/browse")
@CrossOrigin(origins = "*")
public class BrowseTheatresController {
    
    private final TheatresService theatresService;

    public BrowseTheatresController(TheatresService theatresService) {
        this.theatresService = theatresService;
    }

    @PostMapping("/theatres")
    public ResponseEntity<?> browseTheatres(@Valid @RequestBody BrowseTheatresRequest request) {
        try {
            BrowseTheatresResponse response = theatresService.browseTheatres(request);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("INTERNAL_SERVER_ERROR", e.getMessage()));
        }
    }

    // Req: Browse theatres currently running the show (movie selected) in the town, including show
    // timing by a chosen date
    @GetMapping("/theatres")
    public ResponseEntity<?> browseTheatresGet(
            @RequestParam Long movieId,
            @RequestParam Long cityId,
            @RequestParam String showDate) {
        if (movieId == null || cityId == null || showDate == null) {
            // All search params are considered mandatory.
            return ResponseEntity.badRequest().build();
        }
        try {
            BrowseTheatresRequest request = new BrowseTheatresRequest(
                    movieId, 
                    cityId, 
                    java.time.LocalDate.parse(showDate)
            );
            BrowseTheatresResponse response = theatresService.browseTheatres(request);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("INTERNAL_SERVER_ERROR", e.getMessage()));
        }
    }
}
