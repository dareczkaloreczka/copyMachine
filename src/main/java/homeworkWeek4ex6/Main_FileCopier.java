package homeworkWeek4ex6;

import javax.swing.*;

public class Main_FileCopier {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FileCopier fileCopier = new FileCopier();
                fileCopier.setVisible(true);
            }
        });
    }
}
