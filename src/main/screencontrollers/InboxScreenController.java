package main.screencontrollers;

import main.controllers.AuthController;
import main.controllers.InboxController;
import main.controllers.ProgramController;
import main.presenters.InboxScreen;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InboxScreenController extends ScreenController {

    InboxScreen presenter = new InboxScreen();
    InboxController inboxController;
    Map<UUID, String> messages;

    List<String> messageList;
    List<UUID> messageIndexes;

    public InboxScreenController(ProgramController programController) {
        super(programController);
        this.inboxController = new InboxController(programController.getMessageManager(), programController.getInboxManager(), programController.getUsersManager());
    }

    public void start() {
        this.presenter.welcomeMessage();
        this.optionsPrompt();
        this.end();

    }

    private void optionsPrompt() {
        this.presenter.optionsPrompt();
        String choice = this.scanner.nextLine();
        switch (choice) {
            case "0":
                this.programController.setCurrentScreenController(this.programController.getPreviousScreenController());
                return;
            case "1":
                this.listMessages();
                break;
            default:
                this.presenter.invalidOption();
                this.optionsPrompt();
                break;
        }
    }

    private void fetchMessages() {
        AuthController currentAuthController = this.programController.getAuthController();
        UUID currentUserId = currentAuthController.fetchLoggedInUser();
        this.messages = this.inboxController.getMessagesOfUser(currentUserId);
        this.messageIndexes = new LinkedList<UUID>();
        this.messageList = new LinkedList<String>();

        for (Map.Entry<UUID, String> entry : this.messages.entrySet()) {
            this.messageIndexes.add(entry.getKey());
            this.messageList.add(entry.getValue());
        }

    }

    private void listMessages() {
        this.fetchMessages();
        if (this.messageList.size() == 0) {
            this.presenter.inboxIsEmpty();
            this.optionsPrompt();
            return;
        }
        this.presenter.listMessages(this.messageList);
        this.messageDetailPrompt();

    }

    private void messageDetailPrompt() {
        this.presenter.selectMessagePrompt();
        String choice = this.scanner.nextLine();
        if (choice.equals("0")) {
            this.optionsPrompt();
            return;
        }

        try {
            int messageNum = Integer.parseInt(choice);
            if (messageNum > this.messages.size()) {
                this.presenter.invalidOption();
                this.messageDetailPrompt();
                return;
            }
            UUID messageId = this.messageIndexes.get(messageNum - 1);
            this.openMessageDetailScreen(messageId);
        } catch (NumberFormatException e) {
            this.presenter.invalidOption();
            this.messageDetailPrompt();
            return;
        }


    }

    private void openMessageDetailScreen(UUID messageId) {
        ScreenController messageDetailScreen = new MessageDetailScreenController(this.programController, messageId);
        this.programController.setPreviousScreenController(this);
        this.programController.setCurrentScreenController(messageDetailScreen);
        this.programController.nextScreenController();
    }
}
