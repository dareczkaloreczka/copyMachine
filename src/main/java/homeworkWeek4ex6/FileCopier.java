package homeworkWeek4ex6;
//6. (Poziom 3) Zaimplementuj program z graficznym interfejsem u¿ytkownika s³u¿¹cy do kopiowania plików z
// jednego miejsca na dysku w inne. Kopiowanie powinno odbywaæ siê w oddzielnym w¹tku, na raz mo¿na kopiowaæ kilka plików.
// Postêp kopiowania nale¿y odzwierciedliæ za pomoc¹ ProgressBara, a jego stan aktualizowaæ na bie¿¹co.
// Spróbuj skopiowaæ kilka du¿ych plików (>3GB). Spróbuj zaimplementowaæ ten problem u¿ywaj¹c jednego w¹tku i Java I/O (nie New I/O).
// Jak zachowuje siê program?

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileCopier extends JFrame {

    public FileCopier() throws HeadlessException {
        initialisation();
    }

    public void initialisation() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Copy Machine");
        setSize(600, 150);

        JPanel progressPanel = new JPanel();
        JProgressBar progressBar = new JProgressBar();
        progressBar.setValue(0);
        JLabel progressLabel = new JLabel("status: 0%");

        JPanel pathsPanel = new JPanel();
        JLabel fileChosen = new JLabel("File: ");
        JLabel destinationFolder = new JLabel("Destination: ");
        JButton copyButton = new JButton("COPY");

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem selectFile = new JMenuItem("Choose file..");
        JMenuItem selectFolder = new JMenuItem("Chosse destination...");

        progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));
        progressPanel.add(progressBar);
        progressPanel.add(progressLabel);
        pathsPanel.setLayout(new BoxLayout(pathsPanel, BoxLayout.Y_AXIS));
        pathsPanel.add(fileChosen);
        pathsPanel.add(destinationFolder);
        pathsPanel.add(copyButton);
        fileMenu.add(selectFile);
        fileMenu.add(selectFolder);
        menuBar.add(fileMenu);
        getContentPane().add(BorderLayout.NORTH, menuBar);
        getContentPane().add(BorderLayout.CENTER, progressPanel);
        getContentPane().add(BorderLayout.SOUTH, pathsPanel);


        selectFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(new FileCopier());
                fileChosen.setText(fileChooser.getSelectedFile().getPath());
            }
        });

        selectFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser folderChooser = new JFileChooser();
                folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                folderChooser.showOpenDialog(new FileCopier());
                destinationFolder.setText(folderChooser.getCurrentDirectory().getPath());
            }
        });

        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File(fileChosen.getText());
                File newFile = new File(destinationFolder.getText()+ "\\" + file.getName());
                try {
                    Files.copy(file.toPath(), newFile.toPath());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }


}
