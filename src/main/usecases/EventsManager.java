package main.usecases;

import main.entities.Event;
import java.util.*;

/**
 * The EventsManager holds a list of Events, and modify Event with its corresponding Users.
 *
 * @author Haoze Huang
 * @version 1.0
 * @since 2020-10-31
 */

public class EventsManager {

    private Map<UUID,Event> schedule = new HashMap<>();

    /**
     * Add an Event to schedule with the given time. Throw an existent exception if
     * the given time is conflict with another Event
     *
     * @param newEvent  that to be added
     * @return check for event being added
     */
    public boolean scheduleEvent(Event newEvent) {
        for(UUID id : schedule.keySet()){
            //if time conflict
            Event e = schedule.get(id);
            if ((e.getRoomID() == newEvent.getRoomID()) && (e.getTime() == newEvent.getTime())){
                throw new IllegalArgumentException("Time conflict with Event #" + e.getId());
            }
        }
        schedule.put(newEvent.getId(), newEvent);
        return true;
    }


    /**
     * Remove an Event from the EventSchedule, if schedule is empty or Event is not in
     * schedule, do nothing.
     *
     * @param canceledEventId that needs to be removed
     * @return check for successful removal
     */
    public boolean removeEvent(UUID canceledEventId) {
        if(schedule.containsKey(canceledEventId)){
            schedule.remove(canceledEventId);
            return true;
        }
        return false;
    }


    /**
     * Get the list of events for a User given email
     *
     * @param userId to be get events from
     * @return userEvents
     */
    public ArrayList<Event> getUserEmails(UUID userId) {
        ArrayList<Event> userEvents = new ArrayList<>();
        for(UUID i : schedule.keySet()){
            for (UUID id : schedule.get(i).getAttendeesID()){
                if(id == userId) userEvents.add(schedule.get(i));
            }
        }
        return userEvents;
    }


    /**
     * Get list of events
     *
     * @return events
     */
    public ArrayList<Event> getEvents() {
        ArrayList<Event> events = new ArrayList<>();
        for (UUID i : schedule.keySet()){
            events.add(schedule.get(i));
        }
        return events;
    }


    /**
     * String representation of EventsManager
     *
     * @return String representation of EventsManager
     */
    public String toString() {
        String s = "Events: \n";
        for (UUID i : schedule.keySet()){
            Event e = schedule.get(i);
            String eToString = "Event #" + e.getId() + " : " + e.getTitle() +
                    " at room # " + e.getRoomID() +
                    " spoken by speaker #" + e.getSpeakerID() +
                    " at time " + e.getTime() + "\n";
            s += eToString;
        }
        return s;
    }
}