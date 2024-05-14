package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;

    @Override
    public Optional<User> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    @Override
    public List<Training> getTrainings() {
        log.info("Pobieram wszystkie treningi.");

        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getTrainingsByUserId(Long userId) {
        log.info("Pobrano dane użytkownika o ID " +userId);

        return trainingRepository.findAll()
                .stream()
                .filter(training -> training.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Training> getTrainingsByActivity(ActivityType activity) {
        log.info("Pobrano treningi według aktywności: " + activity);

        return trainingRepository.findAll()
                .stream()
                .filter(training -> training.getActivityType().equals(activity))
                .collect(Collectors.toList());
    }

    @Override
    public List<Training> getTrainingsByAfterDate(String dateString) {
        Date dateTemp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            dateTemp = sdf.parse(dateString);
        }
        catch (Exception e) {
          e.printStackTrace();
        }

        final Date date = dateTemp;

        log.info("Pobrano wszystkie treningi po zadanej dacie: " + date);

        return trainingRepository.findAll()
                .stream()
                .filter((training -> training.getEndTime().compareTo(date) > 0))
                .collect(Collectors.toList());
    }

    public Training addNewTraining(Training training) {
        log.info("Dodanie nowego treningu");

        return trainingRepository.save(training);
    }

}
