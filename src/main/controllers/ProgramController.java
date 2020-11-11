package main.controllers;

import main.presenters.MainScreen;
import main.presenters.Screen;
import main.usecases.ChatRoomManager;
import main.usecases.ContactsManager;
import main.usecases.MessageManager;
import main.usecases.UsersManager;

public class ProgramController implements ProgramInterface{
    UsersManager usersManager;
    ContactsManager contactsManager;
    ChatRoomManager chatRoomManager;
    AuthController authController;
    UserController currentController;
    MessageController messageController;
    EventController eventController;
    Screen currentScreen;

    public ProgramController() {
        this.usersManager = new UsersManager();
        this.contactsManager = new ContactsManager();
        this.chatRoomManager = new ChatRoomManager();
        this.authController = new AuthController(usersManager);
        this.currentScreen = new MainScreen(this);
        this.messageController = new MessageController();
        this.eventController = new EventController();
    }

    public void start() {
        this.currentScreen.start();

    }

    public void nextScreen() {
        this.currentScreen.start();
    }

    public void setScreen(Screen screen) {
        this.currentScreen = screen;
    }
    public UserController getCurrentController() {
        return this.currentController;
    }

    public AuthController getAuthController() {
        return this.authController;
    }

    public UsersManager getUsersManager() {
        return this.usersManager;
    }

    public ContactsManager getContactsManager() {
        return this.contactsManager;
    }

    public ChatRoomManager getChatRoomManager() {
        return this.chatRoomManager;
    }

    public MessageController getMessageController() {
        return this.messageController;
    }

    public EventController getEventController() {
        return this.eventController;
    }
}
