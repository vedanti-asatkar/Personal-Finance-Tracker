package personalfinancemanager.app;

import javax.swing.SwingUtilities;
import personalfinancemanager.view.gui.MainFrame;

public class FinanceGuiApp {
    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater to ensure GUI updates are on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}