package main.guipresenters;

import main.controllers.MessageController;
import main.controllers.ProgramController;
import main.entities.User;
import main.gui_interface.IAttendeeMainUI;
import main.gui_interface.IAttendeeMessageUI;
import main.guilisteners.BackButtonListener;
import main.guilisteners.ListSelectionListener;
import main.guilisteners.SendButtonListener;
import main.usecases.UsersManager;

import javax.swing.event.ListSelectionEvent;

public class AttendeeMessageUIPresenter implements BackButtonListener, SendButtonListener, ListSelectionListener {
    IAttendeeMessageUI iAttendeeMessageUI;
    ProgramController programController;
    MessageController messageController;

    public AttendeeMessageUIPresenter(IAttendeeMessageUI iAttendeeMessageUI, ProgramController programController) {
        this.iAttendeeMessageUI = iAttendeeMessageUI;
        this.programController = programController;
        this.messageController = programController.getMessageController();
        this.iAttendeeMessageUI.addBackButtonListener(this);
        this.iAttendeeMessageUI.addSendButtonListener(this);
        this.iAttendeeMessageUI.addListSelectionListener(this);
    }

    @Override
    public void onBackButtonClicked() {
        IAttendeeMainUI iAttendeeMainUI = iAttendeeMessageUI.goToAttendeeMainUI();
        new AttendeeMainUIPresenter(iAttendeeMainUI, this.programController);
    }

    @Override
    public void onSendButtonClicked() {
        String user = (String) this.iAttendeeMessageUI.getUsersList().getSelectedValue();
        if (user != null) {
            String message = iAttendeeMessageUI.getMessage();
            UsersManager usersManager = this.programController.getUsersManager();
            String username;
            int i = user.indexOf(",");
            String userID = usersManager.get;
            this.messageController.sendMessage(this.programController.getAuthController().fetchLoggedInUser(), userID, message);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
    }
}
