package boardapp.view.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import boardapp.model.dto.Board;

public class InsertDialog extends JDialog {
    private BoardMain boardApp;
    private JPanel pCenter, pTitle, pContent, pWriter, pSouth;
    private JTextField txtTitle, txtWriter;
    private JTextArea txtContent;
    private JButton btnOk, btnCancel;

    public InsertDialog(BoardMain boardApp) {
        super(boardApp);
        this.boardApp = boardApp;
        setTitle("게시물 작성");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);
        setSize(400, 270);
        setLocationRelativeTo(boardApp);

        getContentPane().add(getPCenter(), BorderLayout.CENTER);
        getContentPane().add(getPSouth(), BorderLayout.SOUTH);
    }

    public JPanel getPCenter() {
        if (pCenter == null) {
            pCenter = new JPanel();
            pCenter.setBorder(new EmptyBorder(10, 10, 10, 10));
            pCenter.add(getPTitle());
            pCenter.add(getPWriter());
            pCenter.add(getPContent());
        }
        return pCenter;
    }

    public JPanel getPTitle() {
        if (pTitle == null) {
            pTitle = new JPanel(new BorderLayout());
            JLabel label = new JLabel("제목");
            label.setPreferredSize(new Dimension(70, 30));
            label.setHorizontalAlignment(JLabel.CENTER);
            txtTitle = new JTextField();
            txtTitle.setPreferredSize(new Dimension(300, 30));
            pTitle.add(label, BorderLayout.WEST);
            pTitle.add(txtTitle, BorderLayout.CENTER);
        }
        return pTitle;
    }

    public JPanel getPWriter() {
        if (pWriter == null) {
            pWriter = new JPanel(new BorderLayout());
            JLabel label = new JLabel("글쓴이");
            label.setPreferredSize(new Dimension(70, 30));
            label.setHorizontalAlignment(JLabel.CENTER);
            txtWriter = new JTextField();
            txtWriter.setPreferredSize(new Dimension(300, 30));
            pWriter.add(label, BorderLayout.WEST);
            pWriter.add(txtWriter, BorderLayout.CENTER);
        }
        return pWriter;
    }

    public JPanel getPContent() {
        if (pContent == null) {
            pContent = new JPanel(new BorderLayout());
            JLabel label = new JLabel("내용");
            label.setPreferredSize(new Dimension(70, 30));
            label.setHorizontalAlignment(JLabel.CENTER);
            txtContent = new JTextArea();
            txtContent.setPreferredSize(new Dimension(300, 100));
            txtContent.setBorder(new EtchedBorder());
            pContent.add(label, BorderLayout.WEST);
            pContent.add(txtContent, BorderLayout.CENTER);
        }
        return pContent;
    }

    public JPanel getPSouth() {
        if (pSouth == null) {
            pSouth = new JPanel();
            pSouth.setBackground(Color.WHITE);
            pSouth.add(getBtnOk());
            pSouth.add(getBtnCancel());
        }
        return pSouth;
    }

    public JButton getBtnOk() {
        if (btnOk == null) {
            btnOk = new JButton("저장");
            btnOk.addActionListener(e -> {
                String title = txtTitle.getText().trim();
                String content = txtContent.getText().trim();
                String writer = txtWriter.getText().trim();

                if (title.isEmpty() || content.isEmpty() || writer.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "모든 항목을 입력하세요.");
                    return;
                }

                Board board = new Board(title, content, writer);
                boardApp.addBoard(board);
                dispose();
            });
        }
        return btnOk;
    }

    public JButton getBtnCancel() {
        if (btnCancel == null) {
            btnCancel = new JButton("취소");
            btnCancel.addActionListener(e -> dispose());
        }
        return btnCancel;
    }
}
