package paypals.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import paypals.PayPalsTest;
import paypals.Person;
import paypals.exception.ExceptionMessage;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;

class UnpaidCommandTest extends PayPalsTest {

    @BeforeEach
    public void setUpUnpaid() {
        callCommand(new AddCommand("d/lunch n/John f/Jane a/28.0"));
        callCommand(new AddCommand("d/tickets n/John f/Betty a/23.53 f/Jane a/20.21 f/Bob a/38.10"));
        callCommand(new PaidCommand("n/Jane i/1"));
    }
    @Test
    public void markOnePersonAsUnpaid_oneFriendMarkedAsPaid_personMarkedAsUnpaid() {
        callCommand(new UnpaidCommand("n/Jane i/1"));
        Person jane = activityManager.getActivity(0).getFriend("Jane");
        assertFalse(jane.hasPaid(), "Jane should have been marked as unpaid");
    }

    @Test
    public void testOutOfBoundsIdentifier_oneFriendMarkedAsPaid_throwsException(){
        try {
            callCommand(new UnpaidCommand("unpaid n/Jane i/2"));
        } catch (Exception e) {
            assertException(e, ExceptionMessage.OUTOFBOUNDS_IDENTIFIER);
        }
    }

    @Test
    public void testInvalidFormat_invalidCommand_throwsException(){
        try {
            callCommand(new UnpaidCommand("invalid"));
        } catch (Exception e) {
            assertException(e, ExceptionMessage.INVALID_FORMAT);
        }
    }

    @Test
    public void testMarkAllAsUnpaid_everyoneMarkedAsPaid_everyoneMarkedAsUnpaid() {
        callCommand(new PaidCommand("n/John i/2"));
        callCommand(new UnpaidCommand("n/John i/1"));
        Collection<Person> friends = activityManager.getActivity(1).getAllFriends();
        for (Person friend : friends) {
            assertFalse(friend.hasPaid(), friend.getName()
                    + " should have been marked as unpaid");
        }
    }
}
