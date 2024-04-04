package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.Meetings;

import java.util.List;
import java.util.Optional;

public interface MeetingsService {
    List<Meetings> getAllMeetings();
    Optional<Meetings> getMeetingsById(Long id);
    Meetings createMeetings(Meetings meetings);
    Meetings updateMeetings(Meetings meetings);
    void deleteMeetingsById(Long id);
}