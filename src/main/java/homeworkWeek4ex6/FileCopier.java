package homeworkWeek4ex6;
//6. (Poziom 3) Zaimplementuj program z graficznym interfejsem u¿ytkownika s³u¿¹cy do kopiowania plików z
// jednego miejsca na dysku w inne. Kopiowanie powinno odbywaæ siê w oddzielnym w¹tku, na raz mo¿na kopiowaæ kilka plików.
// Postêp kopiowania nale¿y odzwierciedliæ za pomoc¹ ProgressBara, a jego stan aktualizowaæ na bie¿¹co.
// Spróbuj skopiowaæ kilka du¿ych plików (>3GB). Spróbuj zaimplementowaæ ten problem u¿ywaj¹c jednego w¹tku i Java I/O (nie New I/O).
// Jak zachowuje siê program?

import javafx.concurrent.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;

public class FileCopier extends JFrame {

    private JPanel progressPanel;
    private JProgressBar progressBar;
    private JLabel progressLabel;
    private JPanel pathsPanel;
    private JLabel fileChosen;
    private JLabel destinationFolder;
    private JButton copyButton;
    private Task task;


    public FileCopier() throws HeadlessException {
        initialisation();
    }

    public void initialisation() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Copy Machine");
        setSize(600, 150);

        progressPanel = new JPanel();
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        pathsPanel = new JPanel();
        fileChosen = new JLabel("File: ");
        destinationFolder = new JLabel("Destination: ");
        copyButton = new JButton("COPY");

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem selectFile = new JMenuItem("Choose file..");
        JMenuItem selectFolder = new JMenuItem("Chosse destination...");

        progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));
        progressPanel.add(progressBar);

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

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        copyFileAndShowProgress();
                    }
                });
                thread.start();


            }
        });

    }
    private synchronized void copyFileAndShowProgress( ){
        File src = new File(fileChosen.getText());
        File trgt = new File(destinationFolder.getText() + "\\" +src.getName());
        try {
            InputStream inputStream = new FileInputStream(src);
            OutputStream outputStream = new FileOutputStream(trgt);

            long inputSize = src.length();
            progressBar.setMaximum((int) (inputSize));
            progressBar.setValue(0);
            long transferred = 0;
            byte [] buff = new byte[1024];
            int bytesRead = 0;

            while((bytesRead = inputStream.read(buff)) > 0 ){
                transferred += bytesRead;
                progressBar.setValue((int)transferred);

                outputStream.write(buff, 0, bytesRead);




            }
            inputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
