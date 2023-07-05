package org.example;
import org.apache.commons.codec.digest.DigestUtils;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.filechooser.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

class lb  {
    public JTextField md2_label = new JTextField();
    public JTextField md5_label = new JTextField();
    public JTextField sha512_label = new JTextField();


}
class hash_dialog extends JDialog {

    public void main() {
        setTitle("hashes");
        setVisible(true);
        setSize(1040,400);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLayout(new FlowLayout());

    }
}
class filechooser extends JFrame implements ActionListener {
    static JButton verify = new JButton("verify");


    String hash = "";

    filechooser()
    {
    }

    public static void main(String args[]) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {

            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");

        }
        catch (Exception x) {
            x.getStackTrace();
        }

        JFrame f = new JFrame("hash tool");

        f.setLayout(new GridBagLayout());

        f.setSize(400, 400);

        f.setVisible(true);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JButton open = new JButton("open");
        open.setAlignmentX(Component.CENTER_ALIGNMENT);
        verify.setAlignmentX(Component.CENTER_ALIGNMENT);
        verify.setFocusPainted(false);
        open.setFocusPainted(false);

        filechooser f1 = new filechooser();
        open.addActionListener(f1);

        JPanel p = new JPanel();
        p.add(verify);
        p.add(open);
        f.add(p);
        f.show();
    }
    public void actionPerformed(ActionEvent evt)
    {
        // if the user presses the save button show the save dialog
        String com = evt.getActionCommand();



            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            // set the selection mode to directories only
            j.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

            // invoke the showsOpenDialog function to show the save dialog
            int r = j.showOpenDialog(null);

            if (r == JFileChooser.APPROVE_OPTION) {
                try (InputStream is = Files.newInputStream(Paths.get(j.getSelectedFile().getAbsolutePath()))) {
                    try {
                        String checksum_md5 = DigestUtils.md5Hex(is);
                        String checksum_sha512 = DigestUtils.sha512Hex(is);
                        String checksum_md2 = DigestUtils.md2Hex(is);


                        lb label = new lb();
                        label.md5_label.setText(checksum_md5);
                        label.sha512_label.setText(checksum_sha512);
                        label.md2_label.setText(checksum_md2);

                        label.sha512_label.setToolTipText("sha512 hash");
                        label.md5_label.setToolTipText("md5 hash");
                        label.md2_label.setToolTipText("md2 hash");

                        hash_dialog m = new hash_dialog();
                        m.add(label.md5_label);
                        m.add(label.sha512_label);
                        m.add(label.md2_label);

                        m.main();
                        verify.setEnabled(true);



                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
    }
}
