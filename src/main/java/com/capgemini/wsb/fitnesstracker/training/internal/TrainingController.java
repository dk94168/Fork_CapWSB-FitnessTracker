package com.capgemini.wsb.fitnesstracker.training.internal;



import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/training")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingProvider trainingProvider;
    private final TrainingMapper trainingMapper;

    @GetMapping
    public List<TrainingTO> getTrainings() {
        return trainingProvider.getTrainings()
                .stream()
                .map(trainingMapper::toTraining)
                .collect(Collectors.toList());
    }
}
