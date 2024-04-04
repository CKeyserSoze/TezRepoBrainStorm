package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.Meetings;
import com.cobanoglu.denemebrain.repository.MeetingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeetingsServiceImpl implements MeetingsService{

    private final MeetingsRepository meetingsRepository;

    @Autowired
    public MeetingsServiceImpl(MeetingsRepository meetingsRepository) {
        this.meetingsRepository = meetingsRepository;
    }

    @Override
    public List<Meetings> getAllMeetings() {
        return meetingsRepository.findAll();
    }

    @Override
    public Optional<Meetings> getMeetingsById(Long id) {
        return meetingsRepository.findById(id);
    }

    @Override
    public Meetings createMeetings(Meetings meetings) {
        return meetingsRepository.save(meetings);
    }

    @Override
    public Meetings updateMeetings(Meetings meetings) {
        return meetingsRepository.save(meetings);
    }

    @Override
    public void deleteMeetingsById(Long id) {
        meetingsRepository.deleteById(id);
    }
}
