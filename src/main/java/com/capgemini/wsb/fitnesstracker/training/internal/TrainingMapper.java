package com.capgemini.wsb.fitnesstracker.training.internal;

import org.hibernate.annotations.Comment;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {

    TrainingTO toTraining(Training training){
        return new TrainingTO(
                training.getId(),
                training.getUser(),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
            );
    }
}
