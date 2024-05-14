package com.capgemini.wsb.fitnesstracker.training.internal;



import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import static java.rmi.server.LogStream.log;

@RestController
@RequestMapping("/v1/training")
@RequiredArgsConstructor
@Slf4j
public class TrainingController {

    private final TrainingProvider trainingProvider;
    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    @GetMapping("/allTrainings")
    public List<TrainingTO> getTrainings() {
        return trainingProvider.getTrainings()
                .stream()
                .map(trainingMapper::toTraining)
                .collect(Collectors.toList());
    }

    @GetMapping("/byUserId/{userId}")
    public List<TrainingTO> getTrainingsByUserId(@PathVariable Long userId){

        return trainingService.getTrainingsByUserId(userId)
                    .stream()
                    .map(trainingMapper::toTraining).toList();
    }

    @GetMapping("/byActivity/{activity}")
    public List<TrainingTO> getTrainingsByActivity(@PathVariable ActivityType activity){

        return trainingService.getTrainingsByActivity(activity)
                    .stream()
                    .map(trainingMapper::toTraining).toList();
    }

    @GetMapping("/byAfterDate/{dateString}")
    public List<TrainingTO> getTrainingsByAfterDate(@PathVariable String dateString){

        return trainingService.getTrainingsByAfterDate(dateString)
                    .stream()
                    .map(trainingMapper::toTraining).toList();
    }

    @PostMapping("/addNewTraining")
    public ResponseEntity<Training> addNewTraining(@RequestBody Training training) {
        try {
            Training newTraining = trainingService.addNewTraining(training);
            return ResponseEntity.ok(newTraining);
        }
        catch (IllegalArgumentException e) {
            log.info("Błąd podczas dodawania nowego treningu");

            return ResponseEntity.badRequest().body(null);
        }
    }

}
