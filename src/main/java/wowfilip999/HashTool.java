package wowfilip999;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.codec.digest.DigestUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.filechooser.*;

class HashDialog extends JDialog {
    public void init() {
        setTitle("Hashes");
        setVisible(true);
        setSize(800,480);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLayout(new FlowLayout());
    }
}

public class HashTool {
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
        }
        catch (Exception x) {
            x.printStackTrace();
        }

        JFrame frame = new JFrame("Hash Tool");
        frame.setLayout(new GridBagLayout());
        frame.setSize(400, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton verify = new JButton("verify");
        JButton open = new JButton("open");
        verify.setAlignmentX(Component.CENTER_ALIGNMENT);
        open.setAlignmentX(Component.CENTER_ALIGNMENT);
        verify.setFocusPainted(false);
        open.setFocusPainted(false);

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                j.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int r = j.showOpenDialog(null);

                if (r == JFileChooser.APPROVE_OPTION) {
                    try (InputStream is = Files.newInputStream(Paths.get(j.getSelectedFile().getAbsolutePath()))) {
                        try {
                            String checksum_md5 = DigestUtils.md5Hex(is);
                            String checksum_sha512 = DigestUtils.sha512Hex(is);
                            String checksum_md2 = DigestUtils.md2Hex(is);

                            JButton md2_btn = new JButton();
                            JButton md5_btn = new JButton();
                            JButton sha512_btn = new JButton();
                            md2_btn.setBorder(BorderFactory.createEmptyBorder());
                            md5_btn.setBorder(BorderFactory.createEmptyBorder());
                            sha512_btn.setBorder(BorderFactory.createEmptyBorder());

                            md5_btn.setText(checksum_md5);
                            sha512_btn.setText(checksum_sha512);
                            md2_btn.setText(checksum_md2);
                            md5_btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            sha512_btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            md2_btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

                            ActionListener copy_listener = new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    JButton btn = (JButton) e.getSource();
                                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                                    clipboard.setContents(new StringSelection(btn.getText()), null);
                                }
                            };

                            md5_btn.addActionListener(copy_listener);
                            sha512_btn.addActionListener(copy_listener);
                            md2_btn.addActionListener(copy_listener);

                            JLabel md2_label = new JLabel();
                            JLabel md5_label = new JLabel();
                            JLabel sha512_label = new JLabel();
                            sha512_label.setText("sha512 hash");
                            md5_label.setText("md5 hash");
                            md2_label.setText("md2 hash");

                            HashDialog dialog = new HashDialog();
                            JPanel hash_pnl = new JPanel(new MigLayout("fillx"));
                            hash_pnl.add(md5_label, "align center, wrap");
                            hash_pnl.add(md5_btn, "align center, wrap");
                            hash_pnl.add(sha512_label, "align center, wrap");
                            hash_pnl.add(sha512_btn, "align center, wrap");
                            hash_pnl.add(md2_label, "align center, wrap");
                            hash_pnl.add(md2_btn, "align center, wrap");
                            dialog.add(hash_pnl);

                            dialog.init();

                            md2_btn.setBackground(md2_btn.getParent().getBackground());
                            md5_btn.setBackground(md5_btn.getParent().getBackground());
                            sha512_btn.setBackground(sha512_btn.getParent().getBackground());

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });

        JPanel pnl = new JPanel();
        pnl.add(verify);
        pnl.add(open);

        frame.add(pnl);
        frame.setVisible(true);
    }
}
