package net.glm.motohelp.contracts;

import net.glm.motohelp.User;

/**
 * Created by Michael on 12/09/2017.
 */

public interface ViewPresentorContracts {

    interface Presenter {

        void setMainView(ViewPresentorContracts.MainView mainView);

        User getUser();

        void acceptUserData(User user);
    }

    interface MainView {

        void runUserDataActivity();

    }
}
