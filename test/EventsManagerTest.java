import main.entities.Attendee;
import main.entities.Event;
import main.entities.User;
import main.usecases.EventBuilder;
import main.usecases.EventFactory;
import main.usecases.EventsManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.CoreMatchers.containsString;

public class EventsManagerTest {
    EventsManager eventsManager;
    LocalDateTime time1;
    LocalDateTime time2;
    UUID room1;
    UUID room2;
    UUID speaker1;
    UUID speaker2;

    @Before
    public void setUp(){
        this.eventsManager = new EventsManager();
        time1 = LocalDateTime.of(2020, 1,1, 12, 0 );
        time2 = LocalDateTime.of(2020, 1,1, 16, 0 );
        room1 = UUID.randomUUID();
        room2 = UUID.randomUUID();
        speaker1 = UUID.randomUUID();
        speaker2 = UUID.randomUUID();
    }

    public EventBuilder setUpEvent(String title, LocalDateTime time, UUID roomID, UUID speakerID){
        EventBuilder eb = new EventBuilder();
        eb.setTitle(title);
        eb.setRoom(roomID);
        eb.setTime(time);
        eb.setSpeaker(speakerID);
        return eb;
    }

    @Test
    public void testScheduleEvent(){
        //test initial
        EventBuilder e1 = setUpEvent("Event1", time1, room1, speaker1);
        Assert.assertTrue(eventsManager.scheduleEvent(e1));
        //test same room same speaker
        EventBuilder e2 = setUpEvent("Event2", time2, room1, speaker1);
        Assert.assertTrue(eventsManager.scheduleEvent(e2));
        //test speaker conflict
        EventBuilder e3 = setUpEvent("Event3", time1, room2, speaker1);
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> eventsManager.scheduleEvent(e3));
        String expected1 = "Time conflict for speaker " + speaker1 + " with Event #" + "Event1";
        Assert.assertEquals(expected1, exception1.getMessage());
        //test room conflict
        EventBuilder e4 = setUpEvent("Event4", time1, room1, speaker2);
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> eventsManager.scheduleEvent(e4));
        String expected2 = "Time conflict for room " + room1 + " with Event #" + "Event1";
        Assert.assertEquals(expected2, exception2.getMessage());
    }

    @Test
    public void testRemoveEvent(){
        //test empty
        EventBuilder e1 = setUpEvent("Event1", time1, room1, speaker1);
        Assert.assertFalse(eventsManager.removeEvent(e1.toEvent().getId()));
        //test not in schedule
        eventsManager.scheduleEvent(e1);
        EventBuilder e2 = setUpEvent("Event2", time2, room1, speaker1);
        Assert.assertFalse(eventsManager.removeEvent(e2.toEvent().getId()));
        //test success removes
        eventsManager.scheduleEvent(e2);
        UUID e1id =  eventsManager.getEvents().get(0).getId();
        UUID e2id =  eventsManager.getEvents().get(1).getId();
        Assert.assertTrue(eventsManager.removeEvent(e1id));
        Assert.assertTrue(eventsManager.removeEvent(e2id));
    }

    @Test
    public void testUserEvents(){
        EventBuilder e1 = setUpEvent("Event1", time1, room1, speaker1);
        eventsManager.scheduleEvent(e1);
        EventBuilder e2 = setUpEvent("Event2", time2, room1, speaker1);
        eventsManager.scheduleEvent(e2);
        User u1 = new Attendee("user1@email.com", "password");
        eventsManager.getEvents().get(0).addAttendees(u1.getId());
        //test single
        Assert.assertEquals(1, eventsManager.getUserEvents(u1.getId()).size());
        Assert.assertTrue(eventsManager.getEvents().contains(eventsManager.getEvents().get(0)));
        //test multiple
        eventsManager.getEvents().get(1).addAttendees(u1.getId());
        Assert.assertEquals(2, eventsManager.getUserEvents(u1.getId()).size());
        Assert.assertTrue(eventsManager.getEvents().contains(eventsManager.getEvents().get(0)));
        Assert.assertTrue(eventsManager.getEvents().contains(eventsManager.getEvents().get(1)));
    }


    @Test
    public void testToString(){
        EventBuilder e1 = setUpEvent("Event1", time1, room1, speaker1);
        eventsManager.scheduleEvent(e1);
        Event expected1 = eventsManager.getEvents().get(0);
        EventBuilder e2 = setUpEvent("Event2", time2, room1, speaker1);
        eventsManager.scheduleEvent(e2);
        Event expected2 = eventsManager.getEvents().get(1);
        EventBuilder e3 = setUpEvent("Event3", time2, room2, speaker2);
        eventsManager.scheduleEvent(e3);
        Event expected3 = eventsManager.getEvents().get(2);
        String expected = "Events: \n" + expected1.toString() + "\n" + expected2.toString() +
                "\n" + expected3.toString() + "\n";
        Assert.assertEquals(expected, eventsManager.toString());
    }

}