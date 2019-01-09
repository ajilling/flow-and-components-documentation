package com.vaadin.flow.tutorial.components;

import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.ShortcutActions;
import com.vaadin.flow.component.ShortcutRegistration;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.tutorial.annotations.CodeFor;

@CodeFor("components/tutorial-flow-shortcut.asciidoc")
public class Shortcuts {

    /**
     * First example
     */
    public void smallExecExample() {
        TextField username = new TextField();
        ShortcutActions.exec(this::login, username).on(Key.ENTER);
    }

    /**
     * Second example
     */
    public void smallScopeExample() {
        ShortcutRegistration shortcutRegistration = ShortcutActions
                .exec(() -> {});
        // these are the actual rows
        Div myDivComponent = new Div();
        shortcutRegistration.scope(myDivComponent);
    }

    /**
     * Third example(s) (contains 4 one-liners
     */
    public void shortUsablityExamples()  {
        // helpers:
        Div component = new Div();
        Div myScopeComponent1 = new Div();
        Div myScopeComponent2 = new Div();
        ShortcutRegistration shortcutRegistration = ShortcutActions.exec(() ->{});

        // ex. 1
        shortcutRegistration.on('K').alt();
        // ex. 2
        shortcutRegistration.bindLifecycleTo(component);
        // ex. 3
        shortcutRegistration.scope(myScopeComponent1, myScopeComponent2);
        // ex. 4
        shortcutRegistration.preventDefault().stopPropagation();
    }

    /**
     * Fourth example
     * Global shortcuts
     */
    public class SomeView extends Div {
        private ShortcutRegistration shortcutRegistration;

        public SomeView() {
            shortcutRegistration = ShortcutActions.exec(
                    () -> UI.getCurrent().navigate(MainView.class))
                    .on(Key.BACKSPACE);
        }

        /**
         * Removes the registered global shortcut
         */
        private void removeShortcut() {
            if (shortcutRegistration != null) shortcutRegistration.remove();
        }
    }

    /**
     * Fifth example
     */
    public void focusAndClickExample() {
        TextField answerField = new TextField();
        // focus the answerField pressing ALT+F
        answerField.addFocusShortcut().on('F').alt();

        Button submit = new Button();
        submit.addClickListener(event -> submit());
        // submit the answer by pressing ENTER
        submit.addClickShortcut().on(Key.ENTER);
    }

    /**
     * Sixth example
     * Extended scope
     */
    public class Login1 {
        public class LoginScreen extends FlexLayout {

            public LoginScreen() {
                TextField username = new TextField();
                TextField password = new TextField();
                Button login = new Button();
                login.addClickListener(event -> this.login());

                add(username);
                add(password);
                add(login);

                ShortcutActions.exec(this::login, this).scope(this).on(Key.ENTER);
            }

            private void login() {
                // Login goes here.
            }
        }
    }

    /**
     * Seventh example
     * Extended scope, but a click shortcut
     */

    public class Login2 {
        public class LoginScreen extends FlexLayout {

            public LoginScreen() {
                TextField username = new TextField();
                TextField password = new TextField();
                Button login = new Button();
                login.addClickListener(event -> this.login());
                login.addClickShortcut().scope(this).on(Key.ENTER);

                add(username);
                add(password);
                add(login);
            }

            private void login() {
                // Login goes here.
            }
        }
    }

    /**
     * Eighth example
     */
    public void preventPropagationAndDefaultExample() {
        TextField textField = new TextField();
        // When the user presses '*', three zeros are added instead
        ShortcutActions.exec(
                () -> textField.setValue(textField.getValue() + "000"), textField)
                .on('*').scope(textField).preventDefault().stopPropagation();
    }

    /**
     * Ninth example
     * Focusable + tabindex trick
     */
    public class Login3 {
        public class LoginScreen extends FlexLayout implements Focusable {

            public LoginScreen() {
                // ...

                // Make the component selectable by the users' clicks
                setTabIndex(-1);

                // This removes the focus style when user clicks it.
                this.getElement().getStyle().set("outline", "none");

                // ...
            }
        }
    }

    /**
     * Helpers
     */

    private void login() {}

    private void submit() {}

    public class MainView extends Div {

    }
}
