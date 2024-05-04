package com.capgemini.wsb.fitnesstracker.training.internal;



import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.rmi.server.LogStream.log;

@RestController
@RequestMapping("/v1/training")
@RequiredArgsConstructor
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
}
