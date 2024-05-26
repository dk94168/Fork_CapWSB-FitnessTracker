package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private final UserRepository userRepository;

    private final UserService userService;

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
        log.info("Pobrano dane użytkownika o ID " + userId);

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Date date = dateTemp;

        log.info("Pobrano wszystkie treningi po zadanej dacie: " + date);

        return trainingRepository.findAll()
                .stream()
                .filter((training -> training.getEndTime().compareTo(date) > 0))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateTraining(Long trainingId, Training training) {
        Optional<Training> trainingOptional = trainingRepository.findById(trainingId);

        if (trainingOptional.isPresent()) {
            log.info("Pobrano właściwy trening");

            Training trainingExisting = trainingOptional.get();

            if (training.getUser() != null) {
                //trainingExisting.setUser(training.getUser());

                Optional<User> userOptional = userRepository.findById(training.getUser().getId());

                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    log.info("Nowy użytkownik:" + user.getId());

                    trainingExisting.setUser(user);
                } else {
                    log.info("Nie znaleziono użytkownika o ID: " + training.getUser().getId());
                    throw new IllegalArgumentException("Nie znaleziono użytkownika o ID: " + training.getUser().getId());
                }
            }

            if (training.getStartTime() != null) {
                trainingExisting.setStartTime(training.getStartTime());
            }

            if (training.getEndTime() != null) {
                trainingExisting.setEndTime(training.getEndTime());
            }

            if (training.getActivityType() != null) {
                trainingExisting.setActivityType(training.getActivityType());
            }

            // Typ prosty: double przy wartości null zwraca zawsze 0.0, a dystans treningu nie może mieć takiej wartości
            if (!(training.getDistance() == 0.0)) {
                trainingExisting.setDistance(training.getDistance());
            }

            // Tup prosty: double przy wartości null zwraca wartość 0.0
            if (!(training.getAverageSpeed() == 0.0)) {
                trainingExisting.setAverageSpeed(training.getAverageSpeed());
            }

            log.info("Aktualizacja treningu o ID {}", trainingId);
            trainingRepository.save(trainingExisting);
        } else {
            log.info("Nie znaleziono treningu o ID: " + trainingId);
            throw new IllegalArgumentException("Nie znaleziono treningu o ID: " + trainingId);
        }

    }


    @Override
    @Transactional
    public Training addNewTraining(Training training) {
        log.info("Dodanie nowego treningu");
        User user = training.getUser();

        if (user.getId() == null) {
            log.info("Nie ma użytkownika o ID:" + user.getId() );
            User savedUser = userRepository.save(user);
            training.setUser(savedUser);
        }
        else{
            log.info("Użytkownik już istnieje o ID:" + user.getId() );
        }

        Training newTraining = new Training(user, training.getStartTime(), training.getEndTime(), training.getActivityType(), training.getDistance(), training.getAverageSpeed());

        log.info("Dodano nowy trening");
        return trainingRepository.save(newTraining);
    }
}
