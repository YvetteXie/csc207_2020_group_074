package main.guipresenters;

import main.controllers.AuthController;
import main.controllers.EventController;
import main.controllers.ProgramController;
import main.gui_interface.IEventSignUpUI;
import main.gui_interface.IViewUserEventsUI;
import main.guilisteners.BackButtonListener;
import main.guilisteners.CancelEventButtonListener;

@SuppressWarnings("FieldCanBeLocal")

public class ViewUserEventsUIPresenter
        implements BackButtonListener, CancelEventButtonListener {

    private ProgramController programController;
    private AuthController authController;
    private EventController eventController;
    private IViewUserEventsUI iViewUserEventsUI;
    private IEventSignUpUI iEventSignUpUI;

    public ViewUserEventsUIPresenter(IViewUserEventsUI viewUserEventsUI,
                                     ProgramController programController) {
        this.iViewUserEventsUI = viewUserEventsUI;
        this.programController = programController;
        this.authController = programController.getAuthController();
        this.eventController = programController.getEventController();
        iViewUserEventsUI.addBackButtonListener(this);
        iViewUserEventsUI.addRegisterEventButtonListener(this);
    }

    @Override
    public void onBackButtonClicked() {
        programController.saveForNext();
        iEventSignUpUI = iViewUserEventsUI.goToEventSignUpUI();
        new EventSignUpUIPresenter(iEventSignUpUI, programController);
    }

    @Override
    public void onCancelEventButtonClicked() {
        String userId = authController.fetchLoggedInUser();
        try {
            if (eventController.getSignupEvents(userId).size() > 0) {
                int eventIndex = iViewUserEventsUI.getEventIndexFromList();
                String eventId = eventController.getEventId(eventIndex);
                if (eventController.signupEvent(eventId, userId)) {
                    iViewUserEventsUI.cancelNewEventSuccessful();
                } else {
                    iViewUserEventsUI.cancelNewEventError();
                }
            }
        } catch (NullPointerException | IllegalArgumentException |
                IndexOutOfBoundsException e) {
            iViewUserEventsUI.cancelNewEventError();
        }
    }
}