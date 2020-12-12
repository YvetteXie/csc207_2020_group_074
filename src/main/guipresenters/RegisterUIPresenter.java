package main.guipresenters;

import main.controllers.AuthController;
import main.controllers.ProgramController;
import main.gui_interface.*;
import main.guilisteners.BackButtonListener;
import main.guilisteners.RegisterUIListener;

/**
 * The presenter for <code>RegisterUI</code>
 *
 * @author Steven Yuan
 */
public class RegisterUIPresenter implements RegisterUIListener, BackButtonListener {

    IRegisterUI iRegisterUI;
    ProgramController programController;
    AuthController authController;
    ILandingUI iLandingUI;
//    RegisterMessageSuccessfulUI registerMessageSuccessfulUI;
//    RegisterMessageErrorUI iRegisterMessageErrorUI;
    IRegisterMessageSuccessfulUI iRegisterMessageSuccessfulUI;
    IRegisterMessageErrorUI iRegisterMessageErrorUI;

    public RegisterUIPresenter(IRegisterUI registerUI, ProgramController programController) {
        this.iRegisterUI = registerUI;
        this.programController = programController;
        this.authController = programController.getAuthController();
        registerUI.addRegisterUIListener(this);
        registerUI.addBackButtonListener(this);
    }
    @Override
    public void onConfirmButtonClicked() {
            String userType = iRegisterUI.getUserType();
            String username = iRegisterUI.getUserName();
            String password = iRegisterUI.getPwd();
            if (!userType.equals("Attendee") && !userType.equals("Organizer")) {
                programController.saveForNext();
                iRegisterMessageErrorUI = iRegisterUI.goToErrorUI();
                new RegisterMessageErrorPresenter(iRegisterMessageErrorUI, programController);
            }
            boolean success = this.authController.registerUser(
                    username, password, userType);
            if (!success) {
                programController.saveForNext();
                iRegisterMessageErrorUI = iRegisterUI.goToErrorUI();
                new RegisterMessageErrorPresenter(iRegisterMessageErrorUI, programController);
            }
            else {
                programController.saveForNext();
                iRegisterMessageSuccessfulUI = iRegisterUI.goToSuccessfulUI();
                new RegisterMessageSuccessfulPresenter(
                        iRegisterMessageSuccessfulUI, programController);
            }
    }

    @Override
    public void onBackButtonClicked() {
        programController.saveForNext();
        iLandingUI = iRegisterUI.goToLandingUI();
        new LandingUIPresenter(iLandingUI, programController);
    }
}